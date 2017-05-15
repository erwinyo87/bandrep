/*
 * 
 * PT. Sarana Pactindo
 * 
 */
package com.mpn.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author H.A
 * created 2017-02-22
 */
public class JwtAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter {
    public static final String TOKEN_HEADER = "Authorization";
    
    @Autowired TokenAuthenticationService tokenAuthService;

    public JwtAuthenticationTokenFilter(JwtUtil jwtUtil) {
        super(new RequestHeaderRequestMatcher(TOKEN_HEADER));
        setAuthenticationManager(new ProviderManager(Collections.singletonList((AuthenticationProvider) new JwtAuthenticationProvider(jwtUtil))));
        setAuthenticationSuccessHandler(new JwtAuthenticationSuccessHandler());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String token = request.getHeader(TOKEN_HEADER);

        // should at least not be null
        if (token == null || !token.startsWith("Bearer ")) {
            // todo return exception
            throw new BadCredentialsException("Authorization not found");
        }

        token = token.substring(token.indexOf(' ') + 1).trim();

        Authentication authToken = new JwtAuthenticationToken(token);

        return getAuthenticationManager().authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
//        tokenAuthService.addAuthentication(response, authResult.getName());
    }
}
