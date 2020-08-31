package mynewpackage.repository;

import mynewpackage.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    boolean deleteUserById(Long id);
}
