package com.example.taskmanagement.security;

import com.example.taskmanagement.config.properties.SecurityProperties;
import com.example.taskmanagement.exception.BaseException;
import com.example.taskmanagement.exception.ErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import static com.example.taskmanagement.exception.ErrorCode.TOKEN_INVALID;
import static com.example.taskmanagement.exception.ErrorCode.TOKEN_OUTDATED;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final SecurityProperties securityProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            if (request.getHeader("Authorization") != null) {
                final TokenDecoder decoder = TokenDecoder.of(securityProperties.getSecretKey(), parseJwt(request));
                final AuthUser user = decoder.user();

                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()
                                .stream()
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toSet())
                        )
                );
            }
        } catch (BaseException e) {
            if (e.getErrorCode() == TOKEN_OUTDATED || e.getErrorCode() == TOKEN_INVALID) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token expired");
                return;
            }
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token expired");
            return;
        } catch (Exception exception) {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        final String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.split(" ")[1].trim();
        }
        throw new BaseException(ErrorCode.INVALID_TOKEN);
    }

}