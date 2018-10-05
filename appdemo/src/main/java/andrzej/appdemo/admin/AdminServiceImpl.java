package andrzej.appdemo.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import andrzej.appdemo.user.User;

@Service("adminService")
@Transactional
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminRepository adminRepository;

	@Override
	public Page<User> findAll(Pageable pageable) {
		Page<User> userList = adminRepository.findAll(pageable);
		return userList;
	}

	@Override
	public User findUserById(int id) {
		User user = adminRepository.findUserById(id);
		return user;
	}

	@Override
	public void updateUser(int id, int nrRoli, int activity) {
		adminRepository.updateActivationUser(activity, id);
		adminRepository.updateRoleUser(nrRoli, id);
	}

	

}
