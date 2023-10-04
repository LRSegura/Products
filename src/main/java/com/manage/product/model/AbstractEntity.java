package com.manage.product.model;

import com.manage.product.api.annotation.InjectedDate;
import com.manage.product.api.persistence.validation.HibernateEventHandlers;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

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
    @InjectedDate
    private LocalDateTime registerDate;

    @Version
    @Column(name = "OptLock")
    private int version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractEntity abstractEntity)) return false;
        return version == abstractEntity.version && Objects.equals(id, abstractEntity.id) && Objects.equals(registerDate, abstractEntity.registerDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, registerDate, version);
    }
}
