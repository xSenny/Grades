package dev.xsenny.grades.profile;

import dev.xsenny.grades.user.User;
import dev.xsenny.grades.user.UserRepository;
import dev.xsenny.grades.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {


    private final UserService userService;
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public Profile createProfile(HttpServletRequest request, ProfileRequest profileRequest) {
        User user = userService.getUser(request);
        Profile profile = Profile.builder().name(profileRequest.getName()).build();
        user.getProfiles().add(profile);
        profileRepository.save(profile);
        userRepository.save(user);
        return profile;
    }

}
