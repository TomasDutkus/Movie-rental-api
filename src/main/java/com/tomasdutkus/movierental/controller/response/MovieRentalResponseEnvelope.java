package com.tomasdutkus.movierental.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MovieRentalResponseEnvelope {

    private final boolean success;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ApiErrorBody> errors;

    public MovieRentalResponseEnvelope(boolean success) {
        this.success = success;
    }

    public MovieRentalResponseEnvelope addError(ApiErrorBody error) {
        if (errors == null) {
            errors = new ArrayList<ApiErrorBody>();
        }
        errors.add(error);
        return this;
    }

    public MovieRentalResponseEnvelope addData(Object data) {
        if (this.data == null) {
            this.data = data;
        }
        return this;
    }
}
