package mynewpackage.service;

import mynewpackage.domain.Test;
import mynewpackage.domain.User;
import mynewpackage.repository.TestRepository;
import mynewpackage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Test> allTests() {
        return testRepository.findAll();
    }

    public Test getTestWithId(Long id) {
        Optional<Test> testFromDb = testRepository.findById(id);
        return testFromDb.orElse(null);
    }

    public Test getTestWithIdAndAuthor(Long idAuthor, Long idTest) {
        Optional<Test> testFromDb = testRepository.findById(idTest);
        if (allTestsWithAuthor(idAuthor).contains(testFromDb.get()))
            return testFromDb.get();
        return null;
    }

    public List<Test> allTestsWithAuthor(Long idAuthor) {
        Optional<User> author = userRepository.findById(idAuthor);
        return author.map(user -> testRepository.findAllByAuthor(user)).orElse(null);
    }

    public Test saveTest(Test test, Long idUser) {
        /*Optional<Test> testFromDb = testRepository.findById(test.getId());
        if (testFromDb.isPresent()) {
            return null;
        }*/
        test.setAuthor(userRepository.findById(idUser).get());
        testRepository.save(test);
        return test;
    }

    public Test updateTest(Long id, Test test) {
        test.setId(id);
        Optional<Test> testFromDb = testRepository.findById(id);
        if (testFromDb.isPresent()) {
            test.setAuthor(testFromDb.get().getAuthor());
            return test;
        }
        return null;
    }

    public boolean deleteTest(Long testId) {
        if (testRepository.findById(testId).isPresent()) {
            testRepository.deleteById(testId);
            return true;
        }
        return false;
    }
}
