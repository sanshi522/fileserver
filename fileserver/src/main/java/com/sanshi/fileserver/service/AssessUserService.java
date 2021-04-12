package com.sanshi.fileserver.service;

import com.sanshi.fileserver.bean.AssessUser;

import java.util.List;

/**
 * 考核与考核对象绑定表
 */
public interface AssessUserService {

    public   int  save(List<AssessUser> list);
}
