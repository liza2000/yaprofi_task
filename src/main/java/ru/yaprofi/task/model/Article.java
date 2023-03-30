package ru.yaprofi.task.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;

    @NotBlank
    String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;


    @PrePersist
    public void prePersist() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Article>> constraintViolations = validator.validate(this);
        if (!constraintViolations.isEmpty())
            throw new ConstraintViolationException(constraintViolations);
    }

}
