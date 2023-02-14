package com.example.LoanApp2.JwtTokenUtils;

import org.springframework.stereotype.Service;

        import io.jsonwebtoken.Claims;
        import io.jsonwebtoken.Jwts;
        import io.jsonwebtoken.SignatureAlgorithm;
        import org.springframework.security.core.userdetails.UserDetails;
        import org.springframework.stereotype.Service;

        import java.util.Date;
        import java.util.HashMap;
        import java.util.Map;
        import java.util.function.Function;

@Service
public class JwtTokenUtil {

    private String secret = "yBKrn1G0b7cY";
    //private final String jwtSecret = "yBKrn1G0b7cY";
    private final String jwtIssuer = "deliverance.com";  //https://passwordsgenerator.net/

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /* public String getUserId(String token) {
         Claims claims = Jwts.parser()
                 .setSigningKey(secret)

                 .parseClaimsJws(token)
                 .getBody();
         //return claims.getSubject().split(",")[0];
         return claims.getSubject();
     }*/
    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)

                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
        // return claims.getSubject().split(",")[1];
    }


    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                //.setExpiration(new Date(System.currentTimeMillis() + 300000)) //5 minutes
                .setExpiration(new Date(System.currentTimeMillis() + 1800000)) //30 minutes
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {

        final String username = extractUsername(token);
        //throw a 401 error to show token has expired
        if(isTokenExpired(token)) throw new RuntimeException("Token has expired");//throw a 401 error to show token has expired
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}


