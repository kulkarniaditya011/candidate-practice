package com.employee.app.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class RestApiResponse<T> {
    private Object message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("data")
    private T data;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("timeStamp")
    private Date timeStamp;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Object errors;
}
