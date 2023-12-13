package my.todo.domain.item.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import my.todo.domain.item.entity.TodoItem;

@Data
@NoArgsConstructor
public class TodoItemDto {
  
  private Long number;

  private String title;

  private String description;

  public TodoItemDto(TodoItem item) {
    this.number = item.getNumber();
    this.title = item.getTitle();
    this.description = item.getDescription();
  }

}
