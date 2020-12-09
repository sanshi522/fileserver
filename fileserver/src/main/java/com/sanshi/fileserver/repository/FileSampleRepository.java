package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.FileSample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileSampleRepository extends JpaRepository<FileSample,Integer> {
    //@Insert("insert into file_sample(patch_index,parent,name,path,md5,size) value(#{file.patchIndex},#{file.parent},#{file.name},#{file.path},#{file.md5},#{file.size})")
   // @SelectKey(statement = "select last_insert_id()",keyProperty="file.id",before = false,resultType = int.class )

    /**
     * 添加记录
     * @param file
     */
    @Modifying
    @Query(value = "insert into file_sample(patch_index,parent,name,path,md5,size) value(:#{#file.patchIndex},:#{#file.name},:#{#file.parent},:#{#file.md5}},:#{#file.size})", nativeQuery = true)
    void insertFileSample(FileSample file);

    FileSample save(FileSample file);

    Optional<FileSample> findById(Integer id);
    /**
     * 通过MD5和父类id查询
     * @param parent
     * @param md5
     * @return
     */
    FileSample findByParentAndMd5(Integer parent,String md5);

    /**
     * 根据id删除
     * @param id
     */
    void deleteById(Integer id);
    /**
     * 根据父级id删除记录
     * @param parent
     */
    void deleteByParent(Integer parent);
    List<FileSample> findByParentOrderByPatchIndexAsc(Integer id);
    FileSample findByMd5(String md5);
    List<Integer> findPatchIndexByParent(Integer id);

}
