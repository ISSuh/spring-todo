package my.todo.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;
import my.todo.domain.user.entity.User;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
  
  private Long id;

  private String email;

  private String username;

  private String role;

  private String token;

  public UserDto(User user) {
    this.id = user.getId();
    this.email = user.getEmail();
    this.username = user.getUsername();
    this.role = user.getRole().toString();
  }

}
