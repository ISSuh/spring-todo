package my.todo.services;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.todo.domain.user.dto.SignInDto;
import my.todo.domain.user.dto.SignUpDto;
import my.todo.domain.user.dto.UserDto;
import my.todo.domain.user.entity.Role;
import my.todo.domain.user.entity.User;
import my.todo.domain.user.repository.UserRepository;
import my.todo.domain.user.service.UserService;
import my.todo.infrastructure.security.jwt.JwtTokenProvider;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
  
  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public UserDto create(SignUpDto signUp) {
    Optional<UserDto> finded = userRepository.findUserDtoByName(signUp.getUsername());
    if (finded.isPresent()) {
      throw new IllegalArgumentException("already exist username");
    }

    finded = userRepository.findUserDtoByName(signUp.getEmail());
    if (finded.isPresent()) {
      throw new IllegalArgumentException("already exist email");
    }

    String encodedPw = passwordEncoder.encode(signUp.getPassword());
    User user =
      User.builder()
        .username(signUp.getUsername())
        .email(signUp.getEmail())
        .password(encodedPw)
        .role(Role.ROLE_USER)
        .build();
    
    return new UserDto(userRepository.saveUser(user));
  }

  @Override
  public UserDto login(SignInDto signIn) {
    String username = signIn.getUsername();

    Optional<User> finded = userRepository.findUserByName(username);
    if (finded.isEmpty()) {
      throw new IllegalArgumentException("not found username. " + username);
    }

    User user = finded.get();
    if (!passwordEncoder.matches(signIn.getPassword(), user.getPassword())) {
      throw new IllegalArgumentException("not match password");
    }

    UserDto dto = new UserDto(user);
    dto.setToken(jwtTokenProvider.generateToken(username, Role.ROLE_USER.name()));
    return dto;
  }

}
