package mynewpackage.service;

import mynewpackage.domain.Answer;
import mynewpackage.domain.Question;
import mynewpackage.repository.AnswerRepository;
import mynewpackage.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public List<Answer> allAnswersInQuestion(Long idQuestion) {
        Optional<Question> questionFromDb = questionRepository.findById(idQuestion);
        return questionFromDb.map(question -> answerRepository.findAllByQuestion(question)).orElse(null);
    }

    public boolean saveAnswerList(List<Answer> answerList, Question question) {
        for (Answer answer : answerList) {
            answer.setQuestion(question);
            answerRepository.save(answer);
        }
        return true;
    }

    public Answer saveAnswer(Answer answer, Long idQuestion) {
        answer.setQuestion(questionRepository.findById(idQuestion).get());
        answerRepository.save(answer);
        return answer;
    }
}
