package mynewpackage.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import mynewpackage.domain.Test;
import mynewpackage.domain.User;
import mynewpackage.domain.Views;
import mynewpackage.service.TestService;
import mynewpackage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        return testService.allTestsWithAuthor(idUser);
    }

    @GetMapping("/{id_user}/mytests/{id_test}")
    @JsonView(Views.RequiredField.class)
    public ResponseEntity<Test> readUserTests111(@PathVariable("id_user") Long idUser,
                                              @PathVariable("id_test") Long idTest) {
        Test testFromDB = testService.getTestWithIdAndAuthor(idUser, idTest);
        if (testFromDB == null) {
            System.out.println("00000000000000000000");
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(testFromDB);
        }
    }

    @PostMapping("/{id_user}/createtest")
    @JsonView(Views.RequiredField.class)
    public ResponseEntity<Test> createTest(@PathVariable("id_user") Long idUser,
                                           @RequestBody Test test){
        Test createdTest = testService.saveTest(test, idUser);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id_user}/mytests/{id_test}")
                .buildAndExpand(idUser, createdTest.getId())
                .toUri();

        return ResponseEntity.created(uri)
                .body(createdTest);
    }

    @PutMapping("/{id}")
    @JsonView(Views.RequiredField.class)
    public ResponseEntity<User> updateUser(@RequestBody User user,
                                           @PathVariable Long id) {
        User updatedUser = userService.updateUser(id, user);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(updatedUser);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        return ResponseEntity.ok().build();
    }
}
