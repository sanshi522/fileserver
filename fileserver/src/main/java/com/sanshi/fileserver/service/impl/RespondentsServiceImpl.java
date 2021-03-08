package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.Respondents;
import com.sanshi.fileserver.repository.RespondentsRepository;
import com.sanshi.fileserver.service.RespondentsService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("RespondentsServiceImpl")
public class RespondentsServiceImpl implements RespondentsService {
    private RespondentsRepository respondentsRepository;

    public RespondentsServiceImpl(RespondentsRepository respondentsRepository) {
        this.respondentsRepository = respondentsRepository;
    }

    /**
     * 1.获取某考核下的所有答卷
     * @param respondents
     * @return
     */
    @Override
    public List<Respondents> findAll(Respondents respondents) {
        if (respondents.getAssessId()!=null){
            if(respondents.getStuId()!=null){//指定考核id , 答卷人
                return respondentsRepository.findAllByAssessIdAndStuId(respondents.getAssessId(),respondents.getStuId());
            }else{
                if (respondents.getCorrect()!=null){ //指定考核 批阅状态
                    return respondentsRepository.findAllByAssessIdAndCorrect(respondents.getAssessId(),respondents.getCorrect());
                }else{//指定考核id
                    return respondentsRepository.findAllByAssessId(respondents.getAssessId());
                }
            }
        }else{//不筛选考核id
            if(respondents.getStuId()!=null){//指定人所有考核
                if (respondents.getCorrect()!=null){//批阅状态
                    return respondentsRepository.findAllBystuIdAndCorrect(respondents.getStuId(),respondents.getCorrect());
                }else{
                    return respondentsRepository.findAllBystuId(respondents.getStuId());
                }
            }else{//不筛选答卷人
                if (respondents.getCorrect()!=null){//批阅状态
                    return respondentsRepository.findAllByCorrect(respondents.getCorrect());
                }else{
                    return respondentsRepository.findAll();
                }
            }
        }
    }

    @Override
    public Respondents save(Respondents respondents) {
        return respondentsRepository.save(respondents);
    }

    @Override
    public void delete(Respondents respondents) {
        if(respondents.getId()!=null)
            respondentsRepository.deleteById(respondents.getId());
        else{
            if(respondents.getAssessId()!=null)
                respondentsRepository.deleteByAssessId(respondents.getAssessId());
        }

    }
}
