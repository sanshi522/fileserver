package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.Respondents;
import com.sanshi.fileserver.service.RespondentsService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("RespondentsServiceImpl")
public class RespondentsServiceImpl implements RespondentsService {
    @Override
    public List<Respondents> findAll(Respondents respondents) {
        return null;
    }

    @Override
    public Respondents save(Respondents respondents) {
        return null;
    }

    @Override
    public void delete(Respondents respondents) {

    }
}
