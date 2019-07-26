package com.revised.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@JsonIgnoreProperties(
    value = {"createdDate", "modifiedDate"},
    allowGetters = true)
@EntityListeners(AuditingEntityListener.class)
public class DateAudit implements Serializable {

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private Instant createdDate;

  @LastModifiedDate
  @Column(nullable = false)
  private Instant modifiedDate;

  public Instant getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Instant createdDate) {
    this.createdDate = createdDate;
  }

  public Instant getModifiedDate() {
    return modifiedDate;
  }

  public void setModifiedDate(Instant modifiedDate) {
    this.modifiedDate = modifiedDate;
  }
}
