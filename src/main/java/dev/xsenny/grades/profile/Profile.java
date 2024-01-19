package dev.xsenny.grades.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.xsenny.grades.classes.CClass;
import dev.xsenny.grades.exam.Exam;
import dev.xsenny.grades.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Profile {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    @ManyToOne
    private User user;
    @OneToMany
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = CClass.class)
    private List<CClass> classes;
    @OneToMany
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Exam.class)
    private List<Exam> exams;

    @JsonProperty("average")
    private Double calculateFinalAverage() {
        double sum = 0;
        for (CClass c : classes) {
            sum += c.calculateAverage();
        }
        return sum /= classes.size();
    }

}
