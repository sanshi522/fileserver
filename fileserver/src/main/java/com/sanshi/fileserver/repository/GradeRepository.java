package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.Grade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade,Integer>, JpaSpecificationExecutor {


    /**
     * 根据id获取年级对象
     * @param id
     * @return
     */
    Grade findOneById(Integer id);

    /**
     * 获取年级id集合
     * @return
     */
    List<Grade> findAllByYear(Integer Year);

    Page<Grade> findAllByYear(Integer Year,Pageable pageable);

    Page<Grade> findAllByYearAndNameLike(Integer tear, String name, Pageable pageable);

    /**
     * 获取年级id集合
     * @return
     */
    List<Grade> findAllByYearAndIdIn(Integer Year,List<Integer> ids);
    /**
     * 一组院系的学年分组查询
     * @return
     */
    @Query(value="select s.year from Grade s where s.id in ?1 group by s.year")
    List<Integer> findYearsByIdIn(List<Integer> list);

    @Query(value="select s.year from Grade s group by s.year")
    List<Integer> findAllYears();
    /**
     * 一组院系根据学年过滤
     */
    @Query(value="select s.id from Grade s where s.id in ?1 and s.year= ?2")
    List<Integer> findIdByIdInAndYear(List<Integer> list,Integer year);
    /**
     * 根据学年获取一组院系
     */
    @Query(value="select s.id from Grade s where  s.year= ?1")
    List<Integer> findIdsByYear(Integer year);

    /**
     * 查询所有id
     * @return
     */
    @Query(value="select s.id from Grade s")
    List<Integer> findAllId();
}
