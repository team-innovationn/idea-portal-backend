package com.ecobank.idea.init;

//import com.ecobank.idea.entity.Department;

import com.ecobank.idea.config.ApplicationProperties;
import com.ecobank.idea.entity.Role;
import com.ecobank.idea.entity.User;
import com.ecobank.idea.entity.ValueType;
import com.ecobank.idea.entity.idea.IdeaVertical;
import com.ecobank.idea.repository.IdeaRepository;
import com.ecobank.idea.repository.IdeaVerticalRepository;
import com.ecobank.idea.repository.UserRepository;
import com.ecobank.idea.repository.ValueTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final ValueTypeRepository valueTypeRepository;
    private final IdeaVerticalRepository ideaVerticalRepository;
    private final UserRepository userRepository;
    private final ApplicationProperties applicationProperties;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Initializing values in CommandLineRunner...");

//      List of Idea value types
        persistValueTypeIfNotExists("Alternative revenue stream");
        persistValueTypeIfNotExists("Efficiency drive");
        persistValueTypeIfNotExists("Platform / Channel upgrade");
        persistValueTypeIfNotExists("Revenue generation");

//        List of Vertical types
        persistIdeaVerticalIfNotExists("Operations");
        persistIdeaVerticalIfNotExists("Tech");
        persistIdeaVerticalIfNotExists("Digital");

        Optional<User> userOptional = userRepository.findByEmail(applicationProperties.getAdminUsername());
        if (userOptional.isEmpty()) {
            User user = new User();
            user.setRole(Role.ADMIN);
            user.setEmail(applicationProperties.getAdminUsername());
            userRepository.save(user);
            user.setProfileUpdated(false);
        }
    }


    private void persistValueTypeIfNotExists(String name) {
        Optional<ValueType> existingValueType = valueTypeRepository.findByName(name);
        if (existingValueType.isEmpty()) {
            ValueType valueType = new ValueType();
            valueType.setName(name);
            valueTypeRepository.save(valueType);
        }
    }

    private void persistIdeaVerticalIfNotExists(String name) {
        Optional<IdeaVertical> existingIdeaVertical = ideaVerticalRepository.findByName(name);
        if (existingIdeaVertical.isEmpty()) {
            IdeaVertical ideaVertical = new IdeaVertical();
            ideaVertical.setName(name);
            ideaVerticalRepository.save(ideaVertical);
        }
    }
}
