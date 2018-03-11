package io.fdlessard.codebites.hystrix.domain;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer implements Serializable {

    private String id;

    private String firstName;

    private String lastName;

    private String company;
}