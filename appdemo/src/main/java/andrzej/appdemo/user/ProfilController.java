package andrzej.appdemo.user;

import java.util.Locale;

import javax.ws.rs.GET;
import javax.ws.rs.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import andrzej.appdemo.utilities.UserUtilities;
import andrzej.appdemo.validators.ChangePasswordValidator;

@Controller
public class ProfilController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	MessageSource messageSource;
	
	@GET
	@RequestMapping(value = "/profil")
	public String showUserProfilePage(Model model) {	
		String username = UserUtilities.getLoggedUser();
		User user = userService.findUserByEmail(username);
		int nrRoli = user.getRoles().iterator().next().getId();
		user.setNrRoli(nrRoli);
		model.addAttribute("user", user);
		return "profil";
	}
	
	@GET
	@RequestMapping(value = "/editpassword")
	public String editUserPassword(Model model) {
		String username = UserUtilities.getLoggedUser();
		User user = userService.findUserByEmail(username);
		model.addAttribute("user", user);
		return "editpassword";
	}
	
	@POST
	@RequestMapping(value = "/updatepass")
	public String changeUSerPassword(User user, BindingResult result, Model model, Locale locale) {
		String returnPage = null;
		
		new ChangePasswordValidator().validate(user, result);
		
		new ChangePasswordValidator().checkPasswords(user.getNewPassword(), result);
		
		if (result.hasErrors()) {
			returnPage = "editpassword";
		} else {
			userService.updateUserPassword(user.getNewPassword(), user.getEmail());
			returnPage = "editpassword";
			model.addAttribute("message", messageSource.getMessage("passwordChange.success", null, locale));
		}
		
		return returnPage;
	}

}
