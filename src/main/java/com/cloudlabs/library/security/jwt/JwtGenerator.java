package com.cloudlabs.library.security.jwt;

import com.cloudlabs.library.security.model.PrincipalUser;
import com.cloudlabs.library.security.util.SecurityConstants;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Component
public class JwtGenerator {

    @Value(SecurityConstants.SECRET_TOKEN)
    private String secret;

    @Value(SecurityConstants.EXPIRATION_TOKEN)
    private int expirationInMs;

    public String generateToken(Authentication authentication) {
        PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();

        List<String> roles = principalUser
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts.builder()
                .setSubject(principalUser.getUsername())
                .claim(SecurityConstants.ROLES, roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expirationInMs))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    public String getUsernameFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            throw new AuthenticationCredentialsNotFoundException(SecurityConstants.TOKEN_SIGNATURE_ERROR);
        } catch (MalformedJwtException ex) {
            throw new AuthenticationCredentialsNotFoundException(SecurityConstants.INVALID_TOKEN);
        } catch (ExpiredJwtException ex) {
            throw new AuthenticationCredentialsNotFoundException(SecurityConstants.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException ex) {
            throw new AuthenticationCredentialsNotFoundException(SecurityConstants.UNSUPPORTED_TOKEN);
        } catch (IllegalArgumentException ex) {
            throw new AuthenticationCredentialsNotFoundException(SecurityConstants.EMPTY_TOKEN);
        }
    }
}
