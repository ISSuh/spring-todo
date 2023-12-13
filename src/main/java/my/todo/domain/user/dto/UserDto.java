package my.todo.domain.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import my.todo.domain.user.entity.User;

@Data
@NoArgsConstructor
public class UserDto {
  
  private Long id;

  private String username;

  private String role;

  public UserDto(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.role = user.getRole().toString();
  }

}
