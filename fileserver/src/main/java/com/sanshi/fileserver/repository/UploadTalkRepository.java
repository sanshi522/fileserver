package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.UploadTalk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadTalkRepository extends JpaRepository<UploadTalk, Integer> {
    UploadTalk save(UploadTalk uploadTalk);
}
