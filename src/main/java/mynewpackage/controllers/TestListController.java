package mynewpackage.controllers;

import mynewpackage.domain.Test;
import mynewpackage.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tests")
public class TestListController {

    @Autowired
    private TestService testService;

    @GetMapping("/")
    public List<Test> readAll() {
        return testService.allTests();
    }

    @GetMapping("/{id}")
    public Test readAuthorsTests(@PathVariable("id") Long id) {
        return testService.getTestWithId(id);
    }
}
