package my.todo.infrastructure.repository.jps;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import my.todo.domain.item.dto.TodoItemDto;
import my.todo.domain.item.entity.TodoItem;
import my.todo.domain.item.repository.TodoItemRepository;

public interface TodoItemJpaRepository extends JpaRepository<TodoItem, Long>, TodoItemRepository {

  TodoItem save(TodoItem user);

  void deleteById(Long id);

  Optional<TodoItem> findItemById(Long id);

  @Query(
    "select i " +
    "from TodoItem i " +
    "where i.number = :number and i.user.id = :userId")
  Optional<TodoItem> findItemByNumber(Long userId, Long number);

  @Query(
    "select new my.todo.domain.item.dto.TodoItemDto(i) " +
    "from TodoItem i " +
    "where i.id = :id")
  Optional<TodoItemDto> findDtoById(Long id);

  @Query(
    "select new my.todo.domain.item.dto.TodoItemDto(i) " +
    "from TodoItem i " +
    "where i.number = :number and i.user.id = :userId")
  Optional<TodoItemDto> findDtoByNumber(Long userId, Long number);

  default TodoItem saveItem(TodoItem user) {
    return save(user);
  }

  default void removeItem(Long id) {
    deleteById(id);
  }

  default Optional<TodoItem> findItem(Long id) {
    return findItemById(id);
  }

  default Optional<TodoItem> findByNumber(Long userId, Long number) {
    return findItemByNumber(userId, number);
  }
  
  default Optional<TodoItemDto> findItemDtoById(Long id) {
    return findDtoById(id);
  }

  default Optional<TodoItemDto> findItemDtoByNumber(Long userId, Long number) {
    return findDtoByNumber(userId, number);
  }

}
