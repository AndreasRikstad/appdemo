package andrzej.appdemo.user;

import java.util.Locale;

import javax.ws.rs.GET;
import javax.ws.rs.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import andrzej.appdemo.emailSender.EmailSender;
import andrzej.appdemo.utilities.AppdemoUtils;
import andrzej.appdemo.validators.UserRegisterValidator;

@Controller
public class RegisterController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailSender emailSender;
	
	@Autowired
	MessageSource messageSource;
	
	@GET
	@RequestMapping(value = "/register")
	public String registerForm(Model model) {
		User u = new User();
		model.addAttribute("user", u);
		return "register";
	}
	
	@POST
	@RequestMapping(value = "/adduser")
	public String registerAction(User user, BindingResult result, Model model, Locale locale) {
				
		String returnPage = null;
		
		User userExist = userService.findUserByEmail(user.getEmail());
		
		new UserRegisterValidator().validateEmailExist(userExist, result);
				
		new UserRegisterValidator().validate(user, result);
				
		if (result.hasErrors()) {
			returnPage = "register";
		} else {
			user.setActivationCode(AppdemoUtils.randomStringGenerator());
			
			String content = "Wymagane potwierdzenie rejestracji. Kliknij w poniższy link aby aktywować konto: " +
			"http://localhost:8080/activatelink/" + user.getActivationCode();
			
			userService.saveUser(user);
			emailSender.sendEmail(user.getEmail(), "Potwierdzenie rejestracji", content);
			model.addAttribute("message", messageSource.getMessage("user.register.success.email", null, locale));
			//model.addAttribute("user", new User());
			returnPage = "index";
		}
		
		return returnPage;
	}

	@POST
	@RequestMapping(value = "/activatelink/{activationCode}")
	public String activateAccount(@PathVariable("activationCode") String activationCode, Model model, Locale locale) {

		userService.updateUserActivation(1, activationCode);
		
		model.addAttribute("message", messageSource.getMessage("user.register.success", null, locale));
		
		return "index";
	}
}
