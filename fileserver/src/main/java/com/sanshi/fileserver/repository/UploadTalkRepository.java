package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.UploadTalk;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadTalkRepository {
    UploadTalk save(UploadTalk uploadTalk);
}
