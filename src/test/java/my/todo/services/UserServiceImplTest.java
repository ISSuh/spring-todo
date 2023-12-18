package my.todo.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import my.todo.domain.user.dto.SignInDto;
import my.todo.domain.user.dto.SignUpDto;
import my.todo.domain.user.dto.UserDto;
import my.todo.domain.user.entity.Role;
import my.todo.domain.user.service.UserService;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
public class UserServiceImplTest {

  @Autowired
  private UserService userService;

  @Test
  void signUp() {
    // given
    String username = "user_100";
    String email = username + "@gmail.com";
    String password = username;

    SignUpDto signUpDto = new SignUpDto();
    signUpDto.setEmail(email);
    signUpDto.setUsername(username);
    signUpDto.setPassword(password);

    // when
    UserDto dto = userService.create(signUpDto);

    // then
    Assertions.assertThat(dto.getUsername()).isEqualTo(username);
    Assertions.assertThat(dto.getEmail()).isEqualTo(email);
    Assertions.assertThat(dto.getRole()).isEqualTo(Role.ROLE_USER.toString());
  }

  @Test
  void signUpFailNoEmail() {
    // given
    String username = "user_1";
    String password = username;

    SignUpDto signUpDto = new SignUpDto();
    signUpDto.setUsername(username);
    signUpDto.setPassword(password);

    // when
    // then
    Assertions.assertThatThrownBy(
      () -> userService.create(signUpDto))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void signUpFailNoUsername() {
    // given
    String email = "test@gmail.com";
    String password = "password";

    SignUpDto signUpDto = new SignUpDto();
    signUpDto.setEmail(email);
    signUpDto.setPassword(password);

    // when
    // then
    Assertions.assertThatThrownBy(
      () -> userService.create(signUpDto))
        .isInstanceOf(ConstraintViolationException.class);
  }

  @Test
  void signUpFailNoPassword() {
    // given
    String username = "user_1";
    String email = username + "@gmail.com";

    SignUpDto signUpDto = new SignUpDto();
    signUpDto.setEmail(email);
    signUpDto.setUsername(username);

    // when
    // then
    Assertions.assertThatThrownBy(
      () -> userService.create(signUpDto))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void signIn() {
    // given
    String username = "user_200";
    String email = username + "@gmail.com";
    String password = username;

    SignUpDto signUpDto = new SignUpDto();
    signUpDto.setEmail(email);
    signUpDto.setUsername(username);
    signUpDto.setPassword(password);

    UserDto dto = userService.create(signUpDto);

    SignInDto signIn = new SignInDto();
    signIn.setUsername(username);
    signIn.setPassword(password);

    // when
    dto = userService.login(signIn);

    // then
    Assertions.assertThat(dto.getUsername()).isEqualTo(username);
    Assertions.assertThat(dto.getEmail()).isEqualTo(email);
    Assertions.assertThat(dto.getRole()).isEqualTo(Role.ROLE_USER.toString());
    Assertions.assertThat(dto.getToken()).isNotEmpty();
  }
}
