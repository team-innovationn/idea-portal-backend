package com.ecobank.idea.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Schema(name = "Response", description = "Schema to hold successful response information")
@Data
@AllArgsConstructor
public class DataResponseDTO<T> {
    @Schema(description = "Status code of response")
    private HttpStatus statusCode;

    @Schema(description = "Message ")
    private String statusMsg;

    @Schema(description = "Data")
    private T data;
}
