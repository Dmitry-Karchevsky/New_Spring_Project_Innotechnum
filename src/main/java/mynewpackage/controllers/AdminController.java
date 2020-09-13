package mynewpackage.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import mynewpackage.domain.User;
import mynewpackage.domain.Views;
import mynewpackage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    @JsonView(Views.RequiredField.class)
    public List<User> readAll() {
        return userService.allUsers();
    }

    @GetMapping("/{id}")
    @JsonView(Views.RequiredField.class)
    public ResponseEntity<User> readUser(@PathVariable("id") Long id) {
        User userFromDB = userService.findUserById(id);
        if (userFromDB == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(userFromDB);
        }
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
        userService.deleteUser2(id);

        return ResponseEntity.ok().build();
    }


}
