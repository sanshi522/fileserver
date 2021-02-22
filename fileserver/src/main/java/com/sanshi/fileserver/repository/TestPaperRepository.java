package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.TestPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TestPaperRepository extends JpaRepository<TestPaper,Integer>, JpaSpecificationExecutor {
}
