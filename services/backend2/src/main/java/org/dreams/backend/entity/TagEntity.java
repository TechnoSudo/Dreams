package org.dreams.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.data.relational.core.mapping.Table;

@Entity
@Table(name = "tag")
public record TagEntity(
        @Id
        String tag,

        String name
) {
}
