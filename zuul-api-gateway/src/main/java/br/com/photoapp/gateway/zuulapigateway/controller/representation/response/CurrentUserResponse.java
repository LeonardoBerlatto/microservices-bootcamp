package br.com.photoapp.gateway.zuulapigateway.controller.representation.response;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUserResponse {

    private String username;
    private List<String> roles;

}
