package andrzej.appdemo.user;

public interface UserService {
	
	public User findUserByEmail(String email);
	public void saveUser(User user);

}
