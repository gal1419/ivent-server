package com.app.controller;

import com.app.Application;
import com.app.module.ApplicationUser;
import com.app.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;


@RestController
@RequestMapping(path="/users")
public class UserController {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/sign-up")
    public void signUp(@RequestBody ApplicationUser user) {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setFirstName(user.getFirstName());
        applicationUser.setLastName(user.getLastName());
        applicationUser.setEmail(user.getEmail());
        applicationUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        applicationUserRepository.save(applicationUser);
    }

    @PostMapping("/login")
    public @ResponseBody ApplicationUser login(@RequestBody ApplicationUser user) {
        return applicationUserRepository.findByEmail(user.getEmail());
    }

    @GetMapping("/user")
    public @ResponseBody ApplicationUser getUser(HttpServletRequest request) {
        Principal p = request.getUserPrincipal();
        return applicationUserRepository.findByEmail(p.getName());
    }
}