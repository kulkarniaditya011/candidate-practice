package com.employee.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="privilege")
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "priv_seq")
    @SequenceGenerator(name = "priv_seq", sequenceName = "privilege_sequence", allocationSize = 1)
    private Long id;

    private String name;
}
