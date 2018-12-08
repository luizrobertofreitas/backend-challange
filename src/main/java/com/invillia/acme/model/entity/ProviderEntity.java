package com.invillia.acme.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "provider")
public class ProviderEntity implements Serializable {

  @Id
  @GeneratedValue
  private Long id;

  @NotEmpty
  private String name, address;

  private LocalDateTime createdAt;

  public ProviderEntity updateWith(final ProviderEntity provider) {
    Optional.of(provider).ifPresent(p -> {
      this.setName(p.getName());
      this.setAddress(p.getAddress());
    });

    return this;
  }

  @PrePersist
  private void prePersist() {
    this.createdAt = LocalDateTime.now();
  }
}
