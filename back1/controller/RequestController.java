package org.example.back1.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.example.back1.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class RequestController {

    @Autowired
    private PointService pointService;

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> createAccount(@RequestBody AuthRequest authRequest) {
        String login = authRequest.getLogin();
        String password = authRequest.getPassword();

        if (authService.createAccount(login, password)) {
            return ResponseEntity.ok(Map.of("message", "Регистрация успешна"));
        } else {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
                    .body(Map.of("message", "Логин уже существует"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {
        String login = authRequest.getLogin();
        String password = authRequest.getPassword();

        if (authService.authenticate(login, password)) {
            return ResponseEntity.ok(Map.of("token", "FAKE_JWT_TOKEN"));
        } else {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
                    .body(Map.of("message", "Неверный логин или пароль"));
        }
    }

    @Autowired
    private PointRepository pointRepository;

    @PostMapping("/point/add")
    public ResponseEntity<?> addPoint(@RequestBody Point point) {
        point.setIsHit(Checker.check(point.getX(), point.getY(), point.getR()));
        point.setLeadTime(System.currentTimeMillis());
        point.setServerTime(java.time.Instant.now().toString());

        Point savedPoint = pointRepository.save(point);
        return ResponseEntity.ok(savedPoint);
    }

    @PostMapping("/point/delete")
    public List<Point> deletePoint() {
        return pointService.clear();
    }

    @PostMapping("/point/show")
    public List<Point> showPoints() {
        return pointService.getPointList();
    }
}
