package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.Assess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AssessRepository extends JpaRepository<Assess,Integer>, JpaSpecificationExecutor {

}
