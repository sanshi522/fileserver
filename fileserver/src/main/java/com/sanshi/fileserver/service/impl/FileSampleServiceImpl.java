package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.FileSample;
import com.sanshi.fileserver.repository.FileSampleRepository;
import com.sanshi.fileserver.service.FileSampleService;
import com.sanshi.fileserver.utils.MergeObjectsUtil;
import com.sanshi.fileserver.vo.FileExists;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("FileSampleServiceImpl")
public class FileSampleServiceImpl implements FileSampleService {
    private final FileSampleRepository fileSampleRepository;

    public FileSampleServiceImpl(FileSampleRepository fileSampleRepository) {
        this.fileSampleRepository = fileSampleRepository;
    }


    @Override
    public FileExists fileExists(String md5, Long size) {
        FileSample file = fileSampleRepository.findByMd5(md5);
        if(file == null) {
            return FileExists.nonExistent();
        }
        if(file.getSize().equals(size)) {
            return FileExists.exists(file.getId());
        }
        return FileExists.partExistent(file.getId(), fileSampleRepository.findPatchIndexByParent(file.getId()));
    }

    @Override
    public FileSample insertFileSample(FileSample fileSample) {
      //  fileSample.setId(fileSampleRepository.insertFileSample(fileSample));
        fileSampleRepository.save(fileSample);
        return fileSample;
       // return null;
    }
    @Override
    public FileSample findById(Integer Id) {
        return fileSampleRepository.findById(Id).get();
    }
    @Override
    public int deleteById(Integer id) {
        fileSampleRepository.deleteById(id);
        return 1;
    }
    @Override
    public int updateById(FileSample file){
        FileSample fileSample =new FileSample();
        FileSample fileSample2 =new FileSample();
        fileSample2=fileSampleRepository.findById(file.getId()).get();
        fileSample=(FileSample) MergeObjectsUtil.isNullNoCover(fileSample2, file);
        if (null != fileSample){
            fileSampleRepository.save(fileSample);
            return 1;
        }else{
            return -1;
        }
    }
    @Override
    public FileSample findByParentAndMd5(Integer parent, String md5) {
        return fileSampleRepository.findByParentAndMd5(parent,md5);
    }
    @Override
    public int deleteByParent(Integer parent) {
        fileSampleRepository.deleteByParent(parent);
        return 1;
    }

    @Override
    public List<FileSample> findByParentOrderByPatchIndexAsc(Integer id) {
        return fileSampleRepository.findByParentOrderByPatchIndexAsc(id);
    }

    @Override
    public int updateByIdSetPathAndSize(Integer parent, String path, Long total) {
        FileSample fileSample=new FileSample(parent,null,null,null,path,null,total,null);
        return this.updateById(fileSample);
    }

    @Override
    public FileSample findByMd5(String md5) {
        return fileSampleRepository.findByMd5(md5);
    }

}
