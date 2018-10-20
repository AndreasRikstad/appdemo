package andrzej.appdemo.utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import andrzej.appdemo.user.User;

public class UserUtilities {
	
	public static String getLoggedUser() {
		String username = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(!(auth instanceof AnonymousAuthenticationToken)) {
			username = auth.getName();
		}
		return username;
	}
	
	public static List<User> usersDataLoader(File file) {
		List<User> userList = new ArrayList<User>();
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBilder = dbFactory.newDocumentBuilder();
			Document doc = dBilder.parse(file);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("user");
			for (int i = 0; i < nList.getLength(); i++) {
				Node n = nList.item(i);
				if (n.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) n;
					User u = new User();
					u.setEmail(e.getElementsByTagName("email").item(0).getTextContent());
					u.setPassword(e.getElementsByTagName("password").item(0).getTextContent());
					u.setName(e.getElementsByTagName("name").item(0).getTextContent());
					u.setLastName(e.getElementsByTagName("lastname").item(0).getTextContent());
					u.setActive(Integer.valueOf(e.getElementsByTagName("active").item(0).getTextContent()));
					u.setNrRoli(Integer.valueOf(e.getElementsByTagName("nrroli").item(0).getTextContent()));
					userList.add(u);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userList;
	}
}

