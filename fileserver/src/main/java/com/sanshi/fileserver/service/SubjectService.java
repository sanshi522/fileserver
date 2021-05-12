package com.sanshi.fileserver.service;

import com.sanshi.fileserver.bean.Subject;
import com.sanshi.fileserver.vo.PageGet;

import java.util.List;
import java.util.Map;

public interface SubjectService {
    List<Subject> findAll();

    Subject findById(Integer id);

    Subject save(Subject subject);

    void deleteById(Integer id);

    Map getAll(PageGet val);
}
