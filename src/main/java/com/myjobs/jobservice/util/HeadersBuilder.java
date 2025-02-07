package com.myjobs.jobservice.util;


import org.springframework.http.HttpHeaders;

public class HeadersBuilder {

    public static void setHeaders(HttpHeaders headers, String authToken, String cookie) {
        headers.set("csrf-token", authToken);
        headers.set(HttpHeaders.COOKIE, cookie);
    }
}