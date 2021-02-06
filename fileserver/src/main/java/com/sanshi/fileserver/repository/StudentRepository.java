package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student save(Student stu);
    List<Student> findAll();
    List<Student> findByStuNumber(String stuNumber);
    void deleteById(Integer stuId);

    /**
     * 根据id获取学生
     * @param stuId
     * @return
     */
    Student findOneByStuId(Integer stuId);

    /**
     * 根据小组id组查询学生id集合
     * @return
     */
    @Query(value="select s.stuId from Student s where  s.stuGroup = ?1")
    List<Integer>  findIdsByStuGroup(Integer ids);

    /**
     * 根据小组id组查询学生id集合
     * @return
     */
    @Query(value="select s.stuId from Student s where  s.stuGroup in ?1")
    List<Integer>  findIdsByStuGroupIn(List<Integer> ids);

    List<Student> findAllByStuGroup(Integer StuGroup);

    Page<Student> findAllByStuGroupIn(List<Integer> StuGroups, Pageable pageable);
    Page<Student> findAllByStuGroupInAndStuNameLike(List<Integer> StuGroups, String name,Pageable pageable);
}
