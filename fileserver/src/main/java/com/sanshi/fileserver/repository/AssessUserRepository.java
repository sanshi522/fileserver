package com.sanshi.fileserver.repository;



import com.sanshi.fileserver.bean.AssessUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessUserRepository extends JpaRepository<AssessUser,Integer>, JpaSpecificationExecutor {

    AssessUser save(AssessUser assessuser);
    void  deleteByAssessId(Integer id);
    List<AssessUser> findAllByAssessId(Integer  id);
    List<AssessUser> findAllByTestObjectAndTestObjectId(Integer testObject,Integer testObjectId);

    @Query(value="select s.assessId from AssessUser s where s.testObject =?1 and s.testObjectId=?2 ")
    List<Integer> findAssessIdAllByTestObjectAndTestObjectId(Integer testObject,Integer testObjectId );


}
