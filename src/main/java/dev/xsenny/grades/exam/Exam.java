package dev.xsenny.grades.exam;

import dev.xsenny.grades.classes.CClass;
import dev.xsenny.grades.profile.Profile;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Exam {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private Profile profile;
    private CClass aClass;
    private Integer expectedGrade;
    private LocalDate examDate;

}
