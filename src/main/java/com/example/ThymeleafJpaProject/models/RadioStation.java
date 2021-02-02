package com.example.ThymeleafJpaProject.models;

import javax.persistence.*;

@Entity(name = "radiostation")
public class RadioStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String radiostationName;
    private Integer count;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRadiostationName() {
        return radiostationName;
    }

    public void setRadiostationName(String name) {
        this.radiostationName = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
