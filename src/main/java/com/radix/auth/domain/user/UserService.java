package com.radix.auth.domain.user;

import com.radix.auth.domain.user.exception.InvalidCredentialsException;
import com.radix.auth.domain.user.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  User register(User user) throws UserAlreadyExistsException {
    if (exists(user)) {
      throw new UserAlreadyExistsException("A user with the email address " + user.getEmail() + " already exists.");
    }

    final User newUser = new User();

    newUser.setName(user.getName());
    newUser.setEmail(user.getEmail());
    newUser.setPassword(passwordEncoder.encode(user.getPassword()));

    return userRepository.save(newUser);
  }

  User login(User user) throws InvalidCredentialsException {
    if (!exists(user)) {
      throw new InvalidCredentialsException("A user with the email address " + user.getEmail() + " doesn't exist.");
    }

    final User persistedUser = userRepository.findByEmail(user.getEmail());

    boolean passwordsMatch = passwordEncoder.matches(user.getPassword(), persistedUser.getPassword());

    if (!passwordsMatch) {
      throw new InvalidCredentialsException("Incorrect password.");
    }

    return persistedUser;
  }

  private boolean exists(User user) {
    return userRepository.existsByEmail(user.getEmail());
  }
}
