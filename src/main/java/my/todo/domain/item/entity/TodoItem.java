package my.todo.domain.item.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.todo.domain.base.entity.ModifyTime;
import my.todo.domain.user.entity.User;

@Entity
@Table(name = "item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class TodoItem extends ModifyTime {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "item_id")
  private Long id;

  private Long number;

  @Size(max = 100)
  private String title;

  @Size(max = 255)
  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  public TodoItem(Long id, Long number, @Size(max = 100) String title, @Size(max = 255) String description, User user) {
    this.id = id;
    this.number = number;
    this.title = title;
    this.description = description;
    this.user = user;
  }

  public void registUser(User user) {
    this.user = user;
  }

  public void updateTitle(String title) {
    this.title = title;
  }

  public void updateDescription(String description) {
    this.description = description;
  } 

}
