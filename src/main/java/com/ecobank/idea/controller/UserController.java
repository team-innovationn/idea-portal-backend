package com.ecobank.idea.controller;

import com.ecobank.idea.dto.PagedResponseDTO;
import com.ecobank.idea.dto.user.UserFetchRequestDTO;
import com.ecobank.idea.entity.User;
import com.ecobank.idea.exception.ErrorResponseDTO;
import com.ecobank.idea.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.ecobank.idea.constants.AppConstants.API_BASE_URL;

@Tag(
        name = "Users API",
        description = "API to FETCH users"
)
@RestController
@RequestMapping(API_BASE_URL)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "User API",
            description = "Fetch Users"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @GetMapping("/users")
    public ResponseEntity<PagedResponseDTO<User>> fetchUsers(@RequestParam(required = false, defaultValue = "") String filter, @RequestParam(required = false, defaultValue = "createdAt") String sortBy, @RequestParam(defaultValue = "DESC") String sortDirection, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        // Build query
        UserFetchRequestDTO requestDTO = new UserFetchRequestDTO();
        requestDTO.setFilter(filter);
        requestDTO.setSortDirection(sortDirection);
        requestDTO.setSortBy(sortBy);
        requestDTO.setPage(page);
        requestDTO.setSize(size);

        Page<User> userPage = userService.fetchUsers(requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new PagedResponseDTO<>(userPage));
    }
}
