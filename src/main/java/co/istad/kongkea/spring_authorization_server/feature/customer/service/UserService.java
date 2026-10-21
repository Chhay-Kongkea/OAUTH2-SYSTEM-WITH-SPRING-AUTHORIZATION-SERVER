package co.istad.kongkea.spring_authorization_server.feature.customer.service;

import co.istad.kongkea.spring_authorization_server.feature.customer.dto.CreateUserRequest;
import co.istad.kongkea.spring_authorization_server.feature.customer.dto.UserResponse;
import co.istad.kongkea.spring_authorization_server.feature.customer.entity.AppUser;
import co.istad.kongkea.spring_authorization_server.feature.customer.repository.UserRepository;
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
        if (userRepository.existsByUsername(username)) {
            throw new DataIntegrityViolationException("Username is already registered");
        }

        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole("USER");
        user.setEnabled(true);
        userRepository.save(user);

        return new UserResponse(user.getUsername(), user.getRole(), user.isEnabled());
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
}
