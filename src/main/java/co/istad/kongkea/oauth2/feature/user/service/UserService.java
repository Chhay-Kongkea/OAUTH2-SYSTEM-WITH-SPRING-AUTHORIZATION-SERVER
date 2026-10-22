package co.istad.kongkea.oauth2.feature.user.service;

import co.istad.kongkea.oauth2.feature.user.dto.CreateUserRequest;
import co.istad.kongkea.oauth2.feature.user.dto.UserResponse;
import co.istad.kongkea.oauth2.feature.user.entity.AppUser;
import co.istad.kongkea.oauth2.feature.user.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponse create(CreateUserRequest request) {

        String username = request.username().trim();
        String email = request.email().trim().toLowerCase(java.util.Locale.ROOT);
        if (userRepository.existsByUsername(username)) {
            throw new DataIntegrityViolationException("Username is already registered");
        }
        if (userRepository.existsByEmail(email)) {
            throw new DataIntegrityViolationException("Email is already registered");
        }

        AppUser user = new AppUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole("USER");
        user.setEnabled(true);
        userRepository.save(user);

        return toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .disabled(!user.isEnabled())
                .build();
    }

    @Transactional(readOnly = true)
    public UserResponse getResponse(String username) {
        return userRepository.findByUsername(username)
                .map(this::toResponse)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private UserResponse toResponse(AppUser user) {
        return new UserResponse(user.getUsername(), user.getEmail(), user.getRole(), user.isEnabled());
    }
}
