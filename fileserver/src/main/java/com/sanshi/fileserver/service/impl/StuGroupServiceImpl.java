package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.StuGroup;
import com.sanshi.fileserver.repository.GroupRepository;
import com.sanshi.fileserver.service.StuGroupService;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public StuGroup findOneById(Integer id) {
        return groupRepository.findOneById(id);
    }
}
