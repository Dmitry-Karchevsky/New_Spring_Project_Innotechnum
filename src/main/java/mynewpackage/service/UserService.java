package mynewpackage.service;

import mynewpackage.domain.Role;
import mynewpackage.domain.Test;
import mynewpackage.domain.User;
import mynewpackage.repository.RoleRepository;
import mynewpackage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        /*Боже, как хорошо, что есть индусы на ютубе
        https://www.youtube.com/watch?v=AQg0OTPLzfU

        *Не проходил аутентификацию при тестировании (Postman)
        */
        final List<Role> authoritiesForSpring = user.getRoles().stream().collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authoritiesForSpring);
    }

    public mynewpackage.domain.User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null || !user.getPassword().equals(user.getPasswordConfirm())) {
            return null;
        }

        user.setActive(true);
        user.setRegdate(LocalDate.now());
        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        //user.setPassword(user.getPassword());
        userRepository.save(user);
        return user;
    }

    public User updateUser(Long id, User user) {
        user.setId(id);
        Optional<User> userFromDb = userRepository.findById(id);
        if (userFromDb.isPresent()) {
            user.setActive(userFromDb.get().isActive());
            user.setRegdate(userFromDb.get().getRegdate());
            user.setRoles(userFromDb.get().getRoles());
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            //user.setPassword(user.getPassword());
            userRepository.save(user);
            return user;
        }
        return null;
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public boolean deleteUser2(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            user.get().setActive(false);
            userRepository.save(user.get());
            return true;
        }
        return false;
    }
}
