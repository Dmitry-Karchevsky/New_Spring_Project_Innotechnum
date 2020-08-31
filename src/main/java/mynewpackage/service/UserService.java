package mynewpackage.service;

import mynewpackage.domain.Role;
import mynewpackage.domain.User;
import mynewpackage.repository.RoleRepository;
import mynewpackage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
/*import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;*/
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
//public class UserService implements UserDetailsService {
public class UserService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    //@Autowired
    //BCryptPasswordEncoder bCryptPasswordEncoder;

    /*@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }*/

    public mynewpackage.domain.User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return null;
        }

        user.setActive(true);
        user.setRegdate(LocalDate.now());
        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        //user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setPassword(user.getPassword());
        userRepository.save(user);
        return user;
    }

    public User updateUser(Long id, User user) {
        user.setId(id);
        //User oldUser = userRepository.deleteUserById(id);
        if (userRepository.deleteUserById(id)) {
            //user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setPassword(user.getPassword());
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

    public List<User> usergtList(Long idMin) {
        return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
                .setParameter("paramId", idMin).getResultList();
    }
}
