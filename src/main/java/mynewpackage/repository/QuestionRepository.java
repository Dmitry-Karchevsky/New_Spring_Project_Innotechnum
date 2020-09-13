package mynewpackage.repository;

import mynewpackage.domain.Question;
import mynewpackage.domain.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByTest(Test test);
}
