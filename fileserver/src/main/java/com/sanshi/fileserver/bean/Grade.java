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
    String Name;

    public Grade() {
    }

    public Grade(Integer id, String name) {
        this.id = id;
        Name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", Name='" + Name + '\'' +
                '}';
    }
}
