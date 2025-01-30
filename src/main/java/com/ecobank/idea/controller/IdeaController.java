package com.ecobank.idea.controller;

import com.ecobank.idea.constants.IdeaEnums;
import com.ecobank.idea.dto.PagedResponseDTO;
import com.ecobank.idea.dto.ResponseDTO;
import com.ecobank.idea.dto.idea.IdeaDTO;
import com.ecobank.idea.dto.idea.IdeaFetchRequestDTO;
import com.ecobank.idea.dto.idea.IdeaStatisticsDTO;
import com.ecobank.idea.dto.idea.IdeaUpdateDTO;
import com.ecobank.idea.entity.ValueType;
import com.ecobank.idea.entity.idea.Idea;
import com.ecobank.idea.entity.idea.IdeaVertical;
import com.ecobank.idea.exception.ErrorResponseDTO;
import com.ecobank.idea.service.IdeaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.ecobank.idea.constants.AppConstants.API_BASE_URL;

@Tag(name = "Idea API", description = "CRUD API to CREATE, UPDATE, FETCH and DELETE Ideas")
@RestController
@RequestMapping(path = API_BASE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class IdeaController {
    private final IdeaService ideaService;

    @Operation(summary = "Ideas API", description = "Fetch ideas")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "HTTP Status OK"), @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))})
    @GetMapping("/ideas")
    public ResponseEntity<PagedResponseDTO<Idea>> fetchIdeas(@RequestParam(required = false) String filter, @RequestParam(defaultValue = "createdAt") String sortBy, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(required = false) IdeaEnums.Status status, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate, @RequestParam(required = false) String verticalId, @RequestParam(required = false) String valueTypeId) {
        // Build Idea Query
        IdeaFetchRequestDTO request = new IdeaFetchRequestDTO();
        request.setIdeaVerticalId(verticalId);
        request.setValueTypeId(valueTypeId);
        request.setStatus(status);
        request.setFromDate(fromDate);
        request.setToDate(toDate);
        request.setFilter(filter);
        request.setSortBy(sortBy);
        request.setPage(page);
        request.setSize(size);

        // Fetch Paged Results
        Page<Idea> ideasPage = ideaService.fetchIdeas(request);
        return ResponseEntity.status(HttpStatus.OK).body(new PagedResponseDTO<>(ideasPage));
    }

    @Operation(summary = "Idea Verticals", description = "Fetch idea verticals")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "HTTP Status OK"), @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))})
    @GetMapping("/idea/verticals")
    public ResponseEntity<List<IdeaVertical>> fetchIdeaVerticals() {
        return ResponseEntity.status(HttpStatus.OK).body(ideaService.fetchIdeaVerticals());
    }


    @Operation(summary = "Idea value", description = "Fetch ideas value types")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "HTTP Status OK"), @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))})
    @GetMapping("/idea/valuetypes")
    public ResponseEntity<List<ValueType>> fetchIdeaValueTypes() {
        return ResponseEntity.status(HttpStatus.OK).body(ideaService.fetchIdeaValueTypes());
    }

    @Operation(summary = "Ideas stats API", description = "Fetch ideas stats - pending, approved, rejected")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "HTTP Status OK"), @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))})
    @GetMapping("/ideas/stats")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<IdeaStatisticsDTO> getIdeaStatistics() {
        IdeaStatisticsDTO statistics = ideaService.getIdeaStatistics();
        return ResponseEntity.ok(statistics);
    }

    // Create new Idea
    @Operation(summary = "Idea create API", description = "Create ideas")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "HTTP Status OK"), @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))})
    @PreAuthorize("hasAuthority('USER')")
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/idea")
    public ResponseEntity<ResponseDTO> createIdea(@Valid @RequestBody IdeaDTO ideaDTO) {
        System.out.println(ideaDTO);
        ideaService.createIdea(ideaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.CREATED, "Idea created successfully"));
    }


    // Update Idea Status
    @Operation(summary = "Ideas Update API", description = "Update idea status")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "HTTP Status OK"), @ApiResponse(responseCode = "404", description = "HTTP Status Not found - Idea not found to update"), @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))})
    @PutMapping("/idea")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> updateIdeaStatus(@RequestParam Long ideaId, @RequestBody IdeaUpdateDTO ideaUpdateDTO) {
        ideaService.updateIdeaStatus(ideaId, IdeaEnums.Status.valueOf(ideaUpdateDTO.getStatus()));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(HttpStatus.CREATED, "Idea updated successfully"));
    }
}