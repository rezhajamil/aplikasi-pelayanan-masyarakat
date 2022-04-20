package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Orders type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Orders", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class Orders implements Model {
  public static final QueryField ID = field("Orders", "id");
  public static final QueryField USER_ID = field("Orders", "userId");
  public static final QueryField PURPOSES = field("Orders", "purposes");
  public static final QueryField KTP = field("Orders", "ktp");
  public static final QueryField KK = field("Orders", "kk");
  public static final QueryField STATUS = field("Orders", "status");
  public static final QueryField DESCRIPTION = field("Orders", "description");
  public static final QueryField CREATED_AT = field("Orders", "createdAt");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="ID", isRequired = true) String userId;
  private final @ModelField(targetType="String", isRequired = true) List<String> purposes;
  private final @ModelField(targetType="String") String ktp;
  private final @ModelField(targetType="String") String kk;
  private final @ModelField(targetType="String") String status;
  private final @ModelField(targetType="String", isRequired = true) String description;
  private final @ModelField(targetType="AWSDateTime", isRequired = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getUserId() {
      return userId;
  }
  
  public List<String> getPurposes() {
      return purposes;
  }
  
  public String getKtp() {
      return ktp;
  }
  
  public String getKk() {
      return kk;
  }
  
  public String getStatus() {
      return status;
  }
  
  public String getDescription() {
      return description;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Orders(String id, String userId, List<String> purposes, String ktp, String kk, String status, String description, Temporal.DateTime createdAt) {
    this.id = id;
    this.userId = userId;
    this.purposes = purposes;
    this.ktp = ktp;
    this.kk = kk;
    this.status = status;
    this.description = description;
    this.createdAt = createdAt;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Orders orders = (Orders) obj;
      return ObjectsCompat.equals(getId(), orders.getId()) &&
              ObjectsCompat.equals(getUserId(), orders.getUserId()) &&
              ObjectsCompat.equals(getPurposes(), orders.getPurposes()) &&
              ObjectsCompat.equals(getKtp(), orders.getKtp()) &&
              ObjectsCompat.equals(getKk(), orders.getKk()) &&
              ObjectsCompat.equals(getStatus(), orders.getStatus()) &&
              ObjectsCompat.equals(getDescription(), orders.getDescription()) &&
              ObjectsCompat.equals(getCreatedAt(), orders.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), orders.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUserId())
      .append(getPurposes())
      .append(getKtp())
      .append(getKk())
      .append(getStatus())
      .append(getDescription())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Orders {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("userId=" + String.valueOf(getUserId()) + ", ")
      .append("purposes=" + String.valueOf(getPurposes()) + ", ")
      .append("ktp=" + String.valueOf(getKtp()) + ", ")
      .append("kk=" + String.valueOf(getKk()) + ", ")
      .append("status=" + String.valueOf(getStatus()) + ", ")
      .append("description=" + String.valueOf(getDescription()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static UserIdStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static Orders justId(String id) {
    return new Orders(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      userId,
      purposes,
      ktp,
      kk,
      status,
      description,
      createdAt);
  }
  public interface UserIdStep {
    PurposesStep userId(String userId);
  }
  

  public interface PurposesStep {
    DescriptionStep purposes(List<String> purposes);
  }
  

  public interface DescriptionStep {
    CreatedAtStep description(String description);
  }
  

  public interface CreatedAtStep {
    BuildStep createdAt(Temporal.DateTime createdAt);
  }
  

  public interface BuildStep {
    Orders build();
    BuildStep id(String id);
    BuildStep ktp(String ktp);
    BuildStep kk(String kk);
    BuildStep status(String status);
  }
  

  public static class Builder implements UserIdStep, PurposesStep, DescriptionStep, CreatedAtStep, BuildStep {
    private String id;
    private String userId;
    private List<String> purposes;
    private String description;
    private Temporal.DateTime createdAt;
    private String ktp;
    private String kk;
    private String status;
    @Override
     public Orders build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Orders(
          id,
          userId,
          purposes,
          ktp,
          kk,
          status,
          description,
          createdAt);
    }
    
    @Override
     public PurposesStep userId(String userId) {
        Objects.requireNonNull(userId);
        this.userId = userId;
        return this;
    }
    
    @Override
     public DescriptionStep purposes(List<String> purposes) {
        Objects.requireNonNull(purposes);
        this.purposes = purposes;
        return this;
    }
    
    @Override
     public CreatedAtStep description(String description) {
        Objects.requireNonNull(description);
        this.description = description;
        return this;
    }
    
    @Override
     public BuildStep createdAt(Temporal.DateTime createdAt) {
        Objects.requireNonNull(createdAt);
        this.createdAt = createdAt;
        return this;
    }
    
    @Override
     public BuildStep ktp(String ktp) {
        this.ktp = ktp;
        return this;
    }
    
    @Override
     public BuildStep kk(String kk) {
        this.kk = kk;
        return this;
    }
    
    @Override
     public BuildStep status(String status) {
        this.status = status;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String userId, List<String> purposes, String ktp, String kk, String status, String description, Temporal.DateTime createdAt) {
      super.id(id);
      super.userId(userId)
        .purposes(purposes)
        .description(description)
        .createdAt(createdAt)
        .ktp(ktp)
        .kk(kk)
        .status(status);
    }
    
    @Override
     public CopyOfBuilder userId(String userId) {
      return (CopyOfBuilder) super.userId(userId);
    }
    
    @Override
     public CopyOfBuilder purposes(List<String> purposes) {
      return (CopyOfBuilder) super.purposes(purposes);
    }
    
    @Override
     public CopyOfBuilder description(String description) {
      return (CopyOfBuilder) super.description(description);
    }
    
    @Override
     public CopyOfBuilder createdAt(Temporal.DateTime createdAt) {
      return (CopyOfBuilder) super.createdAt(createdAt);
    }
    
    @Override
     public CopyOfBuilder ktp(String ktp) {
      return (CopyOfBuilder) super.ktp(ktp);
    }
    
    @Override
     public CopyOfBuilder kk(String kk) {
      return (CopyOfBuilder) super.kk(kk);
    }
    
    @Override
     public CopyOfBuilder status(String status) {
      return (CopyOfBuilder) super.status(status);
    }
  }
  
}
