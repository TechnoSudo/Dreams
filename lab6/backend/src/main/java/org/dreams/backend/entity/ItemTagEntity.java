package org.dreams.backend.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "item_tag")
public class ItemTagEntity {

       @EmbeddedId
       private PK id;

       public ItemTagEntity() {
       }

       public ItemTagEntity(UUID itemUuid, String tag) {
              this.id = new PK(itemUuid, tag);
       }

       @Embeddable
       public static class PK implements Serializable {
              private UUID itemUuid;
              private String tag;

              public PK() {
              }

              public PK(UUID itemUuid, String tag) {
                     this.itemUuid = itemUuid;
                     this.tag = tag;
              }

              // Getters and setters
              public UUID getItemUuid() {
                     return itemUuid;
              }

              public void setItemUuid(UUID itemUuid) {
                     this.itemUuid = itemUuid;
              }

              public String getTag() {
                     return tag;
              }

              public void setTag(String tag) {
                     this.tag = tag;
              }

              @Override
              public boolean equals(Object o) {
                     if (this == o) return true;
                     if (o == null || getClass() != o.getClass()) return false;
                     PK pk = (PK) o;
                     return Objects.equals(itemUuid, pk.itemUuid) &&
                             Objects.equals(tag, pk.tag);
              }

              @Override
              public int hashCode() {
                     return Objects.hash(itemUuid, tag);
              }
       }

       public PK getId() {
              return id;
       }

       public void setId(PK id) {
              this.id = id;
       }
}