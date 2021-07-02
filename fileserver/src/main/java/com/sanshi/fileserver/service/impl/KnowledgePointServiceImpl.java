package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.Choice;
import com.sanshi.fileserver.bean.KnowledgePoint;
import com.sanshi.fileserver.bean.Subject;
import com.sanshi.fileserver.repository.ChoiceRepository;
import com.sanshi.fileserver.repository.KnowledgePointRepository;
import com.sanshi.fileserver.service.KnowledgePointService;
import com.sanshi.fileserver.vo.PageGet;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("KnowledgePointServiceImpl")
public class KnowledgePointServiceImpl implements KnowledgePointService {
    private KnowledgePointRepository knowledgePointRepository;
    private ChoiceRepository choiceRepository;

    public KnowledgePointServiceImpl(KnowledgePointRepository knowledgePointRepository, ChoiceRepository choiceRepository) {
        this.knowledgePointRepository = knowledgePointRepository;
        this.choiceRepository = choiceRepository;
    }

    @Override
    public List<KnowledgePoint> findAllByIds(List<Integer> ids) {
        return knowledgePointRepository.findAllByIdIn(ids);
    }

    @Override
    public List<KnowledgePoint> findAllBySubIdAndNameLike(Integer subId, String name) {
        return knowledgePointRepository.findAllBySubIdAndNameLike(subId, "%" + name + "%");
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
    @Transactional
    public void deleteById(Integer id) {

        String  Ability="%"+id+"%";
        List<Choice>  choiceList=choiceRepository.findAllByAbilityIdsLike(Ability);

        for (Choice choice:choiceList){
            String  str="";
            String []  a=choice.getAbilityIds().split(",");
            for (int i=0;i<a.length;i++){
                if (a[i]==""){
                    continue;
                }
                if ( Integer.parseInt(a[i])==id){
                    continue;
                }
                str+=a[i]+",";
            }
            String str2=str.substring(0,str.length()-1);
            choice.setAbilityIds(str2);
            choiceRepository.save(choice);
        }
         knowledgePointRepository.deleteById(id);
    }


    @Override
    public Map getAll(PageGet pageGet) {
        Pageable pageable;
        pageable = PageRequest.of(pageGet.getPageIndex(), pageGet.getPageNumber());
        Map json = new HashMap();
        json.put("resoult", true);
        if (pageGet.getLikeName().isEmpty()) pageGet.setLikeName("");
        if (pageGet.getIssistId() == 0)
            json.put("page", knowledgePointRepository.findAllByNameLike("%" + pageGet.getLikeName() + "%", pageable));
        else
            json.put("page", knowledgePointRepository.findAllBySubIdAndNameLike(pageGet.getIssistId(), "%" + pageGet.getLikeName() + "%", pageable));
        return json;
    }


    @Override
    public List<KnowledgePoint> selectBySubId(Integer id) {
        return knowledgePointRepository.findAllBySubId(id);
    }

    @Override
    public KnowledgePoint findOneBySubIdAndName(Integer subId, String name) {
        return knowledgePointRepository.findOneBySubIdAndName(subId, name);
    }

    @Override
    public List<KnowledgePoint> selectByNam(int[] arr) {
        List<KnowledgePoint> list =new ArrayList<>();
        for(int i=0;i<arr.length;i++){
            if(arr[i]==0){
               continue;
            }
         list.add( knowledgePointRepository.findOneById(arr[i]));
        }
        return list;
    }

}
