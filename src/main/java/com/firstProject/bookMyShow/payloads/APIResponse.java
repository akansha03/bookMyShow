package com.firstProject.bookMyShow.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class APIResponse {

    private String message;
    private Object data;
}
