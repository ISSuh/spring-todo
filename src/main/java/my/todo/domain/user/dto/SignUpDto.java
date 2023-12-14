package my.todo.domain.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignUpDto {

  private String email;

  private String username;

  private String password;
  
}
