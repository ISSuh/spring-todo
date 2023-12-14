package my.todo.domain.user.entity;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapKey;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.todo.domain.base.entity.ModifyTime;
import my.todo.domain.item.entity.TodoItem;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class User extends ModifyTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
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
  @MapKey(name="number")
  private Map<Long, TodoItem> items = new HashMap<>();

  @Column(name = "item_number_counter")
  @ColumnDefault("0")
  @Builder.Default()
  private Long itemNumberCounter = 0L;

  public User(
    Long id,
    @NotBlank @Size(min = 5, max = 50) String email,
    @NotBlank @Size(min = 5, max = 20) String username,
    @NotBlank @Size(min = 1, max = 150) String password,
    Role role,
    Map<Long, TodoItem> items,
    Long itemNumberCounter) {
    this.id = id;
    this.email = email;
    this.username = username;
    this.password = password;
    this.role = role;
    this.items = items;
    this.itemNumberCounter = itemNumberCounter;
  }

  public void addItem(TodoItem item) {
    this.items.put(item.getNumber(), item);
    item.registUser(this);
  }

  public void removeItem(Long id) {
    this.items.remove(id);
  }
  
  public Long nextItemNumber() {
    itemNumberCounter++;
    return itemNumberCounter;
  }

}
