/*
 * 
 * PT. Sarana Pactindo
 * 
 */
package com.mpn.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * @author H.A
 * created 2017-02-22
 */
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private final String token;

    public JwtAuthenticationToken(String token) {
        super(null, null);
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
