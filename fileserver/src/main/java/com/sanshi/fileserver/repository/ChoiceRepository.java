package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.Choice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 试题dao
 */
@Repository
public interface ChoiceRepository extends JpaRepository<Choice,Integer>, JpaSpecificationExecutor {
    /**
     * 添加修改试题
     * @param c
     * @return
     */
    Choice save(Choice c);

    /**
     *通过id获取试题信息
     * @param id
     * @return
     */
    Choice findOneById(Integer id);

    Page<Choice> findAllBySubIdAndTypeAndTopicLike(Integer subid, Integer type, String name,Pageable pageable);

    Page<Choice> findAllBySubIdAndTopicLike(Integer subid,String name,Pageable pageable);

    Page<Choice> findAllByTypeAndTopicLike(Integer type,String name,Pageable pageable);

    Page<Choice> findAllByTopicLike(String name,Pageable pageable);

    Page<Choice> findAllBySubIdAndType(Integer subid,Integer type,Pageable pageable);

    Page<Choice> findAllBySubId(Integer subid,Pageable pageable);

    Page<Choice> findAllByType(Integer type,Pageable pageable);

    Page<Choice> findAll(Pageable pageable);
    /**
     * 通过id删除试题
     * @param id
     */
    void deleteById(Integer id);


    List<Choice> findByIdIn(List<Integer>  testPaperIds);

}
