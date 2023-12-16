package com.example.TMS.util;

import com.example.TMS.dto.AdminLoginDTO;
import com.example.TMS.serviceImpl.CustomAdminDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil implements Serializable {

    private final String secret = "hardikdhrumanakshpateldhruvahdfkhgdfuyfegwyefihewiurhwiefuwygefiywfgyUFGWEIFYGWEYFWGBEFIHGWIYFBWIHB";
    private final long expiryDuration = 5 * 60 * 60;
    long milliTime = System.currentTimeMillis();
    long expiryTime = milliTime + expiryDuration * 1000;
    Date issuedAt = new Date(milliTime);
    Date expiryAt = new Date(expiryTime);

    @Autowired
    CustomAdminDetailService adminDetailService;

    public String generateJwt(AdminLoginDTO adminLoginDTO) {
        UserDetails userDetails = adminDetailService.loadUserByUsername(adminLoginDTO.getAdminName());
        Claims claims = Jwts.claims()
                .setIssuer(userDetails.getUsername())
                .setIssuedAt(issuedAt)
                .setExpiration(new Date((new Date()).getTime() + expiryTime));
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername()).signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String name = getUsernameFromToken(token);
        return (name.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
