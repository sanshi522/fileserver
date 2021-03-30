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
    public List<KnowledgePoint> findAllBySubIdAndNameLike(Integer subId,String name) {
        return knowledgePointRepository.findAllBySubIdAndNameLike(subId,"%"+name+"%");
    }

    @Override
    public KnowledgePoint findOneById(Integer id) {
        return findOneById(id);
    }
//
//    @Override
//    public List<KnowledgePoint> findAll(KnowledgePoint knowledgePoint) {
//        List<KnowledgePoint> list=new ArrayList<KnowledgePoint>();
//        if(knowledgePoint.getId()!=null){
//            list.add(knowledgePointRepository.findOneById(knowledgePoint.getId()));
//            return list;
//        }else if (!knowledgePoint.getName().isEmpty()){
//            list.addAll(knowledgePointRepository.findAllByNameLike(knowledgePoint.getName()));
//        }
//        return null;
//    }

    @Override
    public KnowledgePoint save(KnowledgePoint knowledgePoint) {
        return knowledgePointRepository.save(knowledgePoint);
    }

    @Override
    public void delete(KnowledgePoint knowledgePoint) {
        knowledgePointRepository.deleteById(knowledgePoint.getId());
    }
}
