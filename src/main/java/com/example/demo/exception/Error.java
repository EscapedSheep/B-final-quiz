package com.example.demo.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Error {
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> details;
}
