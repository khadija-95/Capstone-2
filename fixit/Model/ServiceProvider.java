package com.example.fixit.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class ServiceProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Name should be not empty")
    @Size(min =5,message = "Name should be at lest 5 characters long")
    @Column(columnDefinition = "varchar(10) not null")
    private String name;

    @NotEmpty(message = "Email should be not empty")
    @Email
    @Column(columnDefinition = "varchar(30) not null unique")
    private String email;

    @NotEmpty(message = "Password should be not empty")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).+$", message = "Password must contain letters and digits")
    @Column(columnDefinition = "varchar(10) not null")
    private String password;

    @NotEmpty(message = "Name should be not empty")
    @Size(min =5,message = "Name should be at lest 5 characters long")
    @Column(columnDefinition = "varchar(10) not null")
    private String specialization;

    @Pattern(regexp = "^(05)[0-9]{8}$", message = "Phone number must start with 05 and be 10 digits")
    private String phone;

    private String nationalId;

    private String licenseNumber;


    @Min(0)
    private Integer YearsOfExperience;

    private Boolean isApproved;

    private Boolean isAvailable;

    private Boolean isActive;



}
