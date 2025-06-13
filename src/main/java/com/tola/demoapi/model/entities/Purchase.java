package com.tola.demoapi.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "purchases")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer purId;
    private LocalDateTime purDate;
    private String purStatus;
    private String purType;
    private String purPaymentMethod;
    private String purPaymentStatus;
    private String purPaymentAmount;
    private String purPaymentCurrency;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
