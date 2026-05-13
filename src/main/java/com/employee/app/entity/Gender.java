package com.employee.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "gender")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Gender {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_seq")
    @SequenceGenerator(name = "gen_seq", sequenceName = "gender_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "gender_type",length = 6, unique = true, nullable = false)
    private String genderType;
}
