package mynewpackage.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import mynewpackage.domain.Question;
import mynewpackage.domain.User;
import mynewpackage.domain.Views;
import mynewpackage.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/passing-test")
public class PassingTestController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/{id_test}")
    @JsonView(Views.RequiredField.class)
    public List<Question> getAllQuestionsInPassingTest(@PathVariable("id_test") Long testId) {
        return questionService.allQuestionsInTest(testId);
    }

    @GetMapping("/{id_test}/{id_question}")
    @JsonView(Views.RequiredField.class)
    public ResponseEntity<Question> getOneQuestionsInPassingTest(@PathVariable("id_test") Long testId,
                                                       @PathVariable("id_question") Long questionId
    ) {
        Question questionFromDb = questionService.getQuestion(testId, questionId);

        return questionFromDb == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(questionFromDb);
    }

    @PostMapping("/{id_test}/{id_question}")
    @JsonView(Views.RequiredField.class)
    public ResponseEntity<Question> createQuestions(@PathVariable("id_test") Long idTest,
                                                    @PathVariable("id_question") Long questionId,
                                                    @RequestBody Question questionWithAnswers
    ){
        UserDetails userDetails = (UserDetails) org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        Question questionFromDb = questionService.writeAnswers(questionId, questionWithAnswers, userDetails.getUsername());

        return ResponseEntity.ok(questionFromDb);
    }
}
