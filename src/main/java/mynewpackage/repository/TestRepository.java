package mynewpackage.repository;

import mynewpackage.domain.Test;
import mynewpackage.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findAllByAuthor(User author);
}