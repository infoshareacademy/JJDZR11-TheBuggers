package pl.isa.fitly.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.isa.fitly.dto.CustomUserDetails;
import pl.isa.fitly.model.UserData;
import pl.isa.fitly.repository.UserRepository;

@Service(value = "userServiceDetails")
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserRepository.formError createUser(UserData userData) {
        userData.setPassword(passwordEncoder.encode(userData.getPassword()));
        return userRepository.addUser(userData);
    }

    public UserRepository.formError userUpdate(String email, UserData userData) {
        userData.setPassword(passwordEncoder.encode(userData.getPassword()));
        return userRepository.userUpdate(email, userData);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserData user = userRepository.getUserByEmail(username);
        if (user.emptyUser()) {
            throw new UsernameNotFoundException("Username not found");
        }
        return new CustomUserDetails(user);
    }
}
