package com.sanshi.fileserver.service;

import com.sanshi.fileserver.bean.Grade;
import com.sanshi.fileserver.vo.PageGet;

import java.util.List;
import java.util.Map;

public interface GradeService {
    /**
     * 通过用户身份和年份获取对象集合
     *
     * @param Ident
     * @param Id
     * @param yearNumber
     * @return
     */
    public List<Grade> findAll(Integer Ident, Integer Id, Integer yearNumber);

    /**
     * 通过用户身份获取年份list
     *
     * @param Ident
     * @param Id
     * @return
     */
    public List<Integer> findAllYear(Integer Ident, Integer Id);

    public Map GetGradesByyearNumber(PageGet val);

    /**
     * 获取学院信息
     */
    public Grade findOneById(Integer id);

    public Grade save(Grade grade);

    public Integer deleteById(Integer id);
}
