package mynewpackage.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import mynewpackage.domain.Question;
import mynewpackage.domain.Test;
import mynewpackage.domain.User;
import mynewpackage.domain.Views;
import mynewpackage.service.TestService;
import mynewpackage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/lk")
public class UserLkController {

    @Autowired
    private UserService userService;

    @Autowired
    private TestService testService;

    @GetMapping("/{id_user}")
    @JsonView(Views.RequiredField.class)
    public ResponseEntity<User> readUser(@PathVariable("id_user") Long id) {
        if (!isTrueUser(id))
            return ResponseEntity.notFound().build();

        User userFromDB = userService.findUserById(id);
        if (userFromDB == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(userFromDB);
        }
    }

    @GetMapping("/{id_user}/mytests")
    @JsonView(Views.RequiredField.class)
    public List<Test> readUserTests(@PathVariable("id_user") Long idUser) {
        if (!isTrueUser(idUser))
            return null;
        return testService.allTestsWithAuthor(idUser);
    }

    @GetMapping("/{id_user}/mytests/{id_test}")
    @JsonView(Views.RequiredField.class)
    public ResponseEntity<Test> readUserTests111(@PathVariable("id_user") Long idUser,
                                              @PathVariable("id_test") Long idTest) {
        if (!isTrueUser(idUser))
            return ResponseEntity.notFound().build();
        Test testFromDB = testService.getTestWithIdAndAuthor(idUser, idTest);
        if (testFromDB == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(testFromDB);
        }
    }

    /*@PostMapping("/{id_user}/mytests")
    @JsonView(Views.RequiredField.class)// сделать редирект на тестКонструктор
    public ResponseEntity<Test> createTest(@PathVariable("id_user") Long idUser,
                                           @RequestBody Test test){
        if (!isTrueUser(idUser))
            return ResponseEntity.notFound().build();

        Test createdTest = testService.saveTest(test, idUser);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id_user}/mytests/{id_test}")
                .buildAndExpand(idUser, createdTest.getId())
                .toUri();

        return ResponseEntity.created(uri)
                .body(createdTest);
    }*/

    /*@Autowired
    private TestConstructorController testConstructorController;

    @GetMapping("/rer")
    List<Question> rer() {
        //return "http://localhost:8080/test-constructor/3";
        return testConstructorController.getAllQuestions(3l);
    }*/

    @PostMapping("/{id_user}/mytests")
    @JsonView(Views.RequiredField.class)// сделать редирект на тестКонструктор
    public ResponseEntity<Test> createTest(@PathVariable("id_user") Long idUser,
                                           @RequestBody Test test) {
        if (!isTrueUser(idUser))
            return ResponseEntity.notFound().build();

        Test createdTest = testService.saveTest(test, idUser);

        return ResponseEntity.ok(createdTest);
    }

    @PutMapping("/{idUser}/mytests/{idTest}")
    @JsonView(Views.RequiredField.class)
    public ResponseEntity<Test> updateUser(@RequestBody Test test,
                                           @PathVariable("idTest") Long idTest,
                                           @PathVariable("idUser") Long idUser) {
        if (!isTrueUser(idUser))
            return ResponseEntity.notFound().build();

        Test updatedTest = testService.updateTest(idTest, test);
        if (updatedTest == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(updatedTest);
        }
    }

    @DeleteMapping("/{idUser}/mytests/{idTest}")
    public ResponseEntity<Test> deleteUser(@PathVariable("idTest") Long idTest,
                                           @PathVariable("idUser") Long idUser) {
        if (!isTrueUser(idUser))
            return ResponseEntity.notFound().build();

        testService.deleteTest(idTest);

        return ResponseEntity.ok().build();
    }

    private boolean isTrueUser(Long id) {
        UserDetails userDetails = (UserDetails) org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        User userFromDb = userService.findUserById(id);

        return userDetails.getUsername().equals(userFromDb.getUsername()) && userFromDb.isActive();
    }
}
