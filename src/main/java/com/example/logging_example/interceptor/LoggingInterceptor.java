package com.example.logging_example.interceptor;

import com.example.logging_example.wrapper.CustomHttpRequestWrapper;
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

@Slf4j
@Component
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // Request Param Logging
        if (request.getParameterNames().hasMoreElements()) {
            log.info("Request Method: [{}] URL: [{}] Params: [{}]",request.getMethod(), request.getRequestURI(), getRequestParams(request));
            return true;  // 요청을 계속 진행
        }

        // Request Body Logging
        if (request instanceof CustomHttpRequestWrapper) {
            CustomHttpRequestWrapper requestWrapper = (CustomHttpRequestWrapper) request;
            String requestBody = new String(requestWrapper.getRequestBody());

            // Request Body가 있을 경우 로깅
            if (!requestBody.isEmpty()) {
                log.info("Request Method: [{}] URL: [{}] Body: [{}]", request.getMethod(), request.getRequestURI(), requestBody);
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
                    log.info("Request Method: [{}] URL: [{}] pathVariables: [{}]", request.getMethod(), request.getRequestURI(), pathVariables);
                    return true;  // 요청을 계속 진행
                }
            }
        }

        // 기본 요청
        log.info("Request Method: [{}] URL: [{}]",request.getMethod(), request.getRequestURI());
        return true;  // 요청을 계속 진행
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        // 응답 로깅
        log.info("Response Status: [{}]", response.getStatus());

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