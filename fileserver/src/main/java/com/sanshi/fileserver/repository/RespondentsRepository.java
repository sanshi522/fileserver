package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.Respondents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RespondentsRepository extends JpaRepository<Respondents,Integer>, JpaSpecificationExecutor {
}
