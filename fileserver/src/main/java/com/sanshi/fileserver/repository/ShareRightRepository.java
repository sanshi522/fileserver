package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.ShareRight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShareRightRepository extends JpaRepository<ShareRight, Integer> {


    /**
     * 查询授权某个级别某个目标的所有文件id
     */
    @Query(value = "select s.shareFileId from ShareRight s where s.shareIdent=?1 and s.shareId=?2")
    List<Integer> findIdByShareIdentAndShareId(Integer shareIdent, Integer shareId);

    /**
     * 查询授权某个级别某个目标集合的所有文件id
     */
    @Query(value = "select s.shareFileId from ShareRight s where s.shareIdent=?1 and s.shareId in ?2")
    List<Integer> findIdByShareIdentAndShareIdIn(Integer shareIdent, List<Integer> shareIdList);

    /**
     * 查询授权某个级别的
     */
    List<Integer> findIdByShareIdent(Integer shareIdent);

    ShareRight findShareRightByShareIdentAndShareIdAndShareFileId(Integer shareIdent, Integer shareId, Integer shareFileId);

    ShareRight save(ShareRight shareRight);

    //@Query(value = "select s.share_file_id from share_right as s where s.share_ident=?1 and s.share_id=?2", nativeQuery = true);

}
