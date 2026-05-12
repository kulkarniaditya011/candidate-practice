package com.employee.app.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "positions")
@ToString
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "posi_seq")
    @SequenceGenerator(name = "posi_seq", sequenceName = "Position_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "position_name", unique = true, nullable = false, length = 50)
    private String name;
}
