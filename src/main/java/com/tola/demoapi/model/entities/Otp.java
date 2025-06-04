package com.tola.demoapi.model.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity(name = "otps")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Otp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer otpId;

    @Column(name = "otp_code", length = 6, nullable = false)
    private Integer otpCode;

    @Column(name = "created_at", length = 10)
    private LocalDateTime createdAt;

    @Column(name = "expired_at", length = 10)
    private LocalDateTime expiredAt;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;
}
