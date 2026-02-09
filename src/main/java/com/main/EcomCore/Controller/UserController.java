package com.main.EcomCore.Controller;

import com.main.EcomCore.Service.UserService;
import com.main.EcomCore.app.dto.UserRequest;
import com.main.EcomCore.app.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return new ResponseEntity<>(userService.fetchAllUser(),
                HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest){
        userService.AddUser(userRequest);
        return ResponseEntity.ok("User added successfully");
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable long id) {
        return userService.fetchUser(id)
                .map(user -> ResponseEntity.ok(user))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable long id, @RequestBody UserRequest userRequest){
        boolean updated= userService.updateUser(id, userRequest);
        if(updated){
            return  ResponseEntity.ok("User updated successfully");
        }
        else {
            return ResponseEntity.notFound().build();
        }

    }

}
