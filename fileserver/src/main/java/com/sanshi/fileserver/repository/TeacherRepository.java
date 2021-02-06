package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    List<Teacher> findByTeaName(String teaName);
    List<Teacher> findAllByTeaIdIn(List<Integer> ids);
    List<Teacher> findAllByTeaIdentityIn(List<Integer> idents);
    void deleteByTeaId(Integer teaId);
    Page<Teacher> findAllByTeaNameLike(String name, Pageable pa);
    Page<Teacher> findAllByTeaIdentityIn(List<Integer> i1,Pageable page);
    Page<Teacher> findAllByTeaIdentityInAndTeaNameLike(List<Integer> i1,String name,Pageable page);
}
