package com.firstProject.bookMyShow.bootstrap;

import com.firstProject.bookMyShow.entities.Role;
import com.firstProject.bookMyShow.entities.RoleEnum;
import com.firstProject.bookMyShow.repository.RoleRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.loadRoles();
    }

    private void loadRoles() {
        RoleEnum[] roleNames = new RoleEnum[] {RoleEnum.USER, RoleEnum.ADMIN, RoleEnum.SUPER_ADMIN};
        Map<RoleEnum, String> roleDescriptionMap = Map.of(
          RoleEnum.USER, "Default User Role",
          RoleEnum.ADMIN, "Administrator Role",
          RoleEnum.SUPER_ADMIN, "Super Administrator Role"
        );

        Arrays.stream(roleNames).forEach((roleName) -> {
            Optional<Role> optionalRole = roleRepository.findByName(roleName);

            optionalRole.ifPresentOrElse(System.out::println, () -> {
                Role newRole = new Role();

                newRole.setName(roleName);
                newRole.setDescription(roleDescriptionMap.get(roleName));

                roleRepository.save(newRole);
            });
        });
    }
}
