package com.denismerenkov.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "roles")
public class Role extends BaseEntity {
    @NonNull
    @Column(nullable = false, unique = true)
    private String name;

    @ToString.Exclude
    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
