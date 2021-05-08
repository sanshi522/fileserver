package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.ShareRight;
import com.sanshi.fileserver.repository.ShareRightRepository;
import com.sanshi.fileserver.service.ShareRightService;
import org.springframework.stereotype.Service;

@Service("ShareRightServiceImpl")
public class ShareRightServiceImpl implements ShareRightService {
    private ShareRightRepository shareRightRepository;

    public ShareRightServiceImpl(ShareRightRepository shareRightRepository) {
        this.shareRightRepository = shareRightRepository;
    }

    @Override
    public Integer AddIsHaveUpTime(ShareRight shareRight) {
        ShareRight shareRight1;
        shareRight1 = shareRightRepository.findShareRightByShareIdentAndShareIdAndShareFileId(shareRight.getShareIdent(), shareRight.getShareId(), shareRight.getShareFileId());
        if (shareRight1 != null) {//修改共享时间
            shareRight1.setAllottedTime(shareRight.getAllottedTime());
            shareRightRepository.save(shareRight1);
        } else {//直接插入
            shareRightRepository.save(shareRight);
        }
        return 1;
    }
}
