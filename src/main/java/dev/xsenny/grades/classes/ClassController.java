package dev.xsenny.grades.classes;

import dev.xsenny.grades.grade.GradeService;
import dev.xsenny.grades.profile.Profile;
import dev.xsenny.grades.profile.ProfileRepository;
import dev.xsenny.grades.user.User;
import dev.xsenny.grades.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/class")
@RestController
public class ClassController {

    private final UserService userService;
    private final ClassService classService;
    private final GradeService gradeService;
    private final ClassRepository classRepository;
    private final ProfileRepository profileRepository;

    @GetMapping("/{profileId}")
    public ResponseEntity<List<CClass>> getClasses(HttpServletRequest request, @PathVariable Integer profileId) {
        User user = userService.getUser(request);
        Profile profile = profileRepository.findById(profileId).orElseThrow();
        if (!userService.hasProfile(user, profileId)) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(profile.getClasses());
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Class> getClasses(HttpServletRequest request, @PathVariable Integer id) {
//        User user = userService.getUser(request);
//        Class c = classRepository.findById(id).orElse(null);
//        if (c != null && Objects.equals(c.getUser().getId(), user.getId())) {
//            return ResponseEntity.ok(c);
//        }
//        return null;
//    }

    @PostMapping("")
    public ResponseEntity<CClass> createClass(@RequestBody ClassRequest classRequest, HttpServletRequest request) {
        CClass c = classService.createClass(request, classRequest);
        return ResponseEntity.ok(c);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClass(@PathVariable Integer id, HttpServletRequest request) {
        if (classService.deleteClass(request, id)) {
            return ResponseEntity.ok("Class deleted");
        } else {
            return ResponseEntity.ok("You do not have access to this class");
        }
    }
}
