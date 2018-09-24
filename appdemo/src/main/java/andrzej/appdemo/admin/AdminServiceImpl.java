package andrzej.appdemo.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import andrzej.appdemo.user.User;

@Service("adminService")
@Transactional
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Override
	public List<User> findAll() {
		List<User> userList = adminRepository.findAll();
		return userList;
	}

}
