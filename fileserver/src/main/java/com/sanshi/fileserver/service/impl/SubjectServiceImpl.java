package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.Subject;
import com.sanshi.fileserver.repository.SubjectRepository;
import com.sanshi.fileserver.service.SubjectService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("SubjectServiceImpl")
public class SubjectServiceImpl implements SubjectService {
    private SubjectRepository subjectRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    @Override
    public Subject findById(Integer id) {
        return subjectRepository.findOneById(id);
    }

    @Override
    public Subject save(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    public void deleteById(Integer id) {
        subjectRepository.deleteById(id);
    }
}
