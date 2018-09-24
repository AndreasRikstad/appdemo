package andrzej.appdemo.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import andrzej.appdemo.user.User;

@Repository("adminRepository")
public interface AdminRepository extends JpaRepository<User, Integer> {

}
