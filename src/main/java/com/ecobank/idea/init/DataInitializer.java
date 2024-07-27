package com.ecobank.idea.init;

import com.ecobank.idea.entity.Department;
import com.ecobank.idea.entity.ValueType;
import com.ecobank.idea.entity.idea.IdeaVertical;
import com.ecobank.idea.repository.DepartmentRepository;
import com.ecobank.idea.repository.IdeaVerticalRepository;
import com.ecobank.idea.repository.ValueTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
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
        List<String> valueTypes = new ArrayList<>(Arrays.asList("Alternative revenue stream", "Efficiency drive", "Platform / Channel upgrade", "Customer Acquisition", "Revenue generation"));

        for (String name : valueTypes) {
            ValueType valueType = new ValueType();
            valueType.setName(name);
            valueTypeRepository.save(valueType);
        }


        // List of Idea verticals types
        List<String> ideaVerticals = new ArrayList<>(Arrays.asList("Operations", "Tech", "Digital"));
        for (String name : ideaVerticals) {
            IdeaVertical ideaVertical = new IdeaVertical();
            ideaVertical.setName(name);
            ideaVerticalRepository.save(ideaVertical);
        }

        System.out.println("Values initialized.");
    }
}
