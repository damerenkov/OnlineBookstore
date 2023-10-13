package com.denismerenkov.model.user;

import com.denismerenkov.model.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @NonNull
    @Column(unique = true, nullable = false)
    private String userName;

    @NonNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true)
    private String hash;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roles;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Order> orders;
}
