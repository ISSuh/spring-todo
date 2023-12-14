package my.todo.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.todo.domain.user.dto.SignInDto;
import my.todo.domain.user.dto.SignUpDto;
import my.todo.domain.user.dto.UserDto;
import my.todo.domain.user.service.UserService;

@RestController
@RequestMapping("/api/sign")
@RequiredArgsConstructor
@Slf4j
public class AccountRestController {
  
  private final  UserService userService;

  @PostMapping("/signup")
  UserDto newUser(@RequestBody SignUpDto signUp) {
    log.info("[AccountRestController][newUser] SignUpDto={}", signUp);
    return userService.create(signUp);
  }

  @PostMapping("/signin")
  UserDto login(@RequestBody SignInDto signIn) {
    log.info("[AccountRestController][login] signIn={}", signIn);
    return userService.login(signIn);
  }

}
