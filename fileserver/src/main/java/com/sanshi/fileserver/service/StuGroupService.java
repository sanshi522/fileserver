package com.sanshi.fileserver.service;

import com.sanshi.fileserver.bean.StuGroup;

import java.util.List;

public interface StuGroupService {
    /**
     * 通过班级id获取小组集合
     * @param Id
     * @return
     */
    public List<StuGroup> findGroupsByCclassId(Integer Id);

    public StuGroup findOneById(Integer id);
}
