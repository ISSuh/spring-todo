package my.todo.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import my.todo.domain.user.service.UserService;

@RestController(value = "/api/sign")
@RequiredArgsConstructor
public class AccountRestController {
  
  private UserService userService;

  @PostMapping("/signup")
  void newUser() {

  }

  @PostMapping("/signin")
  void login() {

  }

}
