package andrzej.appdemo.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import andrzej.appdemo.user.User;

public interface AdminService {
	
	Page<User> findAll(Pageable pageable);

}
