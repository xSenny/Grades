package dev.xsenny.grades.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.xsenny.grades.profile.Profile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
@Builder
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Table(name="_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Integer id;
    private String firstName;
    private String lastName;
    private String username;
    @JsonIgnore
    private String password;
    private Role role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ElementCollection(targetClass = Profile.class)
    private List<Profile> profiles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
