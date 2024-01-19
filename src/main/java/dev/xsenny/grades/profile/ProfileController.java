package dev.xsenny.grades.profile;

import dev.xsenny.grades.classes.ClassRepository;
import dev.xsenny.grades.classes.ClassService;
import dev.xsenny.grades.grade.GradeService;
import dev.xsenny.grades.user.User;
import dev.xsenny.grades.user.UserRepository;
import dev.xsenny.grades.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
public class ProfileController {


    private final UserService userService;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final ProfileService profileService;

    @GetMapping("")
    public ResponseEntity<List<Profile>> getProfiles(HttpServletRequest request) {
        User user = userService.getUser(request);
        return ResponseEntity.ok(user.getProfiles());
    }

    @PostMapping("")
    public ResponseEntity<Profile> createProfile(@RequestBody ProfileRequest profileRequest, HttpServletRequest request) {
        Profile profile = profileService.createProfile(request, profileRequest);
        return ResponseEntity.ok(profile);
    }

    @DeleteMapping("/{profileId}")
    public ResponseEntity<String> deleteProfile(@PathVariable Integer profileId, HttpServletRequest request) {
        User user = userService.getUser(request);
        Profile profile = profileRepository.findById(profileId).orElseThrow();
        if (userService.hasProfile(user, profileId)) {
            user.getProfiles().remove(profile);
            userRepository.save(user);
            profileRepository.save(profile);
            return ResponseEntity.ok("Profile deleted successfully!");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}
