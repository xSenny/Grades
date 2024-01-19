package dev.xsenny.grades.exam;

import dev.xsenny.grades.classes.CClass;
import dev.xsenny.grades.classes.ClassRepository;
import dev.xsenny.grades.grade.GradeRepository;
import dev.xsenny.grades.profile.Profile;
import dev.xsenny.grades.profile.ProfileRepository;
import dev.xsenny.grades.user.User;
import dev.xsenny.grades.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final UserService userService;
    private final ClassRepository classRepository;
    private final GradeRepository gradeRepository;
    private final ProfileRepository profileRepository;
    private final ExamRepository examRepository;

    public Exam createExam(HttpServletRequest request, ExamRequest examRequest) {
        User user = userService.getUser(request);
        Optional<CClass> aClass = classRepository.findById(examRequest.getClassId());
        if (aClass.isPresent()) {
            CClass c = aClass.get();
            if (userService.hasProfile(user, c.getProfile().getId())) {
                Exam exam = Exam.builder().aClass(c).build();
                examRepository.save(exam);
                return exam;
            }
        }
        return null;
    }

    public void removeExam(HttpServletRequest request, Integer examId) {
        User user = userService.getUser(request);
        Exam exam = examRepository.findById(examId).orElseThrow();
        Profile profile = exam.getProfile();
        if (userService.hasProfile(user, profile.getId())) {
            profile.getExams().remove(exam);
            profileRepository.save(profile);
            examRepository.deleteById(examId);
        }
    }

    public List<Exam> getAllExams(HttpServletRequest req, Integer profileId) {
        User user = userService.getUser(req);
        if (userService.hasProfile(user, profileId)) {
            Optional<Profile> profile = profileRepository.findById(profileId);
            if (profile.isPresent()) {
                return profile.get().getExams();
            }
        }
        return null;
    }

    public void updateExam(HttpServletRequest req, ExamUpdateReq request) {
        User user = userService.getUser(req);
        if (userService.hasExam(user, request.getId())) {
            Exam exam = examRepository.findById(request.getId()).orElseThrow();
            exam.setExamDate(request.getFinishDate());
            exam.setExpectedGrade(request.getExpectedGrade());
            examRepository.save(exam);
        }
    }

}
