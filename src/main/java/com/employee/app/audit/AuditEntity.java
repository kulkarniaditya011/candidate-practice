package com.employee.app.audit;

import com.employee.app.common.ResponseUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EqualsAndHashCode
public abstract class AuditEntity {

    @Column(name = "created_by", updatable = false)
    private Long createdBy;

    @Column(name = "modified_by", updatable = false)
    private Long lastModifiedBy;

    @Column(name = "created_on", updatable = false)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "DD-MM-YYYY HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime createdOn;


    @Column(name = "updated_on", updatable = false)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "DD-MM-YYYY HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime updatedOn;

    @PrePersist
    protected void onCreate(){
        createdOn= LocalDateTime.now();
        createdBy = ResponseUtil.getAuthUser();
    }

    @PreUpdate
    protected void onUpdate(){
        updatedOn = LocalDateTime.now();
        lastModifiedBy = ResponseUtil.getAuthUser();
    }
}
