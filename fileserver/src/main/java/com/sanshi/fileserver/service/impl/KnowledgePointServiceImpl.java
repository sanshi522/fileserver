package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.KnowledgePoint;
import com.sanshi.fileserver.service.KnowledgePointService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("KnowledgePointServiceImpl")
public class KnowledgePointServiceImpl implements KnowledgePointService {
    @Override
    public List<KnowledgePoint> findAllByIds(List<Integer> ids) {
        return null;
    }

    @Override
    public List<KnowledgePoint> findAll(KnowledgePoint knowledgePoint) {
        return null;
    }

    @Override
    public KnowledgePoint save(KnowledgePoint knowledgePoint) {
        return null;
    }

    @Override
    public void delete(KnowledgePoint knowledgePoint) {

    }
}
