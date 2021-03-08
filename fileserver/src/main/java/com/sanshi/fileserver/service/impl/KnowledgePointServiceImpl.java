package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.KnowledgePoint;
import com.sanshi.fileserver.repository.KnowledgePointRepository;
import com.sanshi.fileserver.service.KnowledgePointService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service("KnowledgePointServiceImpl")
public class KnowledgePointServiceImpl implements KnowledgePointService {
    private KnowledgePointRepository knowledgePointRepository;

    public KnowledgePointServiceImpl(KnowledgePointRepository knowledgePointRepository) {
        this.knowledgePointRepository = knowledgePointRepository;
    }

    @Override
    public List<KnowledgePoint> findAllByIds(List<Integer> ids) {
        return knowledgePointRepository.findAllByIdIn(ids);
    }

    @Override
    public List<KnowledgePoint> findAll(KnowledgePoint knowledgePoint) {
        if(knowledgePoint.getId()!=null){
            List<KnowledgePoint> list=new ArrayList<KnowledgePoint>();
            list.add(knowledgePointRepository.findOneById(knowledgePoint.getId()));
            return list;
        }
        return null;
    }

    @Override
    public KnowledgePoint save(KnowledgePoint knowledgePoint) {
        return knowledgePointRepository.save(knowledgePoint);
    }

    @Override
    public void delete(KnowledgePoint knowledgePoint) {
        knowledgePointRepository.deleteById(knowledgePoint.getId());
    }
}
