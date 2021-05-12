package com.sanshi.fileserver.service;

import com.sanshi.fileserver.bean.StuGroup;
import com.sanshi.fileserver.bean.Student;
import com.sanshi.fileserver.vo.PageGet;

import java.util.List;
import java.util.Map;

public interface StuGroupService {
    /**
     * 通过班级id获取小组集合
     *
     * @param Id
     * @return
     */
    public List<StuGroup> findGroupsByCclassId(Integer Id);

    public Map findGroupByCclassId(PageGet val);

    public StuGroup findOneById(Integer id);

    StuGroup save(StuGroup student);

    Integer deleteById(Integer val);

    StuGroup findOneByName(Integer classId, String groupName);
}
