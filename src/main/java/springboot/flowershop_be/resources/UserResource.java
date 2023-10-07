package springboot.flowershop_be.resources;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import springboot.flowershop_be.entities.User;
import springboot.flowershop_be.services.UserService;
import springboot.flowershop_be.Constants;

@RestController
@RequestMapping("/api/users")
public class UserResource {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, Object> userMap) {
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");
        User user = userService.validateUser(email, password);
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody Map<String, Object> userMap) {
        String userName = (String) userMap.get("userName");
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");
        User user = userService.registerUser(userName, email, password);
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    private Map<String, String> generateJWTToken(User user) {
        try {
            long timestamp = System.currentTimeMillis();
            String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
                    .setIssuedAt(new Date(timestamp))
                    .setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
                    .claim("userId", user.getUserId())
                    .claim("email", user.getEmail())
                    .claim("userName", user.getUserName())
                    .compact();
            Map<String, String> map = new HashMap<>();
            map.put("token", token);
            return map;
        } catch (Exception e) {
            throw new UnsupportedOperationException("gen JWT token error " + e);
        }
    }
}
