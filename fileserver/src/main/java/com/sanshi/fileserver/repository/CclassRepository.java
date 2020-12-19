package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.Cclass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CclassRepository extends JpaRepository<Cclass,Integer>, JpaSpecificationExecutor {
    /**
     * 根据id获班级信息
     * @param id
     * @return
     */
    Cclass findOneById(Integer id);

    /**
     * 根据院系id获取班级集合
     * @param id
     * @return
     */
    List<Cclass> findByGradeId(Integer id);

    /**
     * 根据班级id集合获取院系id集合
     * @param ids
     * @return
     */
    @Query(value="select s.gradeId from Cclass s where s.id in ?1")
    List<Integer> findGradeIdsByIdIn(List<Integer> ids);

    /**
     * 查询班级班级id在班级id集合内且 院系id在院系集合
     * @param ids
     * @param GradeIds
     * @return
     */
    @Query(value="select s.id from Cclass s where s.id in ?1 and s.gradeId in ?2")
    List<Integer> findIdsByIdInAndGradeIdIn(List<Integer> ids,List<Integer> GradeIds);
    /**
     * 查询院系组的所有班级id
     */
    @Query(value="select s.id from Cclass s where s.gradeId in ?2")
    List<Integer> findIdsByGradeIdIn(List<Integer> GradeIds);

    /**
     * 查询班级班级id在班级id集合内且 院系id=
     * @param ids
     * @param GradeId
     * @return
     */
    @Query(value="select s.id from Cclass s where s.id in ?1 and s.gradeId = ?2")
    List<Integer>findIdsByIdInAndGradeId(List<Integer> ids,Integer GradeId);

    /**
     * 查询某院系的所有班级id
     * @param GradeId
     * @return
     */
    @Query(value="select s.id from Cclass s where s.gradeId = ?2")
    List<Integer>findIdsByGradeId( Integer GradeId);
    /**
     * 查询所有院系
     */
    @Query(value="select s.id from Cclass s")
    List<Integer> findAllId();
}
