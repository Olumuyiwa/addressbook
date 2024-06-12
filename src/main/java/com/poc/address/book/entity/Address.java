package com.poc.address.book.entity;

// src/main/java/com/example/addressbook/Address.java


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phonenumber;


}
