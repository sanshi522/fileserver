package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository  extends JpaRepository<Subject,Integer>, JpaSpecificationExecutor {
    List<Subject> findAll();
    Subject findOneById(Integer id);
    Subject save(Subject sub);
    void deleteById(Integer id);
}