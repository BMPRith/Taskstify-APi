package com.example.backend.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageResponse<T> {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T payload;
    private Timestamp date;
    private String success;
}
