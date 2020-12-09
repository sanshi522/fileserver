package com.sanshi.fileserver.service;

import com.sanshi.fileserver.bean.FileSample;
import com.sanshi.fileserver.vo.FileExists;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface FileSampleService {
    FileExists fileExists(String md5, Long size);
    FileSample insertFileSample(FileSample fileSample);
    FileSample findById(Integer Id);
    int deleteById(Integer id);
    int updateById(FileSample file);
    FileSample findByParentAndMd5(Integer parent, String md5);
    int deleteByParent(Integer parent);
    List<FileSample> findByParentOrderByPatchIndexAsc(Integer id);
    int updateByIdSetPathAndSize(Integer parent,String path,Long total);
    FileSample findByMd5(String md5);
}
