package com.api.taskManagement.data.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@ToString
@Table(name = "app_user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstname;
    private String lastname;
    private String middleName;
    private String email;
//    @OneToOne
//    private Address address;
    private String phonenumber;
//    private String bvn;
//    private int age;
//    @Enumerated(EnumType.STRING)
//    private Gender gender;
//    @Enumerated(EnumType.STRING)
//    private List<Role> role;
//    @Transient
//    private MultipartFile profilePicture;
    @Transient
    private String fullName;
    private String password;
    private String confirmPassword;
//    private String accountNumber;
//    private boolean enabled;

    //    @PostLoad
    public String getFullName() {
        return firstname + " " + lastname;
    }

}
