package andrzej.appdemo.admin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import andrzej.appdemo.user.Role;
import andrzej.appdemo.user.RoleRepository;
import andrzej.appdemo.user.User;

@Service("adminService")
@Transactional
public class AdminServiceImpl implements AdminService {

	@Autowired
    private JpaContext jpaContext;
	
	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

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

	@Override
	public Page<User> findAllSearch(String param, Pageable pageable) {
		Page<User> userList = adminRepository.findAllSearch(param, pageable);
		return userList;
	}

	@Override
	public void insertInBatch(List<User> userList) {
		EntityManager em = jpaContext.getEntityManagerByManagedType(User.class);
		
		for (int i = 0; i < userList.size(); i++) {
            User u = userList.get(i);
            Role role = roleRepository.findByRole("ROLE_USER");
            u.setRoles(new HashSet<Role>(Arrays.asList(role)));
			u.setPassword(bCryptPasswordEncoder.encode(u.getPassword()));
            em.persist(u);
            if (i % 50 == 0 && i > 0) {
                em.flush();
                em.clear();
                System.out.println("**** Załadowano " + i + " rekordów z " + userList.size() );
            }
		}
	}
	
	public void saveAll(List<User> userList) {
		for (int i = 0; i < userList.size(); i++) {
            Role role = roleRepository.findByRole("ROLE_USER");
            userList.get(i).setRoles(new HashSet<Role>(Arrays.asList(role)));
			userList.get(i).setPassword(bCryptPasswordEncoder.encode(userList.get(i).getPassword()));
		}
		adminRepository.saveAll(userList);
	}
}
