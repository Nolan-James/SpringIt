package me.nolanjames.springit.bootstrap;

import me.nolanjames.springit.model.Link;
import me.nolanjames.springit.model.Role;
import me.nolanjames.springit.model.User;
import me.nolanjames.springit.repository.CommentRepository;
import me.nolanjames.springit.repository.LinkRepository;
import me.nolanjames.springit.repository.RoleRepository;
import me.nolanjames.springit.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final LinkRepository linkRepository;
    private final CommentRepository commentRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public DatabaseLoader(LinkRepository linkRepository, CommentRepository commentRepository,
                          RoleRepository roleRepository, UserRepository userRepository) {
        this.linkRepository = linkRepository;
        this.commentRepository = commentRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        addUsersAndRoles();

        Map<String, String> links = new HashMap<>();
        links.put("Properties with Spring and Spring Boot", "https://www.baeldung.com/properties-with-spring");

        links.forEach((k, v) -> {
            linkRepository.save(new Link(k, v));
        });

        long linkCount = linkRepository.count();
        System.out.println(linkCount);
    }

    private void addUsersAndRoles() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String secret = "{bcrypt}" + encoder.encode("password");

        Role userRole = new Role("ROLE_USER");
        roleRepository.save(userRole);
        Role adminRole = new Role("ROLE_ADMIN");
        roleRepository.save(adminRole);

        User user = new User("user@gmail.com", secret, true);
        user.addRole(userRole);
        userRepository.save(user);

        User admin = new User("admin@gmail.com", secret, true);
        admin.addRole(adminRole);
        userRepository.save(admin);

        User master = new User("master@gmail.com", secret, true);
        master.addRoles(new HashSet<>(Arrays.asList(userRole, adminRole)));
        userRepository.save(master);
    }
}
