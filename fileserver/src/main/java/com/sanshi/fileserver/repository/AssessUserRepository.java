package com.sanshi.fileserver.repository;


import com.sanshi.fileserver.bean.Assess;
import com.sanshi.fileserver.bean.AssessUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AssessUserRepository extends JpaRepository<AssessUser,Integer>, JpaSpecificationExecutor {

    AssessUser save(AssessUser assessuser);
}
