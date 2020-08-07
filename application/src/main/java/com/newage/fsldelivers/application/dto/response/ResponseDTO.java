package com.newage.fsldelivers.application.dto.response;

import java.util.Calendar;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDTO<T> {

    private Integer httpStatus;
    private Boolean success;
    private Date timestamp;
    private Object result;
    private ResponseError error;

    public ResponseDTO(Integer httpStatus, Boolean success, Object result, ResponseError error) {
        this.httpStatus = httpStatus;
        this.success = success;
        this.error = error;
        this.result = result;
    }

    public Date getTimestamp() {
        return Calendar.getInstance().getTime();
    }
}