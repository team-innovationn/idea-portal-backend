package com.ecobank.idea.controller;

import com.ecobank.idea.dto.DataResponseDTO;
import com.ecobank.idea.dto.PagedResponseDTO;
import com.ecobank.idea.dto.user.ProfileUpdateRequestDTO;
import com.ecobank.idea.dto.user.UserFetchRequestDTO;
import com.ecobank.idea.dto.user.UserResponseDTO;
import com.ecobank.idea.entity.User;
import com.ecobank.idea.exception.ErrorResponseDTO;
import com.ecobank.idea.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<PagedResponseDTO<UserResponseDTO>> fetchUsers(@RequestParam(required = false, defaultValue = "") String filter, @RequestParam(required = false, defaultValue = "createdAt") String sortBy, @RequestParam(defaultValue = "DESC") String sortDirection, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        UserFetchRequestDTO requestDTO = new UserFetchRequestDTO();
        requestDTO.setFilter(filter);
        requestDTO.setSortDirection(sortDirection);
        requestDTO.setSortBy(sortBy);
        requestDTO.setPage(page);
        requestDTO.setSize(size);

        Page<UserResponseDTO> userPage = userService.fetchUsers(requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new PagedResponseDTO<>(userPage));
    }

    @Operation(
            summary = "Update account API",
            description = "Create account for new users"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK",
                    content = @Content(
                            schema = @Schema(implementation = DataResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "HTTP Status unauthorized",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @PostMapping("/update")
    public ResponseEntity<DataResponseDTO<UserResponseDTO>> updateProfile(@Valid @RequestBody ProfileUpdateRequestDTO profileUpdateRequestDTO) {
        try {
            User savedUser = userService.updateUserprofile(profileUpdateRequestDTO);
            UserResponseDTO responseDTO = UserResponseDTO.builder()
                    .userId(savedUser.getUserId())
                    .email(savedUser.getEmail())
                    .firstName(savedUser.getFirstName())
                    .lastName(savedUser.getLastName())
                    .department(savedUser.getDepartment())
                    .role(savedUser.getRole())
                    .state(savedUser.getState())
                    .branch(savedUser.getBranch())
                    .interactions(savedUser.getInteractionCount())
                    .profileUpdated(true)
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(new DataResponseDTO<>(HttpStatus.OK, "User profile updated", responseDTO));
        } catch (Exception exception) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new DataResponseDTO<>(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), null));
        }
    }
}
