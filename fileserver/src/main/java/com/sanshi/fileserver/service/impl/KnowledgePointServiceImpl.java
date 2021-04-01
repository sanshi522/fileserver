package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.KnowledgePoint;
import com.sanshi.fileserver.bean.Subject;
import com.sanshi.fileserver.repository.KnowledgePointRepository;
import com.sanshi.fileserver.service.KnowledgePointService;
import com.sanshi.fileserver.vo.PageGet;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Map getAll(PageGet pageGet) {
        Pageable pageable;
        pageable = PageRequest.of(pageGet.getPageIndex() , pageGet.getPageNumber());
        Map json = new HashMap();
        json.put("resoult", true);
        if (pageGet.getLikeName().isEmpty()) pageGet.setLikeName("");
        if (pageGet.getIssistId()==0)
            json.put("page", knowledgePointRepository.findAllByNameLike("%"+pageGet.getLikeName()+"%",pageable));
        else
            json.put("page", knowledgePointRepository.findAllBySubIdAndNameLike(pageGet.getIssistId(),"%"+pageGet.getLikeName()+"%",pageable));
        return json;
    }

    @RequestMapping(path = "/deleteById")
    @ResponseBody
    public Integer deleteById(Integer val, HttpServletRequest request){
         knowledgePointRepository.deleteById(val);
        return 1;
    }
}
