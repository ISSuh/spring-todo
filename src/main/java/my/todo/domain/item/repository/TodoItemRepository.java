package my.todo.domain.item.repository;

import java.util.Optional;

import my.todo.domain.item.dto.TodoItemDto;
import my.todo.domain.item.entity.TodoItem;

public interface TodoItemRepository {
  
  TodoItem saveItem(TodoItem item);

  void removeItem(Long id);

  Optional<TodoItem> findItem(Long id);

  Optional<TodoItem> findByNumber(Long userId, Long number);

  Optional<TodoItemDto> findItemDtoById(Long id);

  Optional<TodoItemDto> findItemDtoByNumber(Long userId, Long number);

}
