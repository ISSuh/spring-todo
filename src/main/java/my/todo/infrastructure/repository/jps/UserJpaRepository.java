package my.todo.infrastructure.repository.jps;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import my.todo.domain.user.dto.UserDto;
import my.todo.domain.user.entity.User;
import my.todo.domain.user.repository.UserRepository;

public interface UserJpaRepository extends JpaRepository<User, Long>,  UserRepository {

  User save(User user);

  Optional<User> findUserById(Long id);

  Optional<User> findUserByUsername(String username);

  @Query(
    "select new my.todo.domain.user.dto.UserDto(u) " +
    "from User u " +
    "where u.id = :id"
  )
  Optional<UserDto> findDtoById(Long id);

  @Query(
    "select new my.todo.domain.user.dto.UserDto(u) " +
    "from User u " +
    "where u.username = :username"
  )
  Optional<UserDto> findDtoByName(String username);

  default User saveUser(User user) {
    return save(user);
  }

  default Optional<User> findUser(Long id) {
    return findUserById(id);
  }

  default Optional<User> findUserByName(String username) {
    return findUserByUsername(username);
  }

  default Optional<UserDto> findUserDtoById(Long id) {
    return findDtoById(id);
  }

  default Optional<UserDto> findUserDtoByName(String username) {
    return findDtoByName(username);
  }

}
