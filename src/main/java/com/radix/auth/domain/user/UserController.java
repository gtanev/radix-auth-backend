package com.radix.auth.domain.user;


import com.radix.auth.domain.user.User.OnRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/login")
  @CrossOrigin
  public ResponseEntity<?> login(@Validated @RequestBody User user) throws Exception {
    User loggedInUser = userService.login(user);
    return ResponseEntity.ok().body(loggedInUser);
  }

  @PostMapping("/register")
  @CrossOrigin
  public ResponseEntity<?> register(@Validated(OnRegister.class) @RequestBody User user) throws Exception {
    User registeredUser = userService.register(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
  }
}
