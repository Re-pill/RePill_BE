package com.repill.backend.global.security.provider;

import com.repill.backend.apiPayload.code.status.ErrorStatus;
import com.repill.backend.apiPayload.exception.RePillClientException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Date;

import static com.repill.backend.apiPayload.code.status.ErrorStatus.AUTH_EXTRACT_ERROR;

@Component
public class JwtTokenProvider {

    private static final String HEADER_STRING = "Authorization";
    private static final String HEADER_STRING_PREFIX = "Bearer ";

    private final SecretKey secretKey;
    private final long accessTokenValidityMilliseconds;
    private final long refreshTokenValidityMilliseconds;

    public JwtTokenProvider(
            @Value("${jwt.secret}") final String secretKey,
            @Value("${jwt.access-token-validity}") final long accessTokenValidityMilliseconds,
            @Value("${jwt.refresh-token-validity}") final long refreshTokenValidityMilliseconds) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.accessTokenValidityMilliseconds = accessTokenValidityMilliseconds;
        this.refreshTokenValidityMilliseconds = refreshTokenValidityMilliseconds;
    }

    public String extractToken(final HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HEADER_STRING);

        if (authorizationHeader != null && authorizationHeader.startsWith(HEADER_STRING_PREFIX)) {
            return authorizationHeader.substring(7);
        }
        throw new RePillClientException(AUTH_EXTRACT_ERROR);
    }

    public String createAccessToken(Long memberId) {
        return createToken(memberId, accessTokenValidityMilliseconds);
    }

    public String createRefreshToken(Long memberId) {
        return createToken(memberId, refreshTokenValidityMilliseconds);
    }

    private String createToken(Long memberId, long validityMilliseconds) {
        Claims claims = Jwts.claims();
        claims.put("id", memberId);

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(validityMilliseconds / 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(tokenValidity.toInstant()))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Long getId(String token) {
        return getClaims(token).getBody().get("id", Long.class);
    }

    public boolean isTokenValid(String token) {
        try {
            Jws<Claims> claims = getClaims(token);
            Date expiredDate = claims.getBody().getExpiration();
            Date now = new Date();

            return expiredDate.after(now);
        } catch (ExpiredJwtException e) {
            throw new RePillClientException(ErrorStatus.AUTH_EXPIRED_TOKEN);
        } catch (SecurityException
                 | MalformedJwtException
                 | UnsupportedJwtException
                 | IllegalArgumentException e) {
            throw new RePillClientException(ErrorStatus.AUTH_INVALID_TOKEN);
        }
    }

    private Jws<Claims> getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
    }

    // 인가
    public Authentication getAuthentication(String token) {
        try {
            Long memberId = getId(token); // Extract member ID from JWT
            User principal = new User(memberId.toString(), "",
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
            return new UsernamePasswordAuthenticationToken(principal, token, principal.getAuthorities());
        } catch (Exception e) {
            throw new RePillClientException(ErrorStatus.AUTH_INVALID_TOKEN);
        }
    }
}