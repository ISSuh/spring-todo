package my.todo.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import my.todo.domain.user.service.UserService;

@RestController(value = "/api/user")
@RequiredArgsConstructor
public class UserRestController {
  
  private UserService userService;

  @PostMapping("/signup")
  void newUser() {

  }

  @PostMapping("/signin")
  void login() {

  }

  @GetMapping("/{id}")
  void user() {
    
  }

}
