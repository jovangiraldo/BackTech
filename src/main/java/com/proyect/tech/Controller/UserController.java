package com.proyect.tech.Controller;
import com.proyect.tech.DTO.LoginRequest;
import com.proyect.tech.DTO.LoginResponse;
import com.proyect.tech.Model.User;
import com.proyect.tech.Service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/users")

public class UserController {
    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }
    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) { 
        return  ResponseEntity.status(HttpStatus.CREATED)
        .body(userService.crearUsuario(user));
    }

    @GetMapping
    public List<User> findAll(){
        return userService.findAll();
    }
    //READ BY ID
    @GetMapping("/{id}")
    public User findByID(@PathVariable Long id){
        return userService.findById(id)
        .orElseThrow(()-> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Usuario no encontrado"));
    }
    // UPDATE
    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User userDetails){
        
        return userService.update(id, userDetails);

    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }     
         
}
