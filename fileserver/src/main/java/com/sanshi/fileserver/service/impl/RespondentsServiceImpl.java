package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.*;
import com.sanshi.fileserver.repository.*;
import com.sanshi.fileserver.service.RespondentsService;
import com.sanshi.fileserver.vo.PageGet;
import com.sanshi.fileserver.vo.RespondentsMsg;
import com.sanshi.fileserver.vo.RespondentsPage;
import com.sanshi.fileserver.vo.SessionUser;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;

@Service("RespondentsServiceImpl")
public class RespondentsServiceImpl implements RespondentsService {
    private RespondentsRepository respondentsRepository;
    private HttpSession      session;
    private AssessRepository  assessRepository;
    private SubjectRepository subjectRepository;
    private AnswerRepository answerRepository;
    private AssessUserRepository assessUserRepository;
    private StudentRepository  studentRepository;
    private GroupRepository  groupRepository;
    private GradeRepository  gradeRepository;
    private CclassRepository cclassRepository;

    public RespondentsServiceImpl(RespondentsRepository respondentsRepository, HttpSession session, AssessRepository assessRepository, SubjectRepository subjectRepository, AnswerRepository answerRepository, AssessUserRepository assessUserRepository,StudentRepository  studentRepository, GroupRepository  groupRepository,GradeRepository  gradeRepository,CclassRepository cclassRepository) {
        this.respondentsRepository = respondentsRepository;
        this.session = session;
        this.assessRepository = assessRepository;
        this.subjectRepository = subjectRepository;
        this.answerRepository = answerRepository;
        this.assessUserRepository=assessUserRepository;
        this.studentRepository=studentRepository;
        this.groupRepository=groupRepository;
        this.gradeRepository=gradeRepository;
        this.cclassRepository=cclassRepository;
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

    @Override
    public Map selectRespondents(RespondentsPage respondentsPage) {
        Pageable  pageable;
        pageable = PageRequest.of(respondentsPage.getPageIndex(), respondentsPage.getPageNumber());
        Map json = new HashMap();
        switch (respondentsPage.getType()){
            case 1:     //查询出未参加考试的学生
                //获得应该参加学生的考核
      List<Integer>  StudentIdList=this.selectStudent(respondentsPage.getAssessId());
                //查询所有已参加的学生集合
      List<Integer>  list=respondentsRepository.findStuIdsAndAssessId(respondentsPage.getAssessId());
      List<Integer> allList=studentRepository.findAllByStuIdAndAssessIdMinus(StudentIdList,list);
                break;
            case 2:  //查询未提交的学生


                break;
            case 3:
                break;
            case 4:
                break;

        }
        return null ;
    }


    //获取这个考核的学生ID集合
    public List<Integer>  selectStudent(Integer id){
        //获取这个考核
        Assess  assess =assessRepository.findOneById(id);
        //获取这个考核的对象
        List<AssessUser>  assessUserList=assessUserRepository.findAllByAssessId(assess.getId());
        //返回数组
        List<Integer> list=new ArrayList<Integer>();

        for(AssessUser  assessUser:assessUserList){
            switch (assessUser.getTestObject()){
                case 1:   //学年级别
                    List<Integer> studentList=studentRepository.findIdsByStuGroupIn( groupRepository.findALLIdByCclassIdIn(cclassRepository.findIdsByGradeIdIn(gradeRepository.findIdsByYear(assessUser.getTestObjectId()))));
                    for(Integer  studentId:studentList){
                        list.add(studentId);
                    }
                    break;
                case 2:   //学院级别
          List<Integer> studentList1=studentRepository.findIdsByStuGroupIn(groupRepository.findALLIdByCclassIdIn(cclassRepository.findIdsByGradeId(assessUser.getTestObjectId())));
                    for (Integer stuId : studentList1){
                        list.add(stuId);
                    }
                    break;
                case 3:   //班级级别
                List<Integer>  stuGroupList=groupRepository.findALLIdByCclassId(assessUser.getTestObjectId());
                 List<Integer> studentList2=studentRepository.findIdsByStuGroupIn(stuGroupList);
            for (Integer stuId : studentList2){
                   list.add(stuId);
              }
                    break;
                case 4:   //小组级别
                List<Student> studentList3= studentRepository.findAllByStuGroup(assessUser.getTestObjectId());
                for(Student  student:studentList3){
                    list.add(student.getStuId());
                }
                    break;
                case 5:   //学生级别
            list.add(studentRepository.findOneByStuId(assessUser.getTestObjectId()).getStuId());
                    break;

            }

        }

return  list;
    }



}
