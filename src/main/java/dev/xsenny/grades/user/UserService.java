package dev.xsenny.grades.user;

import dev.xsenny.grades.classes.CClass;
import dev.xsenny.grades.config.JWTService;
import dev.xsenny.grades.exam.Exam;
import dev.xsenny.grades.profile.Profile;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JWTService jwtService;
    private final UserRepository userRepository;

    public User getUser(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User getUser(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;
        jwt = authHeader.substring(7);
        username = jwtService.extractUserName(jwt);
        return getUser(username);
    }

    public boolean hasProfile(User user, int profile) {
        for (Profile p : user.getProfiles()) {
            if (p.getId() == profile) return true;
        }
        return false;
    }

    public boolean hasClass(User user, int classID) {
        List<CClass> classes = new ArrayList<>();
        for (Profile p : user.getProfiles()) {
            classes.addAll(p.getClasses());
        }
        for (CClass c : classes) {
            if (c.getId() == classID) return true;
        }
        return false;
    }

    public boolean hasExam(User user, int examID) {
        List<Exam> exams = new ArrayList<>();
        for (Profile profile : user.getProfiles()) {
            exams.addAll(profile.getExams());
        }
        for (Exam e : exams) {
            if (e.getId() == examID) return true;
        }
        return false;
    }

}
