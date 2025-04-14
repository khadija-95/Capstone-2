package com.example.fixit.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "should be not empty")
    @Size(min = 10,max = 100)
    @Column(columnDefinition = "varchar(100) not null")
    private String description;

    @NotEmpty(message = "Status must not be empty")
    @Pattern(regexp = "^(PENDING|IN_PROGRESS|COMPLETED|CANCELLED)$",
            message = "Status must be one of the following: PENDING, IN_PROGRESS, COMPLETED, CANCELLED, EMERGENCY")
    private String status;


    private LocalDateTime scheduledTime;
    private boolean isEmergency;

    private Double totalCost;

    private Integer customerId;
    private Integer servicesId;
    private Integer providerId;
}
