package com.example.LoanApp2.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;

import static jakarta.persistence.GenerationType.AUTO;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User  {
    @GeneratedValue(strategy =AUTO)
    @Id
    private long id;
    private String name;
    private String username;
    private String password ;
   // @Enumerated(EnumType.STRING)
@ManyToMany(fetch = FetchType.EAGER)
    private Collection <Role> roles=new ArrayList<>();

}
