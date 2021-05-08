package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.ShareFile;
import com.sanshi.fileserver.repository.ShareFileRepository;
import com.sanshi.fileserver.service.ShareFileService;
import org.springframework.stereotype.Service;

@Service("ShareFileServiceImpl")
public class ShareFileServiceImpl implements ShareFileService {
    private final ShareFileRepository shareFileRepository;

    public ShareFileServiceImpl(ShareFileRepository shareFileRepository) {
        this.shareFileRepository = shareFileRepository;
    }

    @Override
    public Integer getIdIsNoAdd(ShareFile shareFile) {
        ShareFile shareFile1 = shareFileRepository.findByOwnerIdentAndOwnerIdAndFileId(shareFile.getOwnerIdent(), shareFile.getOwnerId(), shareFile.getFileId());
        if (shareFile1 != null) {
            return shareFile1.getId();
        } else {
            shareFileRepository.save(shareFile);
            return shareFile.getId();
        }
    }
}
