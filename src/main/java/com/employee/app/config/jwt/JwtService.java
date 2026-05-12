package com.employee.app.config.jwt;

import com.employee.app.common.EncryptAndDecrypt;
import com.employee.app.entity.Privilege;
import com.employee.app.entity.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String JWT_SECRET;

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails){
        return jwtToken(userDetails);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = EncryptAndDecrypt.decrypt(extractUsername(token));
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public String generateRefreshToken(UserDetails userDetails){
        return refreshJwtToken(userDetails);
    }


    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    private <T> T extractClaim(String token, Function<Claims,T> claimsResolvers) {
       final Claims claims = extractAllClaims(token);
       return claimsResolvers.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }

    private String jwtToken(UserDetails userDetails){
        return Jwts.builder()
                .setClaims(populateClaims(userDetails))
                .setSubject(EncryptAndDecrypt.encrypt(userDetails.getUsername()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ 3600000))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private String refreshJwtToken(UserDetails userDetails){
        return Jwts.builder()
                .setClaims(populateClaims(userDetails))
                .setSubject(EncryptAndDecrypt.encrypt(userDetails.getUsername()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ 86400000))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    private Key getSignKey() {
        byte [] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Map<String, Object> populateClaims(UserDetails userDetails) {
        Users user = (Users) userDetails;
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formattedDateTime = now.format(formatter);

        return new HashMap<String, Object>() {{
            put("name", EncryptAndDecrypt.encrypt(user.getFirstName()));
            put("email", EncryptAndDecrypt.encrypt(user.getEmail()));
            put("company", EncryptAndDecrypt.encrypt(user.getCompany().getName()));
            put("activeRole", EncryptAndDecrypt.encrypt(user.getRole().getName()));
            put("companyID", EncryptAndDecrypt.encrypt(String.valueOf(user.getCompany().getId())));
            put("UserInfo", getUserInfo(user));
            put("LoginTime", formattedDateTime);
        }};
    }

    private Map<String, String> getUserInfo(Users user) {
        return new HashMap<>(){{
            put("userName", user.getFirstName());
            put("userEmail", user.getEmail());
            put("userCompanyId", String.valueOf(user.getCompany().getId()));
            put("userCompany", user.getCompany().getName());
            put("userRole", user.getRole().getName());
            put("   userPrivigle", user.getRole().getPrivileges().stream().map(Privilege::getName).collect(Collectors.joining(",")));
        }};
    }

}
