package com.ecobank.idea.service;

import com.ecobank.idea.dto.user.UserFetchRequestDTO;
import com.ecobank.idea.entity.Department;
import com.ecobank.idea.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    Page<User> fetchUsers(UserFetchRequestDTO userFetchRequestDTO);

    List<Department> fetchDepartments();
}
