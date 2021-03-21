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
    private final PostRepository postRepository;

    public UserResource(UserRepository userRepository,
                        PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new ResourceNotFoundException("ID not found: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public void delUserById(@PathVariable int id) {
        try {
            userRepository.deleteById(id);
        } catch (RuntimeException ex) {
            throw new ResourceNotFoundException("ID not found: " + id);
        }
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public String addUser(@RequestBody User user) {
        try {
            userRepository.save(user);
            return "{\"User ID\":" + user.getId() + "}";
        } catch (RuntimeException ex) {
            throw new DuplicateResourceFoundException(ex.getClass().getName() + " - " + ex.getMessage());
        }
    }

    @GetMapping("/{id}/posts")
    public List<Post> getPosts(@PathVariable int id) {
        return getUserById(id).getPosts();
    }

    @PostMapping(value = "/{id}/posts", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public String addPost(@PathVariable int id, @RequestBody Post post) {
        try {
            User user = getUserById(id);
            post.setUser(user);
            postRepository.save(post);
            return "{\"Post ID\":" + post.getId() + "}";
        } catch (RuntimeException ex) {
            throw new DuplicateResourceFoundException(ex.getClass().getName() + " - " + ex.getMessage());
        }
    }

}
