package com.sanshi.fileserver.service;

import com.sanshi.fileserver.bean.ShareRight;

public interface ShareRightService {
    /**
     * 添加，如果存在修改共享时间
     * @param shareRight
     * @return
     */
    Integer AddIsHaveUpTime(ShareRight shareRight);
}
