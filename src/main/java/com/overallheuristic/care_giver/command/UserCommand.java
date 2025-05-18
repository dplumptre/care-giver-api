package com.overallheuristic.care_giver.command;


import com.overallheuristic.care_giver.config.AppRole;
import com.overallheuristic.care_giver.model.Role;
import com.overallheuristic.care_giver.model.User;
import com.overallheuristic.care_giver.repositories.RoleRepository;
import com.overallheuristic.care_giver.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@Order(1)
public class UserCommand implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserCommand(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    @Override
    public void run(String... args) throws Exception {
        Role userRole = getOrCreateRole(AppRole.ROLE_USER);
        Role sellerRole = getOrCreateRole(AppRole.ROLE_SELLER);
        Role adminRole = getOrCreateRole(AppRole.ROLE_ADMIN);

        Set<Role> userRoles = Set.of(userRole);
        Set<Role> sellerRoles = Set.of(sellerRole);
        Set<Role> adminRoles = Set.of(userRole, sellerRole, adminRole);

        if (!userRepository.existsByUserName("john")) {
            User user1 = new User("john", "John Doe", "user@example.com", passwordEncoder.encode("Service1#"));
            user1.setRoles(userRoles);
            userRepository.save(user1);
        }

        if (!userRepository.existsByUserName("seller")) {
            User seller = new User("seller", "Seller Man", "seller1@example.com", passwordEncoder.encode("Service1#"));
            seller.setRoles(sellerRoles);
            userRepository.save(seller);
        }

        if (!userRepository.existsByUserName("admin")) {
            User admin = new User("admin", "Joseph", "admin@example.com", passwordEncoder.encode("Service1#"));
            admin.setRoles(adminRoles);
            userRepository.save(admin);
        }
    }

    private Role getOrCreateRole(AppRole roleName) {
        return roleRepository.findByRoleName(roleName)
                .orElseGet(() -> roleRepository.save(new Role(roleName)));
    }

}
