package my.todo.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import my.todo.domain.item.dto.TodoItemDto;
import my.todo.domain.item.service.TodoItemService;
import my.todo.domain.user.dto.SignUpDto;
import my.todo.domain.user.service.UserService;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
public class TodoItemServiceImplTest {
  
  @Autowired
  private UserService userService;

  @Autowired
  private TodoItemService todoItemService;

  @BeforeAll
  public void beforAll() {
    for (int i = 0 ; i < 5 ; i++) {
      String username = "user_" + i;
      String email = username + "@gmail.com";
      String password = username;

      SignUpDto signUpDto = new SignUpDto();
      signUpDto.setEmail(email);
      signUpDto.setUsername(username);
      signUpDto.setPassword(password);

      userService.create(signUpDto);
    }
  }


  @Test
  public void create() {
    // given
    Long userId = 1L;
    String title = "title";
    String description = "description";

    TodoItemDto todoItemDto = new TodoItemDto();
    todoItemDto.setTitle(title);
    todoItemDto.setDescription(description);

    // when
    TodoItemDto dto = todoItemService.create(userId, todoItemDto);
    log.info("[TEST][create] dto={}", dto);

    // then
    Assertions.assertThat(dto.getTitle()).isEqualTo(title);
    Assertions.assertThat(dto.getDescription()).isEqualTo(description);
  }

  @Test
  public void createFailNotFoundUser() {
    // given
    Long userId = 100L;
    String title = "title";
    String description = "description";

    // when
    TodoItemDto todoItemDto = new TodoItemDto();
    todoItemDto.setTitle(title);
    todoItemDto.setDescription(description);

    // then
    Assertions.assertThatThrownBy(
      () -> todoItemService.create(userId, todoItemDto))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void findItem() {
    // given
    Long userId = 2L;
    String title = "title";
    String description = "description";

    TodoItemDto todoItemDto = new TodoItemDto();
    todoItemDto.setTitle(title);
    todoItemDto.setDescription(description);

    TodoItemDto created = todoItemService.create(userId, todoItemDto);
    log.info("[TEST][create] dto={}", created);

    // when
    TodoItemDto dto = todoItemService.findItem(userId, created.getNumber());

    // then
    Assertions.assertThat(dto.getNumber()).isEqualTo(created.getNumber());
    Assertions.assertThat(dto.getTitle()).isEqualTo(created.getTitle());
    Assertions.assertThat(dto.getDescription()).isEqualTo(created.getDescription());
  }

  @Test
  public void updateItem() {
    // given
    Long userId = 3L;
    String title = "title";
    String description = "description";

    TodoItemDto todoItemDto = new TodoItemDto();
    todoItemDto.setTitle(title);
    todoItemDto.setDescription(description);

    TodoItemDto created = todoItemService.create(userId, todoItemDto);
    log.info("[TEST][create] created={}", created);

    // when
    String changeTitle = title + "_changed";
    String changedescription = description + "_changed";

    TodoItemDto changeItem = new TodoItemDto();
    changeItem.setTitle(changeTitle);
    todoItemDto.setDescription(changedescription);

    TodoItemDto dto = todoItemService.update(userId, created.getNumber(), changeItem);
    log.info("[TEST][create] dto={}", dto);

    // then
    Assertions.assertThat(dto.getNumber()).isEqualTo(created.getNumber());
    Assertions.assertThat(dto.getTitle()).isEqualTo(changeTitle);
    Assertions.assertThat(dto.getDescription()).isEqualTo(changedescription);
  }

}
