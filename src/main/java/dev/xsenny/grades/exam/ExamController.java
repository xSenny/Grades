package dev.xsenny.grades.exam;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/exam")
public class ExamController {

    private final ExamService examService;

    @PostMapping("")
    public ResponseEntity<Exam> createExam(HttpServletRequest request, ExamRequest examRequest) {
        Exam exam = examService.createExam(request, examRequest);
        if (exam != null) return ResponseEntity.ok(exam);
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public void deleteExam(HttpServletRequest request, @PathVariable Integer id) {
        examService.removeExam(request, id);
    }

    @PostMapping("")
    public void updateExam(HttpServletRequest request, ExamUpdateReq req) {
        examService.updateExam(request, req);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Exam>> getAllExams(HttpServletRequest request, @PathVariable Integer id){
        List<Exam> exams = examService.getAllExams(request, id);
        if (exams == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(exams);
    }

}
