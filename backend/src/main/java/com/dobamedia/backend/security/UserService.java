package com.dobamedia.backend.security;

import com.dobamedia.backend.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final Utils utils;

    String notFound = "User not found";

    public User addUser(@NotNull DtoNewUser dtoNewUser) throws ResponseStatusException {
        if (userRepository.findByUsername(dtoNewUser.username()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, notFound);
        }
        String passwordBcrypt = utils.addPasswordBcrypt(dtoNewUser.password());
        User user = new User(
                utils.addUUIDasString(),
                dtoNewUser.username(),
                passwordBcrypt,
                "USER",
                dtoNewUser.email()
        );
        userRepository.save(user);
        return user;
    }

    User getUserDetails(String username) throws ResponseStatusException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, notFound));
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public User findByUsername(String usernameFromSession) {
        return userRepository.findByUsername(usernameFromSession)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, notFound));
    }
}
