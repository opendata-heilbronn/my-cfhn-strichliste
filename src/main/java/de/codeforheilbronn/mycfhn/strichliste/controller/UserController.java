package de.codeforheilbronn.mycfhn.strichliste.controller;

import de.codeforheilbronn.mycfhn.strichliste.auth.Authenticated;
import de.codeforheilbronn.mycfhn.strichliste.auth.Authorized;
import de.codeforheilbronn.mycfhn.strichliste.model.api.GuestCreateRequest;
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
    @Authenticated
    public List<UserModel> getUsers() {
        return userService.getUserOverview();
    }

    @PostMapping
    @Authenticated
    public UserModel createGuest(
            @RequestBody GuestCreateRequest guestCreateRequest
    ) {
        return userService.createGuest(guestCreateRequest.getUsername());
    }

    @DeleteMapping("{guestName}")
    @Authenticated
    @Authorized(groups = {"boardMembers", "infrastructureAdmins"})
    public UserModel deleteGuest(
            @PathVariable String guestName
    ) {
        return userService.deleteGuest(guestName).orElseThrow(() -> new ResourceNotFoundException(guestName));
    }

    @PostMapping("{username}/consumption")
    @Authenticated
    public UserModel consume(
            @PathVariable String username,
            @RequestBody Map<String, Long> consumption
    ) {
        return userService.consume(username, consumption).orElseThrow(() -> new ResourceNotFoundException("username"));
    }

    @PostMapping("{username}/balance")
    @Authenticated
    public UserModel pay(
            @PathVariable String username,
            @RequestBody Long amount
    ) {
        return userService.pay(username, amount).orElseThrow(() -> new ResourceNotFoundException("username"));
    }
}
