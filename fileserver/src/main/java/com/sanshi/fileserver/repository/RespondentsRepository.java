package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.Respondents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 答卷dao
 */
@Repository
public interface RespondentsRepository extends JpaRepository<Respondents,Integer>, JpaSpecificationExecutor {
    /**
     * 通过考核id获取答卷
     * @param id
     * @return
     */
    List<Respondents> findAllByTestPaperId(Integer id);

    /**
     * 通过答题人id获取答卷
     * @param id
     * @return
     */
    List<Respondents> findAllBystuId(Integer id);
    /**
     *添加修改答卷信息
     * @param re
     * @return
     */
    Respondents save(Respondents re);

    /**
     * 通过id删除答卷
     * @param id
     */
    void deleteById(Integer id);
}
