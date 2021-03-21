package com.tiagocomputer.javamscrud.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse implements Serializable {
    private static final long SerialVersionUID = 8889878776665L;

    private Date timestamp;
    private String message;
    private String details;

}
