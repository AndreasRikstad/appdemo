package andrzej.appdemo.admin;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import andrzej.appdemo.user.User;
import andrzej.appdemo.utilities.UserUtilities;

@Controller
public class AdminPageController {

	private static int ELEMENTS = 10;

	@Autowired
	private AdminService adminService;

	@Autowired
	private MessageSource messageSource;

	@GET
	@RequestMapping(value = "/admin")
	@Secured(value = { "ROLE_ADMIN" })
	public String openAdminMainPage() {
		return "admin/admin";
	}

	@GET
	@RequestMapping(value = "/admin/users/{page}")
	@Secured(value = { "ROLE_ADMIN" })
	public String openAdminAllUsersPage(@PathVariable("page") int page, Model model) {
		Page<User> pages = getAllUsersPageable(page - 1, false, null);
		int totalPages = pages.getTotalPages();
		int currentPage = pages.getNumber();
		List<User> userList = pages.getContent();
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", currentPage + 1);
		model.addAttribute("userList", userList);
		model.addAttribute("recordStartCounter", currentPage * ELEMENTS);
		return "admin/users";
	}

	@GET
	@RequestMapping(value = "/admin/users/edit/{id}")
	@Secured(value = { "ROLE_ADMIN" })
	public String getUserToEdit(@PathVariable("id") int id, Model model) {
		User user = new User();
		user = adminService.findUserById(id);
		Map<Integer, String> roleMap = new HashMap<Integer, String>();
		roleMap = prepareRoleMap();
		Map<Integer, String> activityMap = new HashMap<Integer, String>();
		activityMap = prepareActivityMap();
		int rola = user.getRoles().iterator().next().getId();
		user.setNrRoli(rola);
		model.addAttribute("roleMap", roleMap);
		model.addAttribute("activityMap", activityMap);
		model.addAttribute("user", user);
		return "admin/useredit";
	}

	@POST
	@RequestMapping(value = "/admin/updateuser/{id}")
	@Secured(value = "ROLE_ADMIN")
	public String updateUser(@PathVariable("id") int id, User user) {
		int nrRoli = user.getNrRoli();
		int czyActive = user.getActive();
		adminService.updateUser(id, nrRoli, czyActive);
		return "redirect:/admin/users/1";
	}

	@GET
	@RequestMapping(value = "/admin/users/search/{searchWord}/{page}")
	@Secured(value = "ROLE_ADMIN")
	public String openSearchUsersPage(@PathVariable("searchWord") String searchWord, 
			@PathVariable("page") int page, Model model) {
		Page<User> pages = getAllUsersPageable(page - 1, true, searchWord);
		int totalPages = pages.getTotalPages();
		int currentPage = pages.getNumber();
		List<User> userList = pages.getContent();
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", currentPage + 1);
		model.addAttribute("userList", userList);
		model.addAttribute("recordStartCounter", currentPage * ELEMENTS);
		model.addAttribute("searchWord", searchWord);
		model.addAttribute("userList", userList);
		return "admin/usersearch";
	}

	@GET
	@RequestMapping(value = "/admin/users/importusers")
	@Secured(value = "ROLE_ADMIN")
	public String showUploadPageFromXML(Model model) {
		return "admin/importusers";
	}
	
	@POST
	@RequestMapping(value = "/admin/users/upload")
	@Secured(value = "ROLE_ADMIN")
	public String importUsersFromXML(@RequestParam("filename") MultipartFile mFile) {
		String uploadDir = System.getProperty("user.dir") + "/uploads";
		File file;
		try {
			file = new File(uploadDir);
			if (!file.exists()) {
				file.mkdir();
			}
			Path fileAndPath = Paths.get(uploadDir, mFile.getOriginalFilename());
			Files.write(fileAndPath, mFile.getBytes());
			file = new File(fileAndPath.toString());
			List<User> userList = UserUtilities.usersDataLoader(file);
			for (User u : userList) {
				System.out.println(u.getEmail() + " > " + u.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/admin/users/1";
	}

	// Pobranie listy userów
	private Page<User> getAllUsersPageable(int page, boolean search, String param) {
		Page<User> pages;
		if (!search) {
			pages = adminService.findAll(PageRequest.of(page, ELEMENTS));
		} else {
			pages = adminService.findAllSearch(param, PageRequest.of(page, ELEMENTS));
		}
		for (User users : pages) {
			int numerRoli = users.getRoles().iterator().next().getId();
			users.setNrRoli(numerRoli);
		}
		return pages;
	}

	// przygotowanie mapy ról
	public Map<Integer, String> prepareRoleMap() {
		Locale locale = Locale.getDefault();
		Map<Integer, String> roleMap = new HashMap<Integer, String>();
		roleMap.put(1, messageSource.getMessage("word.admin", null, locale));
		roleMap.put(2, messageSource.getMessage("word.user", null, locale));
		return roleMap;
	}

	// przygotowanie may aktywny/nieaktywny
	public Map<Integer, String> prepareActivityMap() {
		Locale locale = Locale.getDefault();
		Map<Integer, String> activityMap = new HashMap<Integer, String>();
		activityMap.put(0, messageSource.getMessage("word.nie", null, locale));
		activityMap.put(1, messageSource.getMessage("word.tak", null, locale));
		return activityMap;
	}
}
