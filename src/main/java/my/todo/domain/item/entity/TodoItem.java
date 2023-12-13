package my.todo.domain.item.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.todo.domain.base.entity.ModifyTime;
import my.todo.domain.user.entity.User;

@Entity
@Table(name = "item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TodoItem extends ModifyTime {
  
  @Id
  @GeneratedValue
  @Column(name = "item_id")
  private Long id;

  @Column(unique = true)
  private Long number;

  @Size(max = 100)
  private String title;

  @Size(max = 255)
  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

}
