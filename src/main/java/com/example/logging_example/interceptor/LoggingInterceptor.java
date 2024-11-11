package com.example.logging_example.interceptor;

import com.example.logging_example.wrapper.CustomHttpRequestWrapper;
import com.example.logging_example.wrapper.CustomHttpResponseWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestId = UUID.randomUUID().toString();
        request.setAttribute("X-Request-ID", requestId);
        response.addHeader("X-Request-ID", requestId);

        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        // Request Param Logging
        if (request.getParameterNames().hasMoreElements()) {
            log.info("Request Method: [{}] id: [{}] URL: [{}] Params: [{}]", request.getMethod(), requestId, request.getRequestURI(), getRequestParams(request));
            return true;  // 요청을 계속 진행
        }

        // Request Body Logging
        if (request instanceof CustomHttpRequestWrapper) {
            CustomHttpRequestWrapper requestWrapper = (CustomHttpRequestWrapper) request;
            String requestBody = new String(requestWrapper.getRequestBody());

            // Request Body가 있을 경우 로깅
            if (!requestBody.isEmpty()) {
                log.info("Request Method: [{}] id: [{}] URL: [{}] Body: [{}]", request.getMethod(), requestId, request.getRequestURI(), requestBody);
                return true;  // 요청을 계속 진행
            }
        }

        // PathVariable Logging
        if (handler instanceof HandlerMethod) {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest currentRequest = attributes.getRequest();
                Map<String, String> pathVariables = (Map<String, String>) currentRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

                if (pathVariables != null) {
                    log.info("Request Method: [{}] id: [{}] URL: [{}] pathVariables: [{}]", request.getMethod(), requestId, request.getRequestURI(), pathVariables);
                    return true;  // 요청을 계속 진행
                }
            }
        }

        // 기본 요청
        log.info("Request Method: [{}] id: [{}] URL: [{}]", request.getMethod(), requestId, request.getRequestURI());
        return true;  // 요청을 계속 진행
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws JsonProcessingException {
        String requestId = (String) request.getAttribute("X-Request-ID");
        long startTime = (Long) request.getAttribute("startTime");
        long duration = System.currentTimeMillis() - startTime;

        // Response Logging
        if (response instanceof CustomHttpResponseWrapper responseWrapper) {
            byte[] responseData = responseWrapper.getResponseData();
            if (responseData != null && responseData.length > 0) {
                String responseBody = new String(responseData);

                ObjectMapper mapper = new ObjectMapper();
                Object json = mapper.readValue(responseBody, Object.class);
                String prettyBody = mapper.writeValueAsString(json);

                log.info("Response Status: [{}] id: [{}] duration: [{}] URL: [{}] Body: [{}]", response.getStatus(), requestId, duration, request.getRequestURI(), prettyBody);
            } else {
                log.info("Response Status: [{}] id: [{}] duration: [{}] URL: [{}] Body: [Empty]", response.getStatus(), requestId, duration, request.getRequestURI());
            }
        } else {
            log.info("Response Status: [{}] id: [{}] duration: [{}] URL: [{}]", response.getStatus(), requestId, duration, request.getRequestURI());
        }
    }

    private Map<String, String> getRequestParams(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            paramMap.put(paramName, request.getParameter(paramName));
        }

        return paramMap;
    }
}