package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.dto.UpdateUserRequestDto;
import com.spotbiz.spotbiz_backend_springboot.entity.AuthenticationResponse;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import com.spotbiz.spotbiz_backend_springboot.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody User user) {
        AuthenticationResponse response = userService.authenticate(user);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<User> update(@RequestBody UpdateUserRequestDto request) {
        User updatedUser = userService.update(request);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<String> delete(@PathVariable String email) {
        userService.delete(email);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/users/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        try {
            User user = userService.getUserByEmail(email);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve user: " + ex.getMessage());
        }
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsersWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<User> usersPage = userService.getAllUsers(pageable);
            return ResponseEntity.ok(usersPage);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve users: " + ex.getMessage());
        }
    }
}
