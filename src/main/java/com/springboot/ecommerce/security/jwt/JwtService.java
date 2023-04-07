package com.springboot.ecommerce.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private final static String SECRET_KEY = "3777217A25432A462D4A614E645267556B58703272357538782F413F4428472B";


    private String extractUsername(String token){return extractClaim(token, Claims::getSubject);}

    private Date extractExpiration(String token){return extractClaim(token, Claims::getExpiration);}

    private boolean isTokenExpired(String token){return extractExpiration(token).before(new Date());}

    private boolean isTokenValid(String token, UserDetails userDetails){
        return (extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    /**
     * Overload nhé:))
     */
    public String generateToken(UserDetails userDetails){return generateToken(new HashMap<>(), userDetails);}

    private String generateToken(
            Map<String, Object> extractClaims,
            UserDetails userDetails
    ){
        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15))
                .signWith(getSignInWith(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInWith())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    /**
     * Phương thức getSignInWith() trong đoạn mã này trả về một đối tượng Key dùng để ký và xác thực JWT (JSON Web Token).

     * Cụ thể, phương thức này sử dụng một chuỗi SECRET_KEY được mã hóa dưới dạng Base64 và chuyển đổi nó
     * thành một mảng byte keyBytes. Sau đó, phương thức sử dụng lớp Keys của thư viện java-jwt để tạo ra
     * một đối tượng Key bằng cách sử dụng thuật toán mã hóa SHA và keyBytes.
     * Khi một JWT được tạo ra, nó sẽ được ký bằng Key này để đảm bảo tính xác thực.
     * Khi JWT được gửi lại cho server, server sẽ sử dụng cùng một Key để xác thực và
     * giải mã JWT để lấy thông tin người dùng từ trong payload của JWT.
     * Nếu Key được sử dụng để xác thực không khớp với Key được sử dụng để ký JWT ban đầu,
     * server sẽ từ chối yêu cầu và báo lỗi xác thực.
     * @author đếu phải tôi đâu nhé:)))
     */
    private Key getSignInWith(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
