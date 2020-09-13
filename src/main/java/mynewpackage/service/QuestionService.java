package mynewpackage.service;

import mynewpackage.domain.Question;
import mynewpackage.domain.Test;
import mynewpackage.repository.QuestionRepository;
import mynewpackage.repository.TestRepository;
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

    public List<Question> allQuestionsInTest(Long idTest) {
        Optional<Test> testFromDb = testRepository.findById(idTest);
        List<Question> questionList = testFromDb.map(test -> questionRepository.findAllByTest(test)).orElse(null);
        for (Question question : questionList){
            question.setAnswers(answerService.allAnswersInQuestion(question.getId()));
        }
        return questionList;
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
}
