package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.KnowledgePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface KnowledgePointRepository extends JpaRepository<KnowledgePoint,Integer>, JpaSpecificationExecutor {
}
