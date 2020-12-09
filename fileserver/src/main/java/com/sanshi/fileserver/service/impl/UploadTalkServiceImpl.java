package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.UploadTalk;
import com.sanshi.fileserver.repository.UploadTalkRepository;
import com.sanshi.fileserver.service.UploadTalkService;
import org.springframework.stereotype.Service;

@Service("UploadTalkServiceImpl")
public class UploadTalkServiceImpl implements UploadTalkService {
    private final UploadTalkRepository uploadTalkRepository;

    public UploadTalkServiceImpl(UploadTalkRepository uploadTalkRepository) {
        this.uploadTalkRepository = uploadTalkRepository;
    }

    @Override
    public UploadTalk insertUploadTalk(UploadTalk uploadTalk) {
        return uploadTalkRepository.save(uploadTalk);
    }
}
