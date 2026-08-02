package ru.job4j.smapi.security.dtos.response;

import lombok.Data;

import java.util.List;
@Data
public class JwtResponseDTO {
    private String token;
    private String type = "Bearer";
    private Integer id;
    private String name;
    private String email;
    private List<String> roles;

    public JwtResponseDTO(String accessToken, Integer id, String name, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.name = name;
        this.email = email;
        this.roles = roles;
    }
}
