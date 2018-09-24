package andrzej.appdemo.admin;

import java.util.List;

import javax.ws.rs.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import andrzej.appdemo.user.User;

@Controller
public class AdminPageController {
	
	@Autowired
	private AdminService adminService;
	
	@GET
	@RequestMapping(value = "/admin")
	@Secured(value = {"ROLE_ADMIN"})
	public String openAdminMainPage() {
		return "admin/admin";
	}

	@GET
	@RequestMapping(value = "/admin/users")
	@Secured(value = {"ROLE_ADMIN"})
	public String openAdminAllUsersPage(Model model) {
		List<User> userList = getAllUsers();
		model.addAttribute("userList", userList);
		return "admin/users";
	}
	
	//Pobranie listy userów
	private List<User> getAllUsers() {
		List<User> usersList = adminService.findAll();
		for(User users : usersList) {
			int numerRoli = users.getRoles().iterator().next().getId();
			users.setNrRoli(numerRoli);
		}
		return usersList;
	}
}
