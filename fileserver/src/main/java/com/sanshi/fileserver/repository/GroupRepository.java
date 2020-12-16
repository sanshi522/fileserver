package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group,Integer>, JpaSpecificationExecutor {
    /**
     *  根据id获取小组信息
     * @param id
     * @return
     */
    Group findOneById(Integer id);

    /**
     * 根据班级id获取小组集合
     * @param id
     * @return
     */
    List<Group> findByCclassId(Integer id);
}
