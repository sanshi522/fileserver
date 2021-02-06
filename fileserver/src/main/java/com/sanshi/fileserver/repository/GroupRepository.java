package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.StuGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<StuGroup,Integer>, JpaSpecificationExecutor {
    /**
     *  根据id获取小组信息
     * @param id
     * @return
     */
    StuGroup findOneById(Integer id);

    /**
     * 根据班级id获取小组集合
     * @param id
     * @return
     */
    List<StuGroup> findAllByCclassId(Integer id);
    /**
     * 根据班级id获取小组集合
     * @param id
     * @return
     */
    Page<StuGroup> findAllByCclassId(Integer id, Pageable pageable);
    /**
     * 根据班级id获取小组集合
     * @param id
     * @return
     */
    Page<StuGroup> findAllByCclassIdAndNameLike(Integer id, String name,Pageable pageable);

    /**
     * 根据班级id获取小组id集合
     * @param id
     * @return
     */
    @Query(value="select s.id from StuGroup s where  s.cclassId= ?1")
    List<Integer> findALLIdByCclassId(Integer id);
    /**
     * 根据班级id集合获取小组id集合
     */
    @Query(value="select s.id from StuGroup s where  s.cclassId in ?1")
    List<Integer> findALLIdByCclassIdIn(List<Integer> ids);


}
