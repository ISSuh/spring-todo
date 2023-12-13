package my.todo.domain.user.repository;

import java.util.Optional;

import my.todo.domain.user.dto.UserDto;
import my.todo.domain.user.entity.User;

public interface UserRepository {
  
  User saveUser(User user);

  Optional<User> findUserByName(String username);

  Optional<UserDto> findUserDtoById(Long id);

  Optional<UserDto> findUserDtoByName(String username);

}
