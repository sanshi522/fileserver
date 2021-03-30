package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.Accessory;
import com.sanshi.fileserver.repository.AccessoryRepository;
import com.sanshi.fileserver.repository.FileSampleRepository;
import com.sanshi.fileserver.service.AccessoryService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("AccessoryServiceImpl")
public class AccessoryServiceImpl implements AccessoryService {
    private AccessoryRepository accessoryRepository;
    private FileSampleRepository fileSampleRepository;

    public AccessoryServiceImpl(AccessoryRepository accessoryRepository, FileSampleRepository fileSampleRepository) {
        this.accessoryRepository = accessoryRepository;
        this.fileSampleRepository = fileSampleRepository;
    }

    @Override
    public Map save(Integer library, Integer fileid) {
        Map json = new HashMap();
        if (library==0){
            json.put("resoult", true);
            //查询附件库内是否存在，存在获得id,不存在添加返回id
            Accessory accessory=accessoryRepository.findOneByLibraryAndFileId(library,fileid);
            if (accessory!=null)
                json.put("id",accessory.getId());
            else
                json.put("id",accessoryRepository.save(new Accessory(null,library,fileid,null,null)).getId());
            //根据id查询文件名字
            json.put("name",fileSampleRepository.findOneById(fileid).getName());
        }else{
            json.put("resoult", false);
        }
        return json;
    }
}
