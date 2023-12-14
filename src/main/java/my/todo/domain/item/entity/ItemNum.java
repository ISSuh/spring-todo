package my.todo.domain.item.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "item_number")
@Getter
public class ItemNum {
  
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  Long number;

}
