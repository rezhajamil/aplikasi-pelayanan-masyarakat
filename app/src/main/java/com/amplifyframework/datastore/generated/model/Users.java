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

/** This is an auto generated class representing the Users type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Users", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class Users implements Model {
  public static final QueryField ID = field("Users", "id");
  public static final QueryField EMAIL = field("Users", "email");
  public static final QueryField PHONE = field("Users", "phone");
  public static final QueryField NIK = field("Users", "nik");
  public static final QueryField ADDRESS = field("Users", "address");
  public static final QueryField NAME = field("Users", "name");
  public static final QueryField BIRTH_DATE = field("Users", "birthDate");
  public static final QueryField BIRTH_PLACE = field("Users", "birthPlace");
  public static final QueryField AVATAR = field("Users", "avatar");
  public static final QueryField OCCUPATION = field("Users", "occupation");
  public static final QueryField ROLE = field("Users", "role");
  public static final QueryField EMAIL_VERIFIED_AT = field("Users", "emailVerifiedAt");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="AWSEmail", isRequired = true) String email;
  private final @ModelField(targetType="String", isRequired = true) String phone;
  private final @ModelField(targetType="String", isRequired = true) String nik;
  private final @ModelField(targetType="String", isRequired = true) String address;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="AWSDate", isRequired = true) Temporal.Date birthDate;
  private final @ModelField(targetType="String") String birthPlace;
  private final @ModelField(targetType="String") String avatar;
  private final @ModelField(targetType="String", isRequired = true) String occupation;
  private final @ModelField(targetType="String", isRequired = true) String role;
  private final @ModelField(targetType="AWSDateTime") Temporal.DateTime emailVerifiedAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getEmail() {
      return email;
  }
  
  public String getPhone() {
      return phone;
  }
  
  public String getNik() {
      return nik;
  }
  
  public String getAddress() {
      return address;
  }
  
  public String getName() {
      return name;
  }
  
  public Temporal.Date getBirthDate() {
      return birthDate;
  }
  
  public String getBirthPlace() {
      return birthPlace;
  }
  
  public String getAvatar() {
      return avatar;
  }
  
  public String getOccupation() {
      return occupation;
  }
  
  public String getRole() {
      return role;
  }
  
  public Temporal.DateTime getEmailVerifiedAt() {
      return emailVerifiedAt;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Users(String id, String email, String phone, String nik, String address, String name, Temporal.Date birthDate, String birthPlace, String avatar, String occupation, String role, Temporal.DateTime emailVerifiedAt) {
    this.id = id;
    this.email = email;
    this.phone = phone;
    this.nik = nik;
    this.address = address;
    this.name = name;
    this.birthDate = birthDate;
    this.birthPlace = birthPlace;
    this.avatar = avatar;
    this.occupation = occupation;
    this.role = role;
    this.emailVerifiedAt = emailVerifiedAt;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Users users = (Users) obj;
      return ObjectsCompat.equals(getId(), users.getId()) &&
              ObjectsCompat.equals(getEmail(), users.getEmail()) &&
              ObjectsCompat.equals(getPhone(), users.getPhone()) &&
              ObjectsCompat.equals(getNik(), users.getNik()) &&
              ObjectsCompat.equals(getAddress(), users.getAddress()) &&
              ObjectsCompat.equals(getName(), users.getName()) &&
              ObjectsCompat.equals(getBirthDate(), users.getBirthDate()) &&
              ObjectsCompat.equals(getBirthPlace(), users.getBirthPlace()) &&
              ObjectsCompat.equals(getAvatar(), users.getAvatar()) &&
              ObjectsCompat.equals(getOccupation(), users.getOccupation()) &&
              ObjectsCompat.equals(getRole(), users.getRole()) &&
              ObjectsCompat.equals(getEmailVerifiedAt(), users.getEmailVerifiedAt()) &&
              ObjectsCompat.equals(getCreatedAt(), users.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), users.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getEmail())
      .append(getPhone())
      .append(getNik())
      .append(getAddress())
      .append(getName())
      .append(getBirthDate())
      .append(getBirthPlace())
      .append(getAvatar())
      .append(getOccupation())
      .append(getRole())
      .append(getEmailVerifiedAt())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Users {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("email=" + String.valueOf(getEmail()) + ", ")
      .append("phone=" + String.valueOf(getPhone()) + ", ")
      .append("nik=" + String.valueOf(getNik()) + ", ")
      .append("address=" + String.valueOf(getAddress()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("birthDate=" + String.valueOf(getBirthDate()) + ", ")
      .append("birthPlace=" + String.valueOf(getBirthPlace()) + ", ")
      .append("avatar=" + String.valueOf(getAvatar()) + ", ")
      .append("occupation=" + String.valueOf(getOccupation()) + ", ")
      .append("role=" + String.valueOf(getRole()) + ", ")
      .append("emailVerifiedAt=" + String.valueOf(getEmailVerifiedAt()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static EmailStep builder() {
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
  public static Users justId(String id) {
    return new Users(
      id,
      null,
      null,
      null,
      null,
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
      email,
      phone,
      nik,
      address,
      name,
      birthDate,
      birthPlace,
      avatar,
      occupation,
      role,
      emailVerifiedAt);
  }
  public interface EmailStep {
    PhoneStep email(String email);
  }
  

  public interface PhoneStep {
    NikStep phone(String phone);
  }
  

  public interface NikStep {
    AddressStep nik(String nik);
  }
  

  public interface AddressStep {
    NameStep address(String address);
  }
  

  public interface NameStep {
    BirthDateStep name(String name);
  }
  

  public interface BirthDateStep {
    OccupationStep birthDate(Temporal.Date birthDate);
  }
  

  public interface OccupationStep {
    RoleStep occupation(String occupation);
  }
  

  public interface RoleStep {
    BuildStep role(String role);
  }
  

  public interface BuildStep {
    Users build();
    BuildStep id(String id);
    BuildStep birthPlace(String birthPlace);
    BuildStep avatar(String avatar);
    BuildStep emailVerifiedAt(Temporal.DateTime emailVerifiedAt);
  }
  

  public static class Builder implements EmailStep, PhoneStep, NikStep, AddressStep, NameStep, BirthDateStep, OccupationStep, RoleStep, BuildStep {
    private String id;
    private String email;
    private String phone;
    private String nik;
    private String address;
    private String name;
    private Temporal.Date birthDate;
    private String occupation;
    private String role;
    private String birthPlace;
    private String avatar;
    private Temporal.DateTime emailVerifiedAt;
    @Override
     public Users build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Users(
          id,
          email,
          phone,
          nik,
          address,
          name,
          birthDate,
          birthPlace,
          avatar,
          occupation,
          role,
          emailVerifiedAt);
    }
    
    @Override
     public PhoneStep email(String email) {
        Objects.requireNonNull(email);
        this.email = email;
        return this;
    }
    
    @Override
     public NikStep phone(String phone) {
        Objects.requireNonNull(phone);
        this.phone = phone;
        return this;
    }
    
    @Override
     public AddressStep nik(String nik) {
        Objects.requireNonNull(nik);
        this.nik = nik;
        return this;
    }
    
    @Override
     public NameStep address(String address) {
        Objects.requireNonNull(address);
        this.address = address;
        return this;
    }
    
    @Override
     public BirthDateStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public OccupationStep birthDate(Temporal.Date birthDate) {
        Objects.requireNonNull(birthDate);
        this.birthDate = birthDate;
        return this;
    }
    
    @Override
     public RoleStep occupation(String occupation) {
        Objects.requireNonNull(occupation);
        this.occupation = occupation;
        return this;
    }
    
    @Override
     public BuildStep role(String role) {
        Objects.requireNonNull(role);
        this.role = role;
        return this;
    }
    
    @Override
     public BuildStep birthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
        return this;
    }
    
    @Override
     public BuildStep avatar(String avatar) {
        this.avatar = avatar;
        return this;
    }
    
    @Override
     public BuildStep emailVerifiedAt(Temporal.DateTime emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
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
    private CopyOfBuilder(String id, String email, String phone, String nik, String address, String name, Temporal.Date birthDate, String birthPlace, String avatar, String occupation, String role, Temporal.DateTime emailVerifiedAt) {
      super.id(id);
      super.email(email)
        .phone(phone)
        .nik(nik)
        .address(address)
        .name(name)
        .birthDate(birthDate)
        .occupation(occupation)
        .role(role)
        .birthPlace(birthPlace)
        .avatar(avatar)
        .emailVerifiedAt(emailVerifiedAt);
    }
    
    @Override
     public CopyOfBuilder email(String email) {
      return (CopyOfBuilder) super.email(email);
    }
    
    @Override
     public CopyOfBuilder phone(String phone) {
      return (CopyOfBuilder) super.phone(phone);
    }
    
    @Override
     public CopyOfBuilder nik(String nik) {
      return (CopyOfBuilder) super.nik(nik);
    }
    
    @Override
     public CopyOfBuilder address(String address) {
      return (CopyOfBuilder) super.address(address);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder birthDate(Temporal.Date birthDate) {
      return (CopyOfBuilder) super.birthDate(birthDate);
    }
    
    @Override
     public CopyOfBuilder occupation(String occupation) {
      return (CopyOfBuilder) super.occupation(occupation);
    }
    
    @Override
     public CopyOfBuilder role(String role) {
      return (CopyOfBuilder) super.role(role);
    }
    
    @Override
     public CopyOfBuilder birthPlace(String birthPlace) {
      return (CopyOfBuilder) super.birthPlace(birthPlace);
    }
    
    @Override
     public CopyOfBuilder avatar(String avatar) {
      return (CopyOfBuilder) super.avatar(avatar);
    }
    
    @Override
     public CopyOfBuilder emailVerifiedAt(Temporal.DateTime emailVerifiedAt) {
      return (CopyOfBuilder) super.emailVerifiedAt(emailVerifiedAt);
    }
  }
  
}
