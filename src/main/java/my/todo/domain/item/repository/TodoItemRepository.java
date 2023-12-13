package my.todo.domain.item.repository;

import java.util.Optional;

import my.todo.domain.item.dto.TodoItemDto;
import my.todo.domain.item.entity.TodoItem;

public interface TodoItemRepository {
  
  TodoItem saveItem(TodoItem item);

  Optional<TodoItemDto> findItemDtoById(Long id);

  Optional<TodoItemDto> findItemDtoByNumber(Long number);

}
