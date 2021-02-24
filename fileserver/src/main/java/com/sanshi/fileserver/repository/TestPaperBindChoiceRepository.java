package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.TestPaperBindChoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 试题试卷绑定dao
 */
@Repository
public interface TestPaperBindChoiceRepository extends JpaRepository<TestPaperBindChoice,Integer>, JpaSpecificationExecutor {
    /**
     * 通过试卷id获取绑定集合
     * @param id
     * @return
     */
    List<TestPaperBindChoice> findAllByTestPaperId(Integer id);

    /**
     * 添加修改试题试卷绑定
     * @param t
     * @return
     */
    TestPaperBindChoice save(TestPaperBindChoice t);

    /**
     * 通过id删除试题试卷绑定
     * @param id
     */
    void deleteById(Integer id);

}
