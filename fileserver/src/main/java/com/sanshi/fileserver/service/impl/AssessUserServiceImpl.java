package com.sanshi.fileserver.service.impl;


import com.sanshi.fileserver.bean.AssessUser;
import com.sanshi.fileserver.repository.AssessUserRepository;
import com.sanshi.fileserver.service.AssessUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("AssessUserService")
public class AssessUserServiceImpl implements AssessUserService {

    private AssessUserRepository assessUserRepository;

    public AssessUserServiceImpl(AssessUserRepository assessUserRepository) {
        this.assessUserRepository = assessUserRepository;
    }

    @Override
    public int save(List<AssessUser> list) {
        for (int i = 0; i < list.size(); i++) {
            assessUserRepository.save(list.get(i));
        }
        return 1;
    }
}
