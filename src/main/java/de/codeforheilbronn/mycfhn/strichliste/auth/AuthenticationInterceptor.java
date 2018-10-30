package de.codeforheilbronn.mycfhn.strichliste.auth;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Profile("cfhnAuth")
public class AuthenticationInterceptor {

    private CfhnAuthenticationService authenticationService;

    public AuthenticationInterceptor(CfhnAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Before("@annotation(de.codeforheilbronn.mycfhn.strichliste.auth.Authenticated)")
    public void ensureAuthentication() {
        authenticationService.ensureAuthenticated();
    }

    @Before("@annotation(de.codeforheilbronn.mycfhn.strichliste.auth.Authorized)")
    public void ensureAuthorized(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Authorized authorized = method.getAnnotation(Authorized.class);
        authenticationService.ensureAnyGroupMember(authorized.groups());
    }
}
