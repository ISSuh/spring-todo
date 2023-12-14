package my.todo.domain.user.service;

import my.todo.domain.user.dto.SignInDto;
import my.todo.domain.user.dto.SignUpDto;
import my.todo.domain.user.dto.UserDto;

public interface UserService {
  
  UserDto create(SignUpDto signUp);

  UserDto login(SignInDto signIn);

}
