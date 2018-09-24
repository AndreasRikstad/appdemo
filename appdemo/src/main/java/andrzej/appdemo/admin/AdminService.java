package andrzej.appdemo.admin;

import java.util.List;

import andrzej.appdemo.user.User;

public interface AdminService {
	
	public List<User> findAll();

}
