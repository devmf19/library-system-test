package com.cloudlabs.library.security.jwt;

import com.cloudlabs.library.enums.RoleEnum;
import com.cloudlabs.library.security.util.SecurityConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtGenerator jwtGenerator;

    @Autowired
    public JwtAuthenticationFilter(UserDetailsService userDetailsService, JwtGenerator jwtGenerator) {
        this.userDetailsService = userDetailsService;
        this.jwtGenerator = jwtGenerator;
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.AUTHORIZATION_HEADER);
        if (StringUtils.hasText(token) && token.startsWith(SecurityConstants.BEARER)) {
            return token.substring(SecurityConstants.BEARER_LENGTH);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = getTokenFromRequest(request);

        if (StringUtils.hasText(token) && jwtGenerator.validateToken(token)) {
            String username = jwtGenerator.getUsernameFromJwt(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            Collection<? extends GrantedAuthority> userRoles = userDetails.getAuthorities();

            boolean isDisabled = userRoles.stream()
                    .anyMatch(auth -> RoleEnum.DISABLED.name().equals(auth.getAuthority()));

            if (isDisabled) {
                respondWithForbidden(response, SecurityConstants.DISABLED_USER);
                return;
            }

            boolean hasValidRole = userRoles.stream()
                    .anyMatch(auth -> RoleEnum.USER.name().equals(auth.getAuthority()) ||
                                      RoleEnum.ADMIN.name().equals(auth.getAuthority()));

            if (hasValidRole) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                respondWithForbidden(response, SecurityConstants.DENIED_ACCESS);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private void respondWithForbidden(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(message);
    }
}
