package com.sanshi.fileserver.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 年级
 */
@Entity
public class Grade {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Integer id;
    String name;
    Integer year;

    public Grade() {
    }

    public Grade(Integer id, String name, Integer year) {
        this.id = id;
        this.name = name;
        this.year = year;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                '}';
    }
}
