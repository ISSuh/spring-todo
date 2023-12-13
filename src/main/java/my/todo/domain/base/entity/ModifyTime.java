package my.todo.domain.base.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class ModifyTime {

  @Temporal(TemporalType.TIMESTAMP)
  @CreatedDate
  private LocalDateTime createAt;

  @Temporal(TemporalType.TIMESTAMP)
  @LastModifiedDate
  private LocalDateTime modifyAt;

}
