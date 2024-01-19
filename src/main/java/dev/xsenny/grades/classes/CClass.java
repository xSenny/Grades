package dev.xsenny.grades.classes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.xsenny.grades.grade.Grade;
import dev.xsenny.grades.profile.Profile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class CClass {

    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne
    @JsonIgnore
    private Profile profile;
    @OneToMany(targetEntity = Grade.class, cascade = CascadeType.ALL)
    private List<Grade> grades;
    private String name;
    @Transient
    @JsonProperty("average")
    public Double calculateAverage() {
        if (grades == null || grades.isEmpty()) {
            return 0.0;
        }
        int sum = 0;
        for (Grade grade : grades) {
            sum += grade.getValue();
        }
        return (double) sum / grades.size();
    }
    @JsonProperty("progressive-average")
    public Map<LocalDateTime, Double> getProgressiveAverage() {
        Map<LocalDateTime, Double> a = new HashMap<>();
        int sum = 0, b = 0;
        if (grades == null) return a;
        for (Grade grade : grades) {
            sum += grade.getValue();
            b++;
            double c = (double) sum / b;
            System.out.println(sum + " " + b + " " + c);

            a.put(grade.getRegisteredTime(), c);
        }
        return a;
    }

}
