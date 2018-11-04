package andrzej.appdemo.admin;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import andrzej.appdemo.user.User;

public interface AdminService {
	
	Page<User> findAll(Pageable pageable);
	User findUserById(int id);
	void updateUser(int id, int nrRoli, int activity);
	Page<User> findAllSearch(String param, Pageable pageable);
	void insertInBatch(List<User> userList);
	void saveAll(List<User> userList);
}
