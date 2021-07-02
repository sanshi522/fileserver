package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.TeacherBindCclass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherBindCclassRepository extends JpaRepository<TeacherBindCclass, Integer> {
    /**
     * 根据教师id获取班级组
     *
     * @return
     */
    @Query(value = "select s.cclassId from TeacherBindCclass s where s.teeaId = ?1")
    List<Integer> findCclasesIdByTeaid(Integer teaid);

    /**
     * 根据班级组获取同班教学的老师ids
     */
    @Query(value = "select s.teeaId from TeacherBindCclass s where s.cclassId in ?1")
    List<Integer> findIdsByTeaCclasesIdIn(List<Integer> ids);

    /**
     * 根据班级id获取同班教学的老师ids
     */
    @Query(value = "select s.teeaId from TeacherBindCclass s where s.cclassId = ?1")
    List<Integer> findIdsByTeaCclasesId(Integer teaid);

    Page<TeacherBindCclass> findAllByCclassId(Integer cid, Pageable pa);

    TeacherBindCclass save(TeacherBindCclass t);

    void deleteById(Integer id);

    void deleteByTeeaId(Integer id);
}
