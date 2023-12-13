package my.todo.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import my.todo.domain.item.dto.TodoItemDto;
import my.todo.domain.item.repository.TodoItemRepository;
import my.todo.domain.item.service.TodoItemService;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoItemServiceImpl implements TodoItemService {
  
  private final TodoItemRepository todoItemRepository;

  @Override
  public TodoItemDto create() {
    return null;
  }

  @Override
  public TodoItemDto findItem(Long userId, Long number) {
    return null;
  }

  @Override
  public List<TodoItemDto> findItems(Long userId) {
    return null;
  }

  @Override
  public void remove(Long number) {    
  }

  @Override
  public void update(TodoItemDto item) {    
  }

}
