package com.ecobank.idea.controller;

import com.ecobank.idea.dto.ResponseDTO;
import com.ecobank.idea.dto.idea.IdeaDTO;
import com.ecobank.idea.dto.idea.IdeaFetchRequestDTO;
import com.ecobank.idea.dto.idea.IdeaPagedResponseDTO;
import com.ecobank.idea.entity.Idea;
import com.ecobank.idea.service.IdeaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.ecobank.idea.constants.AppConstants.API_BASE_URL;

@RestController
@RequestMapping(path = API_BASE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class IdeaController {
    private final IdeaService ideaService;

    @GetMapping("/ideas")
    public ResponseEntity<IdeaPagedResponseDTO<Idea>> fetchIdeas(@RequestParam(required = false) String filter, @RequestParam(defaultValue = "ASC") String sortBy, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        // Build Idea Query
        IdeaFetchRequestDTO request = new IdeaFetchRequestDTO();
        request.setFilter(filter);
        request.setSortBy(sortBy);
        request.setPage(page);
        request.setSize(size);

        // Fetch Paged Results
        Page<Idea> ideasPage = ideaService.fetchIdeas(request);
        return ResponseEntity.status(HttpStatus.OK).body(new IdeaPagedResponseDTO<>(ideasPage));
    }

    @PostMapping("/idea")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ResponseDTO> createIdea(@Valid @RequestBody IdeaDTO ideaDTO) {
        ideaService.createIdea(ideaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(HttpStatus.CREATED, "Idea created successfully"));
    }
}
