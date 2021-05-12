package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.StuGroup;
import com.sanshi.fileserver.repository.GroupRepository;
import com.sanshi.fileserver.service.StuGroupService;
import com.sanshi.fileserver.vo.PageGet;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("StuGroupServiceImpl")
public class StuGroupServiceImpl implements StuGroupService {
    private GroupRepository groupRepository;

    public StuGroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public List<StuGroup> findGroupsByCclassId(Integer id) {
        return groupRepository.findAllByCclassId(id);
    }

    @Override
    public Map findGroupByCclassId(PageGet pageGet) {
        Pageable pageable;
        pageable = PageRequest.of(pageGet.getPageIndex(), pageGet.getPageNumber());
        Map json = new HashMap();
        json.put("resoult", true);
        if (pageGet.getLikeName().isEmpty())
            json.put("page", groupRepository.findAllByCclassId(pageGet.getIssistId(), pageable));
        else
            json.put("page", groupRepository.findAllByCclassIdAndNameLike(pageGet.getIssistId(), pageGet.getLikeName(), pageable));
        return json;
    }

    @Override
    public StuGroup findOneById(Integer id) {
        return groupRepository.findOneById(id);
    }

    @Override
    public StuGroup save(StuGroup student) {
        return groupRepository.save(student);
    }

    @Override
    public Integer deleteById(Integer val) {
        groupRepository.deleteById(val);
        return 1;
    }

    @Override
    public StuGroup findOneByName(Integer classId, String groupName) {
        return groupRepository.findOneByCclassIdAndName(classId, groupName);
    }
}
