package com.employee.app.entity;

import com.employee.app.audit.AuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Indexed;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "candidates")
public class Candidate extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cand_seq")
    @SequenceGenerator(name = "cand_seq", sequenceName = "Candidate_sequence", allocationSize = 1)
    private Long id;

    @Column(unique = true, name="name", nullable = false, length = 100)
    private String name;

    @Column(unique = true, name = "email",nullable = false, length = 100)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="position_apply", joinColumns = @JoinColumn(name="candidate_id"), inverseJoinColumns = @JoinColumn(name="pos_id"))
    private List<Position> positions;

    @ManyToOne
    @JoinColumn(name = "gender_id",nullable = false)
    private Gender gender;



}
