package com.terenko.musiccloud.DTO;

public class RequestDTO {
    int code;
    String error;

    public RequestDTO(int code, String error) {
        this.code = code;
        this.error = error;
    }
}
