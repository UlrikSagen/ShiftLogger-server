package timetracker.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    // ⚠️ I prod: legg i env-var
    private static final String SECRET =
            "CHANGE_ME_TO_A_LONG_RANDOM_SECRET_32+_CHARS";

    private static final long EXPIRATION_SECONDS = 60 * 60 * 24; // 24h

    private final SecretKey key =
            Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    /* ======================
       CREATE TOKEN
       ====================== */
    public String createToken(String userId, String username) {
        Instant now = Instant.now();

        return Jwts.builder()
                .setSubject(userId)
                .claim("username", username)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(EXPIRATION_SECONDS)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /* ======================
       VERIFY TOKEN
       ====================== */
    public JwtClaims verify(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return new JwtClaims(
                    claims.getSubject(),
                    claims.get("username", String.class)
            );

        } catch (JwtException e) {
            throw new IllegalArgumentException("Invalid JWT", e);
        }
    }
}
