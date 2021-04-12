package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.Assess;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 考核dao
 */
@Repository
public interface AssessRepository extends JpaRepository<Assess,Integer>, JpaSpecificationExecutor {
    /**
     * 通过id获取考核信息
     * @param id
     * @return
     */
    Assess findOneById(Integer id);

    /**
     *学生获取指定学科学生可以加入的考核
     */
//    List<Assess> findAllBySubIdAndTestObjectAndTestObjectIdAndStartTimeLessThanAndEndTimeGreaterThan(Integer subid, Integer testObject, Integer testObjectId, Date start,Date end);
    /**
     * 学生获取学生可以加入的所有考核
     */
//    List<Assess> findAllByTestObjectAndTestObjectIdAndStartTimeLessThanAndEndTimeGreaterThan(Integer testObject, Integer testObjectId, Date start,Date end);

    /**
     * 老师获取指定学科本人发布的进行中考核
     */
    List<Assess> findAllBySubIdAndIssueIdAndStartTimeLessThanAndEndTimeGreaterThan(Integer subid, Integer issueId, Date start,Date end);
    /**
     * 老师获取本人发布进行中考核
     */
    List<Assess> findAllByIssueIdAndStartTimeLessThanAndEndTimeGreaterThan( Integer issueId, Date start,Date end);

    /**
     * 老师获取指定学科本人发布的进行中考核
     */
    List<Assess> findAllBySubIdAndIssueIdAndStartTimeGreaterThanAndEndTimeLessThan(Integer subid, Integer issueId, Date start,Date end);
    /**
     * 老师获取本人发布进行中考核
     */
    List<Assess> findAllByIssueIdAndStartTimeGreaterThanAndEndTimeLessThan( Integer issueId, Date start,Date end);
    /**
     * 管理员获取指定学科的进行中考核
     */
    List<Assess> findAllBySubIdAndStartTimeLessThanAndEndTimeGreaterThan(Integer subid, Date start,Date end);
    /**
     * 管理员获取未指定进行中考核
     */
    List<Assess> findAllByStartTimeLessThanAndEndTimeGreaterThan( Date start,Date end);
    /**
     * 管理员获取指定学科的未进行的考核
     */
    List<Assess> findAllBySubIdAndStartTimeGreaterThanAndEndTimeLessThan(Integer subid, Date start,Date end);
    /**
     * 管理员获取未指定未进行的考核
     */
    List<Assess> findAllByStartTimeGreaterThanAndEndTimeLessThan( Date start,Date end);

    /**
     * 老师获取所有的考核
     */

    Page<Assess> findAll(Pageable pa);
    /**
     * 筛选学科选项
     */
  Page <Assess> findAllBySubId(Integer subId,Pageable pa);





    /**
     * 添加修改考核信息
     * @param assess
     * @return
     */
    Assess save(Assess assess);

    /**
     * 删除考核信息
     */
    void deleteById(Integer id);
}
