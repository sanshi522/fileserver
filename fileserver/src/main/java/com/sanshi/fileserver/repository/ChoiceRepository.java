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


    Page<Choice> findAllByAbilityIdsAndTopicLike(String AbilityIds,String name,Pageable pageable);

    Page<Choice> findAllByTypeAndTopicLikeAndAbilityIds(Integer type,String name,String AbilityIds,Pageable pageable);

    Page<Choice> findAllBySubIdAndTopicLikeAndAbilityIds(Integer subid,String name,String AbilityIds,Pageable pageable);

    Page<Choice> findAllBySubIdAndTypeAndTopicLikeAndAbilityIds(Integer subid, Integer type, String name,String AbilityIds,Pageable pageable);


    Page<Choice> findAllByTopicLikeAndDifficultyLevel(String name,Integer difficultyLevel, Pageable pageable);

    Page<Choice> findAllByTypeAndTopicLikeAndDifficultyLevel(Integer type,String name,Integer difficultyLevel,Pageable pageable);

    Page<Choice> findAllBySubIdAndTopicLikeAndDifficultyLevel(Integer subid,String name,Integer difficultyLevel,Pageable pageable);

    Page<Choice> findAllBySubIdAndTypeAndTopicLikeAndDifficultyLevel(Integer subid, Integer type, String name,Integer difficultyLevel,Pageable pageable);


    Page<Choice> findAllByAbilityIdsAndTopicLikeAndDifficultyLevel(String AbilityIds,String name,Integer difficultyLevel,Pageable pageable);

    Page<Choice> findAllByTypeAndTopicLikeAndAbilityIdsAndDifficultyLevel(Integer type,String name,String AbilityIds,Integer difficultyLevel,Pageable pageable);

    Page<Choice> findAllBySubIdAndTopicLikeAndAbilityIdsAndDifficultyLevel(Integer subid,String name,String AbilityIds,Integer difficultyLevel,Pageable pageable);

    Page<Choice> findAllBySubIdAndTypeAndTopicLikeAndAbilityIdsAndDifficultyLevel(Integer subid, Integer type, String name,String AbilityIds,Integer difficultyLevel,Pageable pageable);

    List<Choice> findAllByTopic(String name);

    /**
     * 通过id删除试题
     * @param id
     */
    void deleteById(Integer id);


    List<Choice> findByIdIn(List<Integer>  testPaperIds);

    /**
     * 筛选出须人工查询的试题
     * @param choiceId
     * @param scaleRule
     * @return
     */
    Choice findOneByIdAndScaleRule(Integer choiceId,Integer scaleRule);
    /**
     * 根据难度星级,学科，类型获取题
     */
    List<Choice> findAllByDifficultyLevelInAndTypeAndSubId(Integer[] arr,Integer type,Integer subId);

    /**
     * 根据学科，类型获取题
     */

    List<Choice> findAllByTypeAndSubId(Integer type,Integer subId);


}
