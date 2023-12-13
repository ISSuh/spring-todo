package my.todo.api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.todo.domain.item.service.TodoItemService;

@RestController(value = "/api")
@RequiredArgsConstructor
@Slf4j
public class TodoItemRestController {
  
  private TodoItemService todoItemService;

  @PostMapping("/{userId}/item/{number}")
  void newItem(
    @PathVariable Long userId,
    @PathVariable Long number) {
    log.info("[TodoItemRestController][newItem] userId={}, number={}", userId, number);
    todoItemService.create();
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

  @PatchMapping("/{userId}/item/{number}")
  void update(
    @PathVariable Long userId,
    @PathVariable Long number) {
    log.info("[TodoItemRestController][update] userId={}, number={}", userId, number);
  }

  @DeleteMapping("/{userId}/item/{number}")
  void remove(
    @PathVariable Long userId,
    @PathVariable Long number) {
    log.info("[TodoItemRestController][remove] userId={}, number={}", userId, number);
  }

}
