package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.TestPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TestPaperRepository extends JpaRepository<TestPaper,Integer>, JpaSpecificationExecutor {
    /**
     * 添加修改试卷
     * @param t
     * @return
     */
    TestPaper save(TestPaper t);

    /**
     * 通过id删除试卷
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 通过id获取试卷
     * @param id
     * @return
     */
    TestPaper findOneById(Integer id);
}
