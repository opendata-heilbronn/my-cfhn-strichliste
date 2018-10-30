package de.codeforheilbronn.mycfhn.strichliste.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@RequestScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Profile("cfhnAuth")
public class CfhnAuthenticationService {

    private TokenValidationService validationService;
    private HttpServletResponse response;
    private HttpServletRequest request;
    @Value("${cfhn.auth.loginUrl}")
    private String loginUrl;
    @Value("${cfhn.auth.redirectUrl}")
    private String redirectUrl;
    @Value("${cfhn.auth.cookieName}")
    private String cookieName;

    @Autowired
    public CfhnAuthenticationService(
            HttpServletRequest request,
            HttpServletResponse response,
            TokenValidationService validationService
    ) {
        this.response = response;
        this.request = request;
        this.validationService = validationService;
    }

    private Optional<TokenData> readFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(cookieName)) {
                    return validationService.validateToken(c.getValue());
                }
            }
        }
        return Optional.empty();
    }

    private Optional<TokenData> readFromAuthHeader(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Optional.empty();
        }

        String token = authHeader.substring("Bearer ".length());
        return validationService.validateToken(token);
    }

    public boolean isAuthenticated() {
        return getUserData().isPresent();
    }

    public Optional<TokenData> getUserData() {
        return Optional.ofNullable(
                readFromCookies(request)
                        .orElseGet(() -> readFromAuthHeader(request)
                                .orElse(null)));
    }

    public void ensureAuthenticated() {
        if (!isAuthenticated()) {
            response.setStatus(HttpStatus.SEE_OTHER.value());
            try {
                response.setHeader("Location", loginUrl + "?redirect=" + URLEncoder.encode(redirectUrl, StandardCharsets.UTF_8.displayName()));
            } catch (UnsupportedEncodingException e) {
                log.warn("Unsupported authentication", e);
            }
            throw new AuthenticationException();
        }
    }

    public void ensureAnyGroupMember(String... groupCn) {
        Optional<TokenData> tokenData = getUserData();
        if (!tokenData.isPresent()) {
            throwAccessDenied();
        }

        List<String> groups = tokenData.get().getGroups();
        for (String group : groupCn) {
            if (groups.stream().anyMatch(g -> g.startsWith("cn=" + group))) {
                return;
            }
        }
        throwAccessDenied();
    }

    private void throwAccessDenied() {
        throw new AuthorizationException();
    }

    @ResponseStatus(code = HttpStatus.SEE_OTHER, value = HttpStatus.SEE_OTHER, reason = "You need to log in first")
    private static class AuthenticationException extends RuntimeException {
    }

    @ResponseStatus(code = HttpStatus.FORBIDDEN, value = HttpStatus.FORBIDDEN, reason = "You don't have the permission")
    private static class AuthorizationException extends RuntimeException {
    }
}
