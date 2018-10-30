package de.codeforheilbronn.mycfhn.strichliste.controller;

import de.codeforheilbronn.mycfhn.strichliste.model.api.UserModel;
import de.codeforheilbronn.mycfhn.strichliste.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("users")
@RestController
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping
    public List<UserModel> getUsers() {
        return userService.getUserOverview();
    }

    @PostMapping("{username}/consumption")
    public UserModel consume(
            @PathVariable String username,
            @RequestBody Map<String, Long> consumption
    ) {
        return userService.consume(username, consumption).orElseThrow(() -> new ResourceNotFoundException("username"));
    }

    @PostMapping("{username}/balance")
    public UserModel pay(
            @PathVariable String username,
            @RequestBody Long amount
    ) {
        return userService.pay(username, amount).orElseThrow(() -> new ResourceNotFoundException("username"));
    }
}
