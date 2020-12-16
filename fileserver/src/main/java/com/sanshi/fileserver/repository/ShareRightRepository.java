package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.ShareRight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShareRightRepository  extends JpaRepository<ShareRight,Integer> {
    ShareRight findShareRightByShareIdentAndShareIdAndShareFileId(Integer shareIdent,Integer shareId,Integer shareFileId);
    ShareRight save(ShareRight shareRight);
    List<Integer> findShareFileIdByShareIdentAndShareId(Integer shareIdent,Integer shareId);
}
