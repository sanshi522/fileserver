package com.sanshi.fileserver.service;

import com.sanshi.fileserver.bean.Cclass;
import com.sanshi.fileserver.vo.PageGet;

import java.util.List;
import java.util.Map;

public interface CclassService {
    /**
     * 通过学院id获取班级集合
     *
     * @param GradeId
     * @return
     */
    public List<Cclass> findCclasesByGradeId(Integer ident, Integer id, Integer GradeId);

    public Map GetClasesByClassId(PageGet val);

    /**
     * 通过id获取院系id
     *
     * @param Id
     * @return
     */
    public Cclass findOneById(Integer Id);

    public Cclass save(Cclass cclass);
}
