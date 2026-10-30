package com.example.shop.account;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;
import java.util.Base64;
@Component public class JwtService {
 private final byte[] secret; private final long seconds; private final ObjectMapper mapper=new ObjectMapper();
 public JwtService(@Value("${app.jwt-secret}") String secret,@Value("${app.jwt-expiration-minutes}") long minutes){this.secret=secret.getBytes(StandardCharsets.UTF_8);if(this.secret.length<32)throw new IllegalArgumentException("JWT_SECRET must contain at least 32 bytes");if(minutes<1)throw new IllegalArgumentException("JWT_EXPIRATION_MINUTES must be positive");this.seconds=minutes*60;}
 public String issue(UserAccount user){try{String head=enc("{\"alg\":\"HS256\",\"typ\":\"JWT\"}");String body=enc(mapper.writeValueAsBytes(Map.of("sub",user.email,"iat",Instant.now().getEpochSecond(),"exp",Instant.now().plusSeconds(seconds).getEpochSecond())));String base=head+"."+body;return base+"."+sign(base);}catch(Exception ex){throw new IllegalStateException(ex);}}
 public Optional<String> subject(String token){try{String[] parts=token.split("\\.");if(parts.length!=3||!java.security.MessageDigest.isEqual(sign(parts[0]+"."+parts[1]).getBytes(StandardCharsets.UTF_8),parts[2].getBytes(StandardCharsets.UTF_8)))return Optional.empty();Map<?,?> claims=mapper.readValue(Base64.getUrlDecoder().decode(parts[1]),Map.class);if(((Number)claims.get("exp")).longValue()<Instant.now().getEpochSecond())return Optional.empty();return Optional.of((String)claims.get("sub"));}catch(Exception ex){return Optional.empty();}}
 private String sign(String value)throws Exception{Mac mac=Mac.getInstance("HmacSHA256");mac.init(new SecretKeySpec(secret,"HmacSHA256"));return enc(mac.doFinal(value.getBytes(StandardCharsets.UTF_8)));}
 private String enc(String v){return Base64.getUrlEncoder().withoutPadding().encodeToString(v.getBytes(StandardCharsets.UTF_8));} private String enc(byte[] v){return Base64.getUrlEncoder().withoutPadding().encodeToString(v);}
}
