package com.ecobank.idea.service.impl;

import com.ecobank.idea.dto.user.UserFetchRequestDTO;
import com.ecobank.idea.entity.User;
import com.ecobank.idea.repository.UserRepository;
import com.ecobank.idea.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Page<User> fetchUsers(UserFetchRequestDTO requestDTO) {
        String sortBy = "createdAt";

        if (null != requestDTO.getSortBy() || !requestDTO.getSortBy().isEmpty()) {
            sortBy = requestDTO.getSortBy();
        }

        // Build sort object
        Sort sort = Sort.by(Sort.Direction.fromString(requestDTO.getSortDirection().toLowerCase()), sortBy);

        // Define the Pageable variable
        Pageable pageable = PageRequest.of(requestDTO.getPage(), requestDTO.getSize(), sort);

        // Implemented filtering logic here, assuming `filter` is a simple keyword search in `title` and `description`.
        if (requestDTO.getFilter() != null && !requestDTO.getFilter().isEmpty()) {
            return userRepository.searchByFirstNameOrLastName(requestDTO.getFilter(), pageable);
        } else {
            return userRepository.findAll(pageable);
        }
    }
}
