package com.example.fixit.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Services {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "UserName should be not empty")
    @Size(min =5,message = "UserName should be at lest 5 characters long")
    @Column(columnDefinition = "varchar(10) not null")
    private String name;

    @NotEmpty(message = "should be not empty")
    @Size(min = 10,max = 100)
    @Column(columnDefinition = "varchar(100) not null")
    private String description;

    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Price must be non-negative")
    private Double price;
}
