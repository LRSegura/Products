package com.manage.product.model;

import com.manage.product.api.annotation.InjectedDate;
import com.manage.product.api.annotation.InjectedDateType;
import com.manage.product.api.persistence.validation.HibernateEventHandlers;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


/**
 * Abstract representation for hibernate entity. It has common field for all entities.
 */
@Getter
@ToString
@MappedSuperclass
@EntityListeners(HibernateEventHandlers.class)
public class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    @Column(name = "Register_Date", nullable = false)
    @InjectedDate(dateType = InjectedDateType.REGISTER_DATE)
    private LocalDateTime registerDate;

    @Column(name = "Update_Date")
    @InjectedDate(dateType = InjectedDateType.UPDATE_DATE)
    private LocalDateTime updateDate;

    @Version
    @Column(name = "OptLock")
    private int version;

    public String getRegisterDateFormat() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return registerDate != null ? registerDate.format(format) : "";
    }

    public String getUpdateDateFormat() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return updateDate != null ? updateDate.format(format) : "";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof AbstractEntity that)) return false;
        return version == that.version && Objects.equals(id, that.id) && Objects.equals(registerDate, that.registerDate)
                && Objects.equals(updateDate, that.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, registerDate, updateDate, version);
    }
}
