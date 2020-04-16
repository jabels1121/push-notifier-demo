package com.jaybe.pushnotifierdemo.security.filters;

import com.jaybe.pushnotifierdemo.models.ExceptionResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MessageTokenFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        var authToken = httpRequest.getHeader("X-auth-test");
        if (authToken == null) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            var responseWriter = httpResponse.getWriter();
            responseWriter.write(ExceptionResponseDTO.getUnauthorizedInstanceAsJsonString());
            responseWriter.flush();
            responseWriter.close();
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

}
