package com.copytorex.restservices.user;

import com.copytorex.restservices.exception.DuplicateResourceFoundException;
import com.copytorex.restservices.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserResource {

    private final UserRepository userRepository;

    public UserResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable int id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new ResourceNotFoundException("ID not found: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public void delById(@PathVariable int id) {
        try {
            userRepository.deleteById(id);
        } catch (RuntimeException ex) {
            throw new ResourceNotFoundException("ID not found: " + id);
        }
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public String add(@RequestBody User user) {
        try {
            User addedUser = userRepository.save(user);
            return "{\"id\":" + addedUser.getId() + "}";
        } catch (RuntimeException ex) {
            throw new DuplicateResourceFoundException(ex.getClass().getName() + " - " + ex.getMessage());
        }
    }

}
