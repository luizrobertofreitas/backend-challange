package com.invillia.acme.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "providers")
public class ProviderEntity implements Serializable {

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;

  @NotEmpty
  private String name, address;

  @NotNull
  private LocalDateTime createdAt;

  @Builder
  public ProviderEntity(final String name, final String address) {
    this.name = name;
    this.address = address;
  }

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
