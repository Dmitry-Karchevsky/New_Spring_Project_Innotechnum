package mynewpackage.service;

import mynewpackage.domain.Test;
import mynewpackage.domain.TestStudentMark;
import mynewpackage.domain.User;
import mynewpackage.repository.QuestionRepository;
import mynewpackage.repository.TestRepository;
import mynewpackage.repository.TestStudentMarkRepository;
import mynewpackage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TestStudentMarkService {
    @Autowired
    private TestStudentMarkRepository testStudentMarkRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    public TestStudentMark getTestStudentMark(Long testId, String username){
        Optional<Test> test = testRepository.findById(testId);
        User student = userRepository.findByUsername(username);

        return testStudentMarkRepository.findByTestAndUser(test.get(), student);
    }

    public TestStudentMark startTest(Long testId, String username){
        Optional<Test> test = testRepository.findById(testId);
        User student = userRepository.findByUsername(username);

        int countOfQuestionsInTest = questionRepository.findAllByTest(test.get()).size();

        TestStudentMark testStudentMark = new TestStudentMark(student, test.get(), countOfQuestionsInTest, false);

        testStudentMarkRepository.save(testStudentMark);
        return testStudentMark;
    }

    public TestStudentMark finishTest(Long testId, String username){
        Optional<Test> test = testRepository.findById(testId);
        User student = userRepository.findByUsername(username);

        TestStudentMark testStudentMark = testStudentMarkRepository.findByTestAndUser(test.get(), student);
        testStudentMark.setIsFinish(true);

        testStudentMarkRepository.save(testStudentMark);
        return testStudentMark;
    }

    public boolean updateMarkPlusOneAnswer(Test test, User student){
        //Optional<Test> test = testRepository.findById(testId);
        //User student = userRepository.findByUsername(username);

        TestStudentMark testStudentMark = testStudentMarkRepository.findByTestAndUser(test, student);
        testStudentMark.setCountOfCorrectAnswers(testStudentMark.getCountOfCorrectAnswers() + 1);
        testStudentMark.setMark((double)testStudentMark.getCountOfCorrectAnswers() / testStudentMark.getCountOfQuestions());

        testStudentMarkRepository.save(testStudentMark);
        return true;
    }
}
