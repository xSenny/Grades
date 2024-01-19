package dev.xsenny.grades.grade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.xsenny.grades.classes.CClass;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Grade {

    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne
    @JsonIgnore
    private CClass aClass;
    private Integer value;
    private LocalDateTime registeredTime;

}
