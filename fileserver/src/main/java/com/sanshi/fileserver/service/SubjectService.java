package com.sanshi.fileserver.service;

import com.sanshi.fileserver.bean.Subject;

import java.util.List;

public interface SubjectService {
    List<Subject> findAll();
    Subject findById(Integer id);
    Subject save(Subject subject);
    void deleteById(Integer id);
}
