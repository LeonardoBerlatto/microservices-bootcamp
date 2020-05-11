package br.com.photoapp.eureka.photoappcommons.domain;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String password;

    private LocalDate birthDate;
}
