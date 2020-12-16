package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.ShareFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShareFileRepository extends JpaRepository<ShareFile,Integer> {
    ShareFile findByOwnerIdentAndOwnerIdAndFileId(Integer ownerIdent,Integer ownerId,Integer fileId);
    ShareFile save(ShareFile shareFile);
    List<Integer> findFileIdByOwnerIdentAndOwnerId(Integer ownerIdent, Integer ownerId);
    List<ShareFile> findByIdIn(List<Integer> list);
}
