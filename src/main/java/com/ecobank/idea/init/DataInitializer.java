package com.ecobank.idea.init;

import com.ecobank.idea.entity.Department;
import com.ecobank.idea.entity.Role;
import com.ecobank.idea.entity.User;
import com.ecobank.idea.entity.ValueType;
import com.ecobank.idea.entity.idea.Idea;
import com.ecobank.idea.entity.idea.IdeaVertical;
import com.ecobank.idea.exception.ResourceNotFoundException;
import com.ecobank.idea.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final DepartmentRepository departmentRepository;
    private final ValueTypeRepository valueTypeRepository;
    private final IdeaVerticalRepository ideaVerticalRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IdeaRepository ideaRepository;


    @Override
    public void run(String... args) throws Exception {
        System.out.println("Initializing values in CommandLineRunner...");

        // List of departments
        List<String> departments = new ArrayList<>(Arrays.asList("Consumer", "HR", "Clearing", "Treasury", "Legal", "Audit"));

        for (String name : departments) {
            Department department = new Department();
            department.setName(name);
            departmentRepository.save(department);
        }

        // List of Idea value types
        ValueType valueType = new ValueType();
        valueType.setName("Alternative revenue stream");
        valueTypeRepository.save(valueType);

        ValueType valueType1 = new ValueType();
        valueType1.setName("Efficiency drive");
        valueTypeRepository.save(valueType1);

        ValueType valueType2 = new ValueType();
        valueType2.setName("Platform / Channel upgrade");
        valueTypeRepository.save(valueType2);

        ValueType valueType3 = new ValueType();
        valueType3.setName("Revenue generation");
        valueTypeRepository.save(valueType3);

        IdeaVertical ideaVertical = new IdeaVertical();
        ideaVertical.setName("Operations");
        ideaVerticalRepository.save(ideaVertical);

        IdeaVertical ideaVertical1 = new IdeaVertical();
        ideaVertical1.setName("Tech");
        ideaVerticalRepository.save(ideaVertical1);

        IdeaVertical ideaVertical2 = new IdeaVertical();
        ideaVertical2.setName("Digital");
        ideaVerticalRepository.save(ideaVertical2);

        // Retrieve user department
        Department department1 = departmentRepository.findById(Long.valueOf("1"))
                .orElseThrow(() -> new ResourceNotFoundException("Department selected not found"));

        Department department2 = departmentRepository.findById(Long.valueOf("2"))
                .orElseThrow(() -> new ResourceNotFoundException("Department selected not found"));

        Department department3 = departmentRepository.findById(Long.valueOf("3"))
                .orElseThrow(() -> new ResourceNotFoundException("Department selected not found"));

        User user = new User();
        user.setFirstName("userFname");
        user.setLastName("userLname");
        user.setEmail("user@ecobank.com");
        user.setPassword(passwordEncoder.encode("password"));
        user.setRole(Role.USER);
        user.setBranch("Ikoyi");
        user.setDepartment(department1);
        user.setState("Lagos");
        userRepository.save(user);

        User user1 = new User();
        user1.setFirstName("user1Fn");
        user1.setLastName("use1rLn");
        user1.setEmail("user1@ecobank.com");
        user1.setPassword(passwordEncoder.encode("password"));
        user1.setRole(Role.USER);
        user1.setBranch("Ikoyi");
        user1.setDepartment(department1);
        user1.setState("Lagos");
        userRepository.save(user1);

        User user2 = new User();
        user2.setFirstName("user2Fn");
        user2.setLastName("user2rLn");
        user2.setEmail("user2@ecobank.com");
        user2.setPassword(passwordEncoder.encode("password"));
        user2.setRole(Role.USER);
        user2.setBranch("Ikoyi");
        user2.setDepartment(department1);
        user2.setState("Lagos");
        userRepository.save(user2);

        User user3 = new User();
        user3.setFirstName("user3Fn");
        user3.setLastName("user3rLn");
        user3.setEmail("user3@ecobank.com");
        user3.setPassword(passwordEncoder.encode("password"));
        user3.setRole(Role.USER);
        user3.setBranch("Ikoyi");
        user3.setDepartment(department1);
        user3.setState("Lagos");
        userRepository.save(user3);


        User user4 = new User();
        user4.setFirstName("adminFn");
        user4.setLastName("adminLn");
        user4.setEmail("admin@ecobank.com");
        user4.setPassword(passwordEncoder.encode("password"));
        user4.setRole(Role.ADMIN);
        user4.setBranch("Ikoyi");
        user4.setDepartment(department1);
        user4.setState("Lagos");
        userRepository.save(user4);
    }
}
