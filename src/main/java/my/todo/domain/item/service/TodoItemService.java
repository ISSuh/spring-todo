package my.todo.domain.item.service;

import java.util.List;

import my.todo.domain.item.dto.TodoItemDto;

public interface TodoItemService {
  
  TodoItemDto create();

  TodoItemDto findItem(Long userId, Long number);

  List<TodoItemDto> findItems(Long userId);

  void update(TodoItemDto item);

  void remove(Long number);

}
