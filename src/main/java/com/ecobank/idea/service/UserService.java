package com.ecobank.idea.service;

import com.ecobank.idea.dto.user.ProfileUpdateRequestDTO;
import com.ecobank.idea.dto.user.UserFetchRequestDTO;
import com.ecobank.idea.dto.user.UserResponseDTO;
import com.ecobank.idea.entity.User;
import org.springframework.data.domain.Page;

public interface UserService {
    Page<UserResponseDTO> fetchUsers(UserFetchRequestDTO userFetchRequestDTO);

    User updateUserprofile(ProfileUpdateRequestDTO requestDTO);

//    List<Department> fetchDepartments();
}
