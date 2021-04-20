package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.Respondents;
import com.sanshi.fileserver.repository.AnswerRepository;
import com.sanshi.fileserver.repository.AssessRepository;
import com.sanshi.fileserver.repository.RespondentsRepository;
import com.sanshi.fileserver.repository.SubjectRepository;
import com.sanshi.fileserver.service.RespondentsService;
import com.sanshi.fileserver.vo.PageGet;
import com.sanshi.fileserver.vo.RespondentsMsg;
import com.sanshi.fileserver.vo.SessionUser;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("RespondentsServiceImpl")
public class RespondentsServiceImpl implements RespondentsService {
    private RespondentsRepository respondentsRepository;
    private HttpSession      session;
    private AssessRepository  assessRepository;
    private SubjectRepository subjectRepository;
    private AnswerRepository answerRepository;

    public RespondentsServiceImpl(RespondentsRepository respondentsRepository, HttpSession session, AssessRepository assessRepository, SubjectRepository subjectRepository, AnswerRepository answerRepository) {
        this.respondentsRepository = respondentsRepository;
        this.session = session;
        this.assessRepository = assessRepository;
        this.subjectRepository = subjectRepository;
        this.answerRepository = answerRepository;
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
        SessionUser  sessionUser=(SessionUser) session.getAttribute("user");
        respondents.setStuId(sessionUser.getStudent().getStuId());
      Respondents  respondents1=respondentsRepository.findByStuIdAndAssessId(respondents.getStuId(),respondents.getAssessId());
        if(respondents1!=null){
            return    respondents1;
        }
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

    @Override
    public Respondents findById(Integer id) {
        return respondentsRepository.findOneById(id);
    }

    @Override
    public Respondents selectSubmit(Integer assessId) {
        SessionUser  sessionUser=(SessionUser) session.getAttribute("user");
        return respondentsRepository.findByStuIdAndAssessId(sessionUser.getStudent().getStuId(),assessId);
    }

    @Override
    public Map selectScore(PageGet  pageGet) {
        SessionUser  sessionUser=(SessionUser) session.getAttribute("user");
        Pageable pageable;
        pageable = PageRequest.of(pageGet.getPageIndex(), pageGet.getPageNumber());
        Map json = new HashMap();
        json.put("page",respondentsRepository.findAllByStuIdAndCorrectOrderByCreateTimeDesc(sessionUser.getStudent().getStuId(),2,pageable));
        return  json ;
    }

    @Override
    public RespondentsMsg selectRespondentsMsg(Integer id) {
       Respondents  respondents=   respondentsRepository.findOneById(id);
        return new RespondentsMsg(assessRepository.findOneById(respondents.getAssessId()),subjectRepository.findOneById(assessRepository.findOneById(respondents.getAssessId()).getSubId()).getName(),answerRepository.selectScore(respondents.getId()));
    }


}
