package andrzej.appdemo.admin;

import javax.ws.rs.GET;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminPageController {
	
	@GET
	@RequestMapping(value = "/admin")
	@Secured(value = {"ROLE_ADMIN"})
	public String openAdminMainPage() {
		return "admin/admin";
	}

}
