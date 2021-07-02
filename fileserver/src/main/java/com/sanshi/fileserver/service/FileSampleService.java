package com.sanshi.fileserver.service;

import com.sanshi.fileserver.bean.FileSample;
import com.sanshi.fileserver.vo.FileExists;
import com.sanshi.fileserver.vo.ScreenShareFile;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public interface FileSampleService {
    FileExists fileExists(String md5, Long size);

    FileSample insertFileSample(FileSample fileSample);

    FileSample findById(Integer Id);

    int deleteById(Integer id);

    int updateById(FileSample file);

    FileSample findByParentAndMd5(Integer parent, String md5);

    int deleteByParent(Integer parent);

    List<FileSample> findByParentOrderByPatchIndexAsc(Integer id);

    int updateByIdSetPathAndSize(Integer parent, String path, Long total);

    FileSample findByMd5(String md5);

    /**
     * 获取共享文件
     *
     * @param screenShareFile
     * @return
     */
    Map ScreenALL(ScreenShareFile screenShareFile, HttpServletRequest request);



    List<Integer>   ScreenALL2(ScreenShareFile screenShareFile, HttpServletRequest request);


    List<Integer>   ScreenMyALL2(ScreenShareFile screenShareFile, HttpServletRequest request);

    /**
     * 获取共享出去的文件
     *
     * @param screenShareFile
     * @return
     */
    Map ScreenMyALL(ScreenShareFile screenShareFile, HttpServletRequest request);

    /**
     * 试题挂载样本文件
     */

    Map choiceShareFile(ScreenShareFile screenShareFile, List<Integer> list);

}
