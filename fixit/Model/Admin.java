package com.example.fixit.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @NotEmpty(message = "UserName should be not empty")
    @Size(min =5,message = "UserName should be at lest 5 characters long")
    @Column(columnDefinition = "varchar(10) not null")
    private String userName;

    @NotEmpty(message = "Email should be not empty")
    @Email
    @Column(columnDefinition = "varchar(30) not null unique")
    private String email;

    @NotEmpty(message = "Password should be not empty")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).+$", message = "Password must contain letters and digits")
    @Column(columnDefinition = "varchar(10) not null")
    private String password;
}
