package mynewpackage.service;

import mynewpackage.domain.Answer;
import mynewpackage.domain.Question;
import mynewpackage.domain.User;
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

    public Answer saveAnswer(Answer answer, Question question, User student) {
        answer.setQuestion(question);
        for (Answer answerFromList : allAnswersInQuestion(question.getId())){
            if (answer.getAnswerString().equals(answerFromList.getAnswerString())) {
                answer.setId(answerFromList.getId());
                answer.getUsers().add(student);
            }
        }
        answerRepository.save(answer);
        return answer;
    }

    public Answer updateAnswer(Long idAnswer, Answer answer) {
        answer.setId(idAnswer);
        Optional<Answer> answerFromDb = answerRepository.findById(idAnswer);
        if (answerFromDb.isPresent()){
            answer.setQuestion(answerFromDb.get().getQuestion());
            answer.setUsers(answerFromDb.get().getUsers());
            answerRepository.save(answer);
            return answer;
        }
        return null;
    }
}
