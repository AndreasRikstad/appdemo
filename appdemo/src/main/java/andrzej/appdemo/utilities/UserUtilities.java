package andrzej.appdemo.utilities;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtilities {
	
	public static String getLoggedUser() {
		String username = null;
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(!(auth instanceof AnonymousAuthenticationToken)) {
			username = auth.getName();
		}
		return username;
	}

}

