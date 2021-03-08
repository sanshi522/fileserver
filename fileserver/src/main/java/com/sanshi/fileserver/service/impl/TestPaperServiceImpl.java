package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.*;
import com.sanshi.fileserver.repository.*;
import com.sanshi.fileserver.service.TestPaperService;
import com.sanshi.fileserver.vo.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Service("TestPaperServiceImpl")
public class TestPaperServiceImpl implements TestPaperService {
    private TestPaperRepository testPaperRepository;
    private AssessRepository assessRepository;
    private RespondentsRepository respondentsRepository;
    private TestPaperBindChoiceRepository testPaperBindChoiceRepository;
    private AnswerRepository answerRepository;
    private ChoiceRepository choiceRepository;

    public TestPaperServiceImpl(TestPaperRepository testPaperRepository, AssessRepository assessRepository, RespondentsRepository respondentsRepository, TestPaperBindChoiceRepository testPaperBindChoiceRepository, AnswerRepository answerRepository, ChoiceRepository choiceRepository) {
        this.testPaperRepository = testPaperRepository;
        this.assessRepository = assessRepository;
        this.respondentsRepository = respondentsRepository;
        this.testPaperBindChoiceRepository = testPaperBindChoiceRepository;
        this.answerRepository = answerRepository;
        this.choiceRepository = choiceRepository;
    }

    @Override
    public TestPaperVo Exam(Integer assessId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("user") != null) {
            SessionUser sessionUser = new SessionUser();
            sessionUser = (SessionUser) session.getAttribute("user");
            Integer logintype = (sessionUser.getLogintype() == 0 ? 0 : sessionUser.getTeacher().getTeaIdentity());
            Integer userId = (sessionUser.getLogintype() == 0 ? sessionUser.getStudent().getStuId() : sessionUser.getTeacher().getTeaId());
            if (logintype==0){
                TestPaperVo testPaperVo=new TestPaperVo();
                testPaperVo.setAssess(assessRepository.findOneById(assessId));//考核信息
                //答卷信息
                testPaperVo.setRespondents(respondentsRepository.findAllByAssessIdAndStuId(assessId,userId).size()==0?respondentsRepository.save(new Respondents(null,assessId,userId,0,1,0,null,null,null)):respondentsRepository.findAllByAssessIdAndStuId(assessId,userId).get(0));
                testPaperVo.setTestPaper(testPaperRepository.findOneById(testPaperVo.getAssess().getTestPaperId()));//获取试卷信息
                List<TestPaperBindChoice> testPaperBindChoicesList=testPaperBindChoiceRepository.findAllByTestPaperId(testPaperVo.getAssess().getTestPaperId());//获取试题集合
                List<MakeChoice> makeChoicesList = new ArrayList<MakeChoice>();
                for (int i=0;i<testPaperBindChoicesList.size();i++){
                    makeChoicesList.add(new MakeChoice(testPaperBindChoicesList.get(i).getIndexNum(),testPaperBindChoicesList.get(i).getScore(), choiceRepository.findOneById(testPaperBindChoicesList.get(i).getChoiceId()), answerRepository.findOneByChoiceIdAndRespondentsId(testPaperBindChoicesList.get(i).getChoiceId(),testPaperVo.getRespondents().getId())==null?answerRepository.save(new Answer(null,testPaperBindChoicesList.get(i).getChoiceId(),testPaperVo.getRespondents().getId(),null,null,0.0,0,null,null,null)):answerRepository.findOneByChoiceIdAndRespondentsId(testPaperBindChoicesList.get(i).getChoiceId(),testPaperVo.getRespondents().getId())));
                }
                Collections.sort(makeChoicesList);
                testPaperVo.setMakeChoiceList(makeChoicesList);
                return testPaperVo;
            }
            return  null;
        }
        return null;
    }

    @Override
    public ReadTestPaper read(Integer testPaperId) {
        ReadTestPaper readTestPaper= new ReadTestPaper();
        readTestPaper.setTestPaper(testPaperRepository.findOneById(testPaperId));
        List<ChoiceDetails> choices=new ArrayList<ChoiceDetails>();
        List<TestPaperBindChoice> testPaperBindChoicesList=testPaperBindChoiceRepository.findAllByTestPaperId(testPaperId);//获取试题集合
        for (int i=0;i<testPaperBindChoicesList.size();i++){
            choices.add(new ChoiceDetails(testPaperBindChoicesList.get(i).getIndexNum(),
                    testPaperBindChoicesList.get(i).getScore(),choiceRepository.findOneById(testPaperBindChoicesList.get(i).getChoiceId())));
        }
        Collections.sort(choices);
        readTestPaper.setChoices(choices);
        return readTestPaper;
    }

    @Override
    public TestPaper findOneById(Integer id) {
        return testPaperRepository.findOneById(id);
    }

    @Override
    public List<TestPaper> findAll(TestPaper testPaper) {
        if(testPaper.getSubId()!=0){//科目
            if (testPaper.getName().isEmpty()){
               return testPaperRepository.findAllBySubId(testPaper.getSubId());
            }else {
                return testPaperRepository.findAllBySubIdAndNameLike(testPaper.getSubId(),"%"+testPaper.getName()+"%");
            }
        }else{
            if (testPaper.getName().isEmpty()){
                return testPaperRepository.findAll();
            }else {
                return testPaperRepository.findAllByNameLike("%"+testPaper.getName()+"%");
            }
        }
    }

    @Override
    public TestPaper save(TestPaper testPaper) {
        return testPaperRepository.save(testPaper);
    }

    @Override
    public void deleteById(TestPaper testPaper) {
        testPaperRepository.deleteById(testPaper.getId());
    }
}
