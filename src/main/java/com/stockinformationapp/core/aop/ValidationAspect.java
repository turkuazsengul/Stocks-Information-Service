package com.stockinformationapp.core.aop;

import com.stockinformationapp.model.StockHistoryDto;
import com.stockinformationapp.util.StockOperationUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.format.DateTimeParseException;

@Aspect
@Component
@RequiredArgsConstructor
public class ValidationAspect {

    private final HttpServletResponse response;

    @Around("@annotation(com.stockinformationapp.core.aop.ValidateRequest)")
    public Object validateRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {
            if (arg instanceof StockHistoryDto requestDTO) {
                if (!isValidDate(requestDTO.getDate())) {
                    sendErrorResponse("Invalid date format. Expected format is MMM d, yyyy.");
                    return null;
                }
            }
        }
        return joinPoint.proceed();
    }

    private boolean isValidDate(String date) {
        try {
            StockOperationUtil.getDateFromString(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private void sendErrorResponse(String errorMessage) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + errorMessage + "\"}");
        response.getWriter().flush();
        response.getWriter().close();
    }
}
