package hexlet.code.controller;

import hexlet.code.dto.AuthRequest;
import hexlet.code.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class AuthenticationController {
    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("")
    public String create(@RequestBody AuthRequest authRequest) {

        var user = authRequest.getUsername();
        var pass = authRequest.getPassword();

        var authentication = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(), authRequest.getPassword());


        var us = authentication.getPrincipal();
        var pas = authentication.getCredentials();

        authenticationManager.authenticate(authentication);

        boolean isAuth = authentication.isAuthenticated();

        var token = jwtUtils.generateToken(authRequest.getUsername());


        return token;
    }
}
