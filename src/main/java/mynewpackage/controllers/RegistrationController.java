package mynewpackage.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import mynewpackage.domain.User;
import mynewpackage.domain.Views;
import mynewpackage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    @JsonView(Views.RequiredField.class)
    public ResponseEntity<User> create(@RequestBody User user) throws URISyntaxException {
        User createdStudent = userService.saveUser(user);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdStudent.getId())
                .toUri();

        return ResponseEntity.created(uri)
                .body(createdStudent);

    }
}
