/*
 * 
 * PT. Sarana Pactindo
 * 
 */
package com.mpn.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.springframework.security.core.authority.AuthorityUtils;

import java.security.Key;
import java.util.Date;

/**
 * @author H.A
 * created 2017-02-22
 */
public class JwtUtil {
    private final Key key;
//    private long EXPIRATIONTIME = 1000 * 60 * 60; // 1 days
    private final long EXPIRATIONTIME = 1000 * 60 * 5; // 5 minutes

    public JwtUtil() {
        key = MacProvider.generateKey();
    }

    public String generateToken(String user, String roleList) {
        Claims claims = Jwts.claims().setSubject(user);
        claims.put("role", roleList);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, key)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .compact();
    }

    public AuthenticatedUser validateToken(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();

            return new AuthenticatedUser(body.getSubject(), token, AuthorityUtils.commaSeparatedStringToAuthorityList((String) body.get("role")));
        } catch (JwtException ex) {
            // TODO log
            return null;
        }
    }
}
