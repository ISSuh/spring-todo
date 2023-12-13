package my.todo.domain.user.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.todo.domain.base.entity.ModifyTime;
import my.todo.domain.item.entity.TodoItem;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends ModifyTime {

  @Id
  @GeneratedValue
  private Long id;

  @Column(unique = true)
  @NotBlank
  @Size(min = 5, max = 50)
  private String email;

  @Column(unique = true)
  @NotBlank
  @Size(min = 5, max = 20)
  private String username;
  
  @NotBlank
  @Size(min = 1, max = 150)
  private String password;

  @Enumerated(EnumType.STRING)
  private Role role;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  List<TodoItem> items = new ArrayList<>();

}
