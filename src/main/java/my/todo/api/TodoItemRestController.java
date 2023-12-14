package my.todo.api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.todo.domain.item.dto.TodoItemDto;
import my.todo.domain.item.service.TodoItemService;

@RestController
@RequestMapping("/api/todo")
@RequiredArgsConstructor
@Slf4j
public class TodoItemRestController {
  
  private final TodoItemService todoItemService;

  @PostMapping("/{userId}/item")
  void newItem(
    @PathVariable Long userId,
    @RequestBody TodoItemDto itemDto) {
    log.info("[TodoItemRestController][newItem] userId={}", userId);
    todoItemService.create(userId, itemDto);
  }

  @GetMapping("/{userId}/item")
  void items(
    @PathVariable Long userId) {
    log.info("[TodoItemRestController][items] userId={}", userId);
    todoItemService.findItems(userId);
  }

  @GetMapping("/{userId}/item/{number}")
  void item(
    @PathVariable Long userId,
    @PathVariable Long number) {
    log.info("[TodoItemRestController][item] userId={}, number={}", userId, number);
    todoItemService.findItem(userId, number);
  }

  @DeleteMapping("/{userId}/item/{number}")
  void remove(
    @PathVariable Long userId,
    @PathVariable Long number) {
    log.info("[TodoItemRestController][remove] userId={}, number={}", userId, number);
    todoItemService.remove(userId, number);
  }

  @PatchMapping("/{userId}/item/{number}")
  TodoItemDto update(
    @PathVariable Long userId,
    @PathVariable Long number,
    @RequestBody TodoItemDto itemDto) {
    log.info("[TodoItemRestController][update] userId={}, number={}", userId, number);
    return todoItemService.update(userId, number, itemDto);
  }

}
