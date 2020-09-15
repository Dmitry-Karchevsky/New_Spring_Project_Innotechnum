package mynewpackage.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import mynewpackage.domain.Question;
import mynewpackage.domain.Views;
import mynewpackage.service.QuestionService;
import mynewpackage.service.TestService;
import mynewpackage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test-constructor")
public class TestConstructorController {
    @Autowired
    private UserService userService;

    @Autowired
    private TestService testService;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/{id_test}")
    @JsonView(Views.RequiredField.class)
    public List<Question> getAllQuestions(@PathVariable("id_test") Long testId){
        return questionService.allQuestionsInTest(testId);
    }

    @PostMapping("/{id_test}")
    @JsonView(Views.RequiredField.class)
    public ResponseEntity<Question> createQuestions(@PathVariable("id_test") Long idTest,
                                  @RequestBody Question questionWithAnswers
    ){
        Question createdQuestion = questionService.saveQuestion(questionWithAnswers, idTest);

        return ResponseEntity.ok(createdQuestion);
    }

    @PutMapping("/{id_test}/{id_question}")
    @JsonView(Views.RequiredField.class)
    public ResponseEntity<Question> updateQuestion(@PathVariable("id_test") Long idTest,
                                                   @PathVariable("id_question") Long idQuestion,
                                                   @RequestBody Question questionWithAnswers) {
        Question updateQuestion = questionService.updateQuestion(idQuestion, questionWithAnswers);
        return ResponseEntity.ok(updateQuestion);
    }
}
