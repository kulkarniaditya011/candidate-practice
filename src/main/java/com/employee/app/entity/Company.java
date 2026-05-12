package com.employee.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_seq")
    @SequenceGenerator(name = "company_seq", sequenceName = "company_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "company_name", unique = true, nullable = false, length = 50)
    private String name;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "contact", length = 15)
    private String companyContact;

    @Column(name = "registration_for", length = 15)
    private String registrationFor;

    @Column(name = "is_active")
    private Boolean isActive;


}
