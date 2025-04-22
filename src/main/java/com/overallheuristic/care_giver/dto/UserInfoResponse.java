package com.overallheuristic.care_giver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {
    private Long id;
    private String jwtToken;
    private String username;
    private String name;
    private List<String> roles;

    public UserInfoResponse(Long id, String username,String name, List<String> roles, String jwtToken) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.roles = roles;
        this.jwtToken = jwtToken;
    }

    public UserInfoResponse(Long id, String username, String name,List<String> roles) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.roles = roles;
    }

}


