package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice,Integer>, JpaSpecificationExecutor {
}
