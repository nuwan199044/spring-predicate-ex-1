package com.myapp.predicateex1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchRequest {
    private String firstname;
    private String lastname;
    private String email;
}
