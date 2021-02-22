package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.TestPaperBindChoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TestPaperBindChoiceRepository extends JpaRepository<TestPaperBindChoice,Integer>, JpaSpecificationExecutor {
}
