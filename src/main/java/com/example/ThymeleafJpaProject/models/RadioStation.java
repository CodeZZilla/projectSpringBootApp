package com.example.ThymeleafJpaProject.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "radiostation")
@Data
@NoArgsConstructor
public class RadioStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String radiostationName;
    private Integer count;

}
