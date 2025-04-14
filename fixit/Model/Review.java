package com.example.fixit.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Min(1)
    @Max(5)
    private Integer rating;

    @NotEmpty(message = "should be not empty")
    @Size(min = 5,max = 50)
    @Column(columnDefinition = "varchar(100) not null")
    private String comment;

    private Integer customerId;

    private Integer requestId;
}
