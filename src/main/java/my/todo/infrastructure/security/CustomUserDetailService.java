package my.todo.infrastructure.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.todo.domain.user.entity.User;
import my.todo.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.info("[loadUserByUsername] username={}", username);
    
    Optional<User> user = userRepository.findUserByName(username);
    return new CustomUserDetails(
      user.orElseThrow(() -> new UsernameNotFoundException("not found. " + username)));
  }

}
