package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.ShareFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShareFileRepository extends JpaRepository<ShareFile,Integer> {
    ShareFile findByOwnerIdentAndOwnerIdAndFileId(Integer ownerIdent,Integer ownerId,Integer fileId);
    ShareFile save(ShareFile shareFile);
    List<Integer> findFileIdByOwnerIdentAndOwnerId(Integer ownerIdent, Integer ownerId);
    List<ShareFile> findByIdIn(List<Integer> list);


    @Query(value="select s.fileId from ShareFile s where s.id in ?1")
    /**
     *查询所有人授权到某处的文件
     * @param list 某处
     */
    List<Integer> findFileIdByIdIn(List<Integer> list);

    /**
     * 根据所属人查询授权到某处的文件
     * @param list 某处
     * @param ownerIdent
     * @param ownerId
     * @return
     */
    @Query(value="select s.fileId from ShareFile s where s.id in ?1 and s.ownerIdent=?2 and s.ownerId=?3")
    List<Integer> findFileIdByIdInAndOwnerIdentAndownerId(List<Integer> list,Integer ownerIdent,Integer ownerId);
}
