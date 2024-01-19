package dev.xsenny.grades.classes;

import dev.xsenny.grades.profile.Profile;
import dev.xsenny.grades.profile.ProfileRepository;
import dev.xsenny.grades.user.User;
import dev.xsenny.grades.user.UserRepository;
import dev.xsenny.grades.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClassService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final ClassRepository classRepository;
    private final ProfileRepository profileRepository;
    public CClass createClass(HttpServletRequest request, ClassRequest classRequest) {
        User user = userService.getUser(request);
        Profile profile = profileRepository.findById(classRequest.getProfileId()).orElseThrow();
        if (!userService.hasProfile(user, profile.getId())) return null;
        CClass c = CClass.builder()
                .name(classRequest.getName())
                .profile(profile)
                .build();
        classRepository.save(c);
        profile.getClasses().add(c);
        profileRepository.save(profile);
        return c;
    }

    public boolean deleteClass(HttpServletRequest request, Integer id) {
        CClass c = classRepository.findById(id).orElse(null);
        if (c == null) return false;
        User user = userService.getUser(request);
        if (!userService.hasClass(user, id)) return false;
        Profile profile = c.getProfile();
        profile.getClasses().remove(c);
        profileRepository.save(profile);
        classRepository.deleteById(id);
        return true;
    }


}
