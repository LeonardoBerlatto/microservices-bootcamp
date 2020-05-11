package br.com.photoapp.api.usermanagement.web.representation.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String token;

    private Integer expirationTime;

}
