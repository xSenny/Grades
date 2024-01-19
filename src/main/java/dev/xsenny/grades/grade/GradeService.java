package dev.xsenny.grades.grade;

import dev.xsenny.grades.classes.CClass;
import dev.xsenny.grades.classes.ClassRepository;
import dev.xsenny.grades.user.User;
import dev.xsenny.grades.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final UserService userService;
    private final ClassRepository classRepository;
    private final GradeRepository gradeRepository;

    public Grade createGrade(HttpServletRequest request, GradeRequest gradeRequest) {
        int grade = gradeRequest.getGrade();
        Integer id = gradeRequest.getClassId();
        User user = userService.getUser(request);
        Grade grade1 = null;
        if (userService.hasClass(user, id)) {
            CClass c = classRepository.findById(id).orElseThrow();
            grade1 = Grade.builder().value(grade).aClass(c).registeredTime(LocalDateTime.now()).build();
            gradeRepository.save(grade1);
            c.getGrades().add(grade1);
            classRepository.save(c);
        }
        return grade1;
    }

    public boolean deleteGrade(HttpServletRequest request, Integer id) {
        Grade grade = gradeRepository.findById(id).orElse(null);
        if (grade == null) return false;
        CClass c = grade.getAClass();
        User user = userService.getUser(request);
        if (!userService.hasClass(user, c.getId())) return true;
        c.getGrades().remove(grade);
        classRepository.save(c);
        gradeRepository.deleteById(id);
        return true;
    }

    public boolean updateGrade(HttpServletRequest request, UpdateGradeRequest updateGradeRequest) {
        Grade grade = gradeRepository.findById(updateGradeRequest.getId()).orElse(null);
        if (grade == null) return false;
        CClass c = grade.getAClass();
        User user = userService.getUser(request);
        if (!userService.hasClass(user, c.getId())) return true;
        grade.setValue(updateGradeRequest.getNewValue());
        gradeRepository.save(grade);
        return true;
    }

}
