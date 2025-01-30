package com.ecobank.idea.service.impl;

import com.ecobank.idea.dto.user.ProfileUpdateRequestDTO;
import com.ecobank.idea.dto.user.UserFetchRequestDTO;
import com.ecobank.idea.dto.user.UserResponseDTO;
import com.ecobank.idea.entity.Role;
import com.ecobank.idea.entity.User;
import com.ecobank.idea.repository.UserRepository;
import com.ecobank.idea.service.UserService;
import com.ecobank.idea.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Page<UserResponseDTO> fetchUsers(UserFetchRequestDTO requestDTO) {
        String sortBy;
        Sort sort;

        if (null != requestDTO.getSortBy() || !requestDTO.getSortBy().isEmpty()) {
            sortBy = requestDTO.getSortBy();

            // Build sort object
            sort = Sort.by(Sort.Direction.fromString(requestDTO.getSortDirection().toLowerCase()), sortBy);
        }

        // Define the Pageable variable
        Pageable pageable = PageRequest.of(requestDTO.getPage(), requestDTO.getSize());

        // Implemented filtering logic here, assuming `filter` is a simple keyword search in `title` and `description`.
        if (requestDTO.getFilter() != null && !requestDTO.getFilter().isEmpty()) {
            Page<User> users = userRepository.searchByFirstNameOrLastNameWithRoleUser(requestDTO.getFilter(), pageable);
            Page<UserResponseDTO> userDTOs = users.map(user -> {
                UserResponseDTO dto = UserResponseDTO.builder()
                        .userId(user.getUserId())
                        .department(user.getDepartment())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .state(user.getState())
                        .email(user.getEmail())
                        .interactions(user.getInteractionCount())
                        .branch(user.getBranch())
                        .role(user.getRole())
                        .profileUpdated(user.isProfileUpdated())
                        .build();
                return dto;
            });
            return userDTOs;
        } else {
            Page<User> users = userRepository.findAllByRole(pageable, Role.USER);
            Page<UserResponseDTO> userDTOs = users
                    .map(user -> {
                        UserResponseDTO dto = UserResponseDTO.builder()
                                .userId(user.getUserId())
                                .department(user.getDepartment())
                                .firstName(user.getFirstName())
                                .email(user.getEmail())
                                .lastName(user.getLastName())
                                .state(user.getState())
                                .interactions(user.getInteractionCount())
                                .branch(user.getBranch())
                                .role(user.getRole())
                                .profileUpdated(user.isProfileUpdated())
                                .build();
                        return dto;
                    });
            return userDTOs;
        }
    }

    @Override
    public User updateUserprofile(ProfileUpdateRequestDTO requestDTO) {
        String userEmail = (String) SecurityUtil.getCurrentAccount();
        Optional<User> userOptional = userRepository.findByEmail(userEmail);
        User user = userOptional.orElseGet(User::new);
        user.setBranch(requestDTO.getBranch());
        user.setDepartment(requestDTO.getDepartment());
        user.setFirstName(requestDTO.getFirstName());
        user.setLastName(requestDTO.getLastName());
        user.setEmail(userEmail);
        user.setState(requestDTO.getState());
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }
        user.setProfileUpdated(true);
        try {
            return userRepository.save(user);
        } catch (Exception ex) {
            throw new RuntimeException("Error updating user profile " + ex.getMessage());
        }
    }
}


//    @Override
//    public List<Department> fetchDepartments() {
//        return departmentRepository.findAll();
//    }
