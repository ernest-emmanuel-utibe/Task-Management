package com.api.taskManagement.data.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = AppUser.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private AppUser userDetails;

    private LocalDate confirmedAt ;
    private LocalDateTime createdAt = LocalDateTime.now ();
    private LocalDateTime expiryTime = createdAt.plusMinutes (15);

    public VerificationToken(String token, LocalDateTime now, LocalDateTime localDateTime, AppUser saved) {
        this.token = token;
        this.expiryTime = localDateTime;
        this.userDetails = saved;
        this.createdAt = now;
    }
}
