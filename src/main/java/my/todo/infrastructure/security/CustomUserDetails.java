package my.todo.infrastructure.security;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import my.todo.domain.user.entity.Role;
import my.todo.domain.user.entity.User;

@Data
public class CustomUserDetails implements UserDetails {

  private final User user;

  public CustomUserDetails(User user) {
    this.user = user;
  }
  
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<String> names = getRoleNames();
    Collection<GrantedAuthority> authorities =
      names.stream()
        .map((name) -> new SimpleGrantedAuthority(name))
        .collect(Collectors.toList());
    return authorities;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  private List<String> getRoleNames() {
  return EnumSet.allOf(Role.class).stream().map(
    role -> role.name()).collect(Collectors.toList());
  }

}
