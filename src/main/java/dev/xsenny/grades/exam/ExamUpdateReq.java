package dev.xsenny.grades.exam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamUpdateReq {


    private Integer id, expectedGrade;
    private LocalDate finishDate;

}
