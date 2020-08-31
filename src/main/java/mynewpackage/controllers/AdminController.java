package mynewpackage.controllers;

import mynewpackage.domain.User;
import mynewpackage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public List<User> readAll() {
        return userService.allUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> readUser(@PathVariable("id") Long id) {
        User userFromDB = userService.findUserById(id);
        if (userFromDB == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(userFromDB);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User student,
                                       @PathVariable Long id) {
        User updatedUser = userService.updateUser(id, student);
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
