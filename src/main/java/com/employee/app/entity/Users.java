package com.employee.app.entity;

import com.employee.app.audit.AuditEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "users", uniqueConstraints =
        {@UniqueConstraint(columnNames = {"company_id", "user_email"})})
public class Users extends AuditEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name= "company_id", nullable = false)
    private Company company;

    @Column(name = "first_name", length = 250, nullable = false)
    private String firstName;

    @Column(name= "last_name", length = 250, nullable = false)
    private String lastName;

    @Column(name = "user_email", unique = true, nullable = false, length = 250)
    private String email;

    @Column(name = "temp_password", nullable = false)
    private String TempPassword;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    private boolean enabled;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = role.getPrivileges()
                .stream()
                .map(priv-> new SimpleGrantedAuthority(priv.getName())).collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
