package mynewpackage.repository;

import mynewpackage.domain.Test;
import mynewpackage.domain.TestStudentMark;
import mynewpackage.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestStudentMarkRepository extends JpaRepository<TestStudentMark, Long> {
    TestStudentMark findByTestAndUser(Test test, User user);
}
