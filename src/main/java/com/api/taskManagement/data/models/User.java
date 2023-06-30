package com.api.taskManagement.data.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
@AllArgsConstructor
@NoArgsConstructor
@Data
//@Entity
//@Table(name = "users")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;


//
//    @NotBlank
//    @Size(min=3, max = 50)
//    private String name;
//
//    @NotBlank
//    @Size(min=3, max = 50)
//    private String username;
//
//    @NaturalId
//    @NotBlank
//    @Size(max = 50)
//    @Email
//    private String email;
//
//    @NotBlank
//    @Size(min=6, max = 100)
//    private String password;
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "user_roles",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id"))
//    private Set<Role> roles = new HashSet<>();
//
//    @ManyToMany(mappedBy = "users")
//    @JsonManagedReference
//    private List<Program> program = new ArrayList<>();
//
//    public User(String name, String username, String email, String encode) {
//
//    }
}
