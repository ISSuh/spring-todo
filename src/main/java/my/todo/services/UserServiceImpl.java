package my.todo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import my.todo.domain.user.dto.UserDto;
import my.todo.domain.user.repository.UserRepository;
import my.todo.domain.user.service.UserService;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  
  private final UserRepository userRepository;

  @Override
  public UserDto create() {
    return null;
  }

}
