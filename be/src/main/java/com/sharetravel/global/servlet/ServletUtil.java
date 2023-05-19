package com.sharetravel.global.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sharetravel.global.api.ApiResponseCode;
import com.sharetravel.global.api.ApiResponseMessage;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import org.springframework.http.MediaType;

public class ServletUtil {

    public static void setApiResponse(HttpServletResponse response, ApiResponseCode apiResponseCode) throws IOException {
        setResponseHeader(response, apiResponseCode.getHttpStatusCode());
        setResponseBody(response, ApiResponseMessage.of(apiResponseCode.getCode(), apiResponseCode.getMessage()));
    }

    private static void setResponseHeader(HttpServletResponse response, int statusCode) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(statusCode);
    }

    private static void setResponseBody(HttpServletResponse response, Object value)
        throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        response.getWriter().write(
            Objects.requireNonNull(
                mapper.writeValueAsString(value)
            )
        );
    }
}
