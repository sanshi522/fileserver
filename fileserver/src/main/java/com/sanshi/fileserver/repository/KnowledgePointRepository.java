package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.KnowledgePoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KnowledgePointRepository extends JpaRepository<KnowledgePoint, Integer>, JpaSpecificationExecutor {
    /**
     * @param ids
     * @return
     */
    List<KnowledgePoint> findAllByIdIn(List<Integer> ids);

    List<KnowledgePoint> findAllBySubIdAndNameLike(Integer subId, String name);

    Page<KnowledgePoint> findAllBySubIdAndNameLike(Integer subId, String name, Pageable pageable);

    Page<KnowledgePoint> findAllByNameLike(String name, Pageable pageable);

    KnowledgePoint findOneBySubIdAndName(Integer subId, String name);

    /**
     * @param id
     * @return
     */
    KnowledgePoint findOneById(Integer id);

    /**
     * 添加修改知识点
     *
     * @param k
     * @return
     */
    KnowledgePoint save(KnowledgePoint k);

    /**
     * 删除知识点
     *
     * @param id
     */
    void deleteById(Integer id);


    void deleteBySubId(Integer id);

    /**
     * 获取知识点
     */

    List<KnowledgePoint> findAllBySubId(Integer id);


}
