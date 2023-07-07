package com.api.taskManagement.data.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
})
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
//@Entity
//@Table(name = "users")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Size(min=3, max = 50)
    private String username;

    @NotBlank
    @Size(min=6, max = 100)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "role_name",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List <Role_Name> role;



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
