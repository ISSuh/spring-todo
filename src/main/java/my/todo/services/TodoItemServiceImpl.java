package my.todo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.todo.domain.item.dto.TodoItemDto;
import my.todo.domain.item.entity.TodoItem;
import my.todo.domain.item.repository.TodoItemRepository;
import my.todo.domain.item.service.TodoItemService;
import my.todo.domain.user.entity.User;
import my.todo.domain.user.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TodoItemServiceImpl implements TodoItemService {
  
  private final TodoItemRepository todoItemRepository;
  private final UserRepository userRepository;

  @Override
  public TodoItemDto create(Long userId, TodoItemDto todoItemDto) {
    Optional<User> user = userRepository.findUser(userId);
    if (user.isEmpty()) {
      throw new IllegalArgumentException("not found user. " + userId);
    }

    Long number = user.get().nextItemNumber();

    TodoItem item =
      TodoItem.builder()
        .title(todoItemDto.getTitle())
        .description(todoItemDto.getDescription())
        .number(number)
        .build();

    TodoItem saved = todoItemRepository.saveItem(item);
    user.get().addItem(saved);
    return new TodoItemDto(saved);
  }

  @Override
  @Transactional(readOnly = true)
  public TodoItemDto findItem(Long userId, Long number) {
    TodoItem item = find(userId, number);
    return new TodoItemDto(item);
  }

  @Override
  public List<TodoItemDto> findItems(Long userId) {
    return null;
  }

  @Override
  public void remove(Long userId, Long number) {
    TodoItem item = find(userId, number);
    todoItemRepository.removeItem(item.getId());
  }

  @Override
  public TodoItemDto update(Long userId, Long number, TodoItemDto itemDto) {
    TodoItem item = find(userId, number);

    if (itemDto.getTitle() != null) {
      item.updateTitle(itemDto.getTitle());
    }

    if (itemDto.getDescription() != null) {
      log.info("[update] before item={}", item);
      item.updateDescription(itemDto.getDescription());
    }

    log.info("[update] after item={}", item);

    return new TodoItemDto(item);
  }

  private TodoItem find(Long userId, Long number) {
    Optional<TodoItem> item = todoItemRepository.findByNumber(userId, number);
    if (item.isEmpty()) {
      throw new IllegalArgumentException("not found item. " + number);
    }

    if (userId != item.get().getUser().getId()) {
      throw new IllegalArgumentException("not match user and item. ");
    }
    return item.get();
  }

}
