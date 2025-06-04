package com.tola.demoapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tola.demoapi.model.entities.Otp;
import com.tola.demoapi.model.entities.User;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Integer> {
    Optional<Otp> findByOtpCode(Integer otpCode);

    Optional<Otp> findByUser(User user);
}
