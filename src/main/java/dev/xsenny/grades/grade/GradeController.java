package dev.xsenny.grades.grade;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/grade")
public class GradeController {

    private final GradeService gradeService;
    private final GradeRepository gradeRepository;

    @PostMapping("")
    public ResponseEntity<Grade> addGrade(@RequestBody GradeRequest gradeRequest, HttpServletRequest request) {
        Grade grade = gradeService.createGrade(request, gradeRequest);
        return ResponseEntity.ok(grade);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGrade(@PathVariable Integer id, HttpServletRequest request) {
        if (gradeService.deleteGrade(request, id)) {
            return ResponseEntity.ok("Grade deleted");
        } else {
            return ResponseEntity.ok("You do not have access to this grade");
        }
    }

    @PutMapping("")
    public ResponseEntity<Grade> updateGrade(@RequestBody UpdateGradeRequest updateGradeRequest, HttpServletRequest request) {
        if (gradeService.updateGrade(request, updateGradeRequest)) {
            return ResponseEntity.ok(gradeRepository.findById(updateGradeRequest.getId()).orElse(null));
        } else {
            return ResponseEntity.ok(null);
        }
    }

}
