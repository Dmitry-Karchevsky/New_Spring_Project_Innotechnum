package mynewpackage.repository;

import mynewpackage.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findRoleByName(String name);
}
