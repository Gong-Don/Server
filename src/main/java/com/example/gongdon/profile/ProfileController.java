package com.example.gongdon.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProfileController {
    private final Environment env;

    @GetMapping("/profile")
    public String getProfile(){
        List<String> profiles = Arrays.asList(env.getActiveProfiles());
        List<String> prodProfiles = Arrays.asList("set1", "set2");
        String defaultProfile = profiles.get(0);

        System.out.println(defaultProfile);
        return Arrays.stream(env.getActiveProfiles()).filter(prodProfiles::contains).findAny().orElse(defaultProfile);
    }
}
