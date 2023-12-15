package my.todo.domain.item.service;

import java.util.List;

import my.todo.domain.item.dto.TodoItemDto;

public interface TodoItemService {
  
  TodoItemDto create(Long userId, TodoItemDto itemDto);

  TodoItemDto findItem(Long userId, Long number);

  List<TodoItemDto> findItems(Long userId);

  void remove(Long userId, Long number);

  TodoItemDto update(Long userId, Long number, TodoItemDto itemDto);


}
