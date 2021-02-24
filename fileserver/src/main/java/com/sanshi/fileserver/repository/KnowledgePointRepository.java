package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.KnowledgePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KnowledgePointRepository extends JpaRepository<KnowledgePoint,Integer>, JpaSpecificationExecutor {
    /**
     *
     * @param ids
     * @return
     */
    List<KnowledgePoint> findAllByIdIn(List<Integer> ids);

    /**
     * 添加修改知识点
     * @param k
     * @return
     */
    KnowledgePoint save(KnowledgePoint k);

    /**
     * 删除知识点
     * @param id
     */
    void deleteById(Integer id);
}
