package mynewpackage.service;

import mynewpackage.domain.Answer;
import mynewpackage.domain.Question;
import mynewpackage.domain.Test;
import mynewpackage.domain.User;
import mynewpackage.repository.AnswerRepository;
import mynewpackage.repository.QuestionRepository;
import mynewpackage.repository.TestRepository;
import mynewpackage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnswerRepository answerRepository;

    public List<Question> allQuestionsInTest(Long idTest) {
        Optional<Test> testFromDb = testRepository.findById(idTest);
        List<Question> questionList = testFromDb.map(test -> questionRepository.findAllByTest(test)).orElse(null);
        for (Question question : questionList){
            question.setAnswers(answerService.allAnswersInQuestion(question.getId()));
        }
        return questionList;
    }

    public Question getQuestion(Long idTest, Long questionId) {
        Optional<Test> testFromDb = testRepository.findById(idTest);
        Optional<Question> questionFromDb = questionRepository.findById(questionId);
        if (!testFromDb.equals(questionFromDb.get().getTest()))
            return null;
        questionFromDb.get().setAnswers(answerService.allAnswersInQuestion(questionId));
        return questionFromDb.get();
    }

    public Question saveQuestion(Question question, Long idTest) {
        question.setTest(testRepository.findById(idTest).get());
        questionRepository.save(question);

        answerService.saveAnswerList(question.getAnswers(), question);

        return question;
    }

    public List<Question> saveListQuestion(List<Question> questionList, Long idTest) {
        for (Question question : questionList){
            question.setTest(testRepository.findById(idTest).get());
            questionRepository.save(question);
        }
        return questionList;
    }

    public Question updateQuestion(Long idQuestion, Question question){
        question.setId(idQuestion);
        Optional<Question> questionFromDb = questionRepository.findById(idQuestion);
        if (questionFromDb.isPresent()){
            questionRepository.save(question);
            return question;
        }
        return null;
    }



    public Question writeAnswers(Long idQuestion, Question question, String userName){
        User student = userRepository.findByUsername(userName);
        Optional<Question> questionFromDb = questionRepository.findById(idQuestion);

        for (Answer answer : question.getAnswers()){
            if (answer.isTrue())
                answerService.saveAnswer(answer, questionFromDb.get(), student);
        }

        return questionFromDb.get();
    }
}
