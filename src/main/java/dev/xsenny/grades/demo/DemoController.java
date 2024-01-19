package dev.xsenny.grades.demo;

import dev.xsenny.grades.user.User;
import dev.xsenny.grades.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user-info")
@RequiredArgsConstructor
public class DemoController {

    private final UserService demoService;

    @GetMapping
    public ResponseEntity<User> sayHello(HttpServletRequest request) {
        if (demoService.getUser(request) == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(demoService.getUser(request));
    }

    @GetMapping("/salut")
    public String sa() {
        return "Salutare";
    }

}
