package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.Assess;
import com.sanshi.fileserver.bean.Respondents;
import com.sanshi.fileserver.bean.Student;
import com.sanshi.fileserver.repository.*;
import com.sanshi.fileserver.service.AssessService;
import com.sanshi.fileserver.service.RespondentsService;
import com.sanshi.fileserver.vo.AssessVo;
import com.sanshi.fileserver.vo.SessionUser;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service("AssessServiceImpl")
public class AssessServiceImpl implements AssessService {
    private StudentRepository studentRepository;
    private TeacherRepository teacherRepository;
    private AssessRepository assessRepository;
    private GroupRepository groupRepository;
    private CclassRepository cclassRepository;
    private GradeRepository gradeRepository;
    private TestPaperRepository testPaperRepository;
    private SubjectRepository subjectRepository;
    private TestPaperBindChoiceRepository testPaperBindChoiceRepository;

    private RespondentsService respondentsService;

    public AssessServiceImpl(StudentRepository studentRepository, TeacherRepository teacherRepository, AssessRepository assessRepository, GroupRepository groupRepository, CclassRepository cclassRepository, GradeRepository gradeRepository, TestPaperRepository testPaperRepository, RespondentsService respondentsService, SubjectRepository subjectRepository, TestPaperBindChoiceRepository testPaperBindChoiceRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.assessRepository = assessRepository;
        this.groupRepository = groupRepository;
        this.cclassRepository = cclassRepository;
        this.gradeRepository = gradeRepository;
        this.testPaperRepository = testPaperRepository;
        this.subjectRepository = subjectRepository;
        this.testPaperBindChoiceRepository = testPaperBindChoiceRepository;

        this.respondentsService = respondentsService;
    }

    @Override
    public List<AssessVo> findAll(Assess assess, Integer test, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("user") != null) {
            SessionUser sessionUser = new SessionUser();
            sessionUser = (SessionUser) session.getAttribute("user");
            Integer logintype = (sessionUser.getLogintype() == 0 ? 0 : sessionUser.getTeacher().getTeaIdentity());
            Integer userId = (sessionUser.getLogintype() == 0 ? sessionUser.getStudent().getStuId() : sessionUser.getTeacher().getTeaId());
            List<AssessVo> assessVoList =new ArrayList<AssessVo>();
            List<Assess> assessList=new ArrayList<Assess>();
            if (test==0){
                assessList.addAll(findAllValid(assess,logintype,userId));
            } else{
                assessList.addAll(findAllInvalid(assess,logintype,userId));
            }
            for (int i=0;i<assessList.size();i++){
                Integer assessSum=0;
                String testObject;
                String testObjectName;
                switch (assessList.get(i).getTestObject()){
                    case 0:
                        testObject="学院";
                        testObjectName=gradeRepository.findOneById(assessList.get(i).getTestObjectId()).getName();
                        if(logintype!=0)
                            assessSum= studentRepository.findAllByStuGroupIn(groupRepository.findALLIdByCclassIdIn(cclassRepository.findIdsByGradeId(assessList.get(i).getTestObjectId()))).size();
                        break;
                    case 1:
                        testObject="班级";
                        testObjectName=gradeRepository.findOneById(cclassRepository.findOneById(assessList.get(i).getTestObjectId()).getGradeId()).getName()
                                +"/"+cclassRepository.findOneById(assessList.get(i).getTestObjectId()).getName();
                        if(logintype!=0)
                            assessSum=studentRepository.findAllByStuGroupIn(groupRepository.findALLIdByCclassId(assessList.get(i).getTestObjectId())).size();
                        break;
                    case 2:
                        testObject="小组";
                        testObjectName=gradeRepository.findOneById(cclassRepository.findOneById(cclassRepository.findOneById(groupRepository.findOneById(assessList.get(i).getTestObjectId()).getCclassId()).getGradeId()).getGradeId()).getName()
                                +"/"+cclassRepository.findOneById(groupRepository.findOneById(assessList.get(i).getTestObjectId()).getCclassId()).getName()
                                +"/"+groupRepository.findOneById(assessList.get(i).getTestObjectId()).getName();
                        if(logintype!=0)
                            assessSum=studentRepository.findAllByStuGroup(assessList.get(i).getTestObjectId()).size();
                        break;
                    case 3:
                        testObject="学生";
                        testObjectName=gradeRepository.findOneById(cclassRepository.findOneById(cclassRepository.findOneById(groupRepository.findOneById(studentRepository.findOneByStuId(assessList.get(i).getTestObjectId()).getStuId()).getCclassId()).getGradeId()).getGradeId()).getName()
                                +"/"+cclassRepository.findOneById(groupRepository.findOneById(studentRepository.findOneByStuId(assessList.get(i).getTestObjectId()).getStuId()).getCclassId()).getName()
                                +"/"+groupRepository.findOneById(studentRepository.findOneByStuId(assessList.get(i).getTestObjectId()).getStuId()).getName()
                                +"/"+studentRepository.findOneByStuId(assessList.get(i).getTestObjectId()).getStuName();
                        if(logintype!=0)
                            assessSum=1;
                    default:
                        testObject="错误";
                        testObjectName="错误";
                        break;

                }//登陆身份、试卷名、进行中数量、已完成数量、考核总人数、科目名、总分、发布人、考核对象、对象名称、用时、提交状态、考核

                assessVoList.add(new AssessVo(
                        logintype,
                        testPaperRepository.findOneById(assessList.get(i).getTestPaperId()).getName(),
                        respondentsService.findAll(new Respondents(assessList.get(i).getId(),null,0)).size(),
                        respondentsService.findAll(new Respondents(assessList.get(i).getId(),null,1)).size(),
                        assessSum,
                        subjectRepository.findOneById(assessList.get(i).getSubId()).getName(),
                        testPaperBindChoiceRepository.findScoreSum(assessList.get(i).getTestPaperId()),
                        teacherRepository.findOneByTeaId(assessList.get(i).getIssueId()).getTeaName(),
                        testObject,testObjectName,
                        logintype==0?(respondentsService.findAll(new Respondents(assessList.get(i).getId(),userId,null)).size()==0?0:respondentsService.findAll(new Respondents(assessList.get(i).getId(),userId,null)).get(0).getMakeTime()):0,
                        logintype==0?(respondentsService.findAll(new Respondents(assessList.get(i).getId(),userId,null)).size()==0?0:respondentsService.findAll(new Respondents(assessList.get(i).getId(),userId,null)).get(0).getSubmit()):0,
                        assessList.get(i)
                ));
            }
            return assessVoList;
        }else{
            return null;
        }
    }

    /**
     *获取进行中考核
     * @param assess
     * @return
     */
    @Override
    public List<Assess> findAllValid(Assess assess, Integer logintype,Integer userId ) {
        Date date=new Date();
        List<Assess> list =new ArrayList<Assess>();
        if (logintype==0){//学生
            if (assess.getSubId()!=null){//学科不为空
                list.addAll(assessRepository.findAllBySubIdAndTestObjectAndTestObjectIdAndStartTimeLessThanAndEndTimeGreaterThan(assess.getSubId(),0,cclassRepository.getOne(groupRepository.getOne(studentRepository.findOneByStuId(userId).getStuGroup()).getCclassId()).getGradeId(),date,date));
                list.addAll(assessRepository.findAllBySubIdAndTestObjectAndTestObjectIdAndStartTimeLessThanAndEndTimeGreaterThan(assess.getSubId(),1,groupRepository.getOne(studentRepository.findOneByStuId(userId).getStuGroup()).getCclassId(),date,date));
                list.addAll(assessRepository.findAllBySubIdAndTestObjectAndTestObjectIdAndStartTimeLessThanAndEndTimeGreaterThan(assess.getSubId(),2,studentRepository.findOneByStuId(userId).getStuGroup(),date,date));
                list.addAll(assessRepository.findAllBySubIdAndTestObjectAndTestObjectIdAndStartTimeLessThanAndEndTimeGreaterThan(assess.getSubId(),3,userId,date,date));
                return list;
            }
            list.addAll(assessRepository.findAllByTestObjectAndTestObjectIdAndStartTimeLessThanAndEndTimeGreaterThan(0,cclassRepository.getOne(groupRepository.getOne(studentRepository.findOneByStuId(userId).getStuGroup()).getCclassId()).getGradeId(),date,date));
            list.addAll(assessRepository.findAllByTestObjectAndTestObjectIdAndStartTimeLessThanAndEndTimeGreaterThan(1,groupRepository.getOne(studentRepository.findOneByStuId(userId).getStuGroup()).getCclassId(),date,date));
            list.addAll(assessRepository.findAllByTestObjectAndTestObjectIdAndStartTimeLessThanAndEndTimeGreaterThan(2,studentRepository.findOneByStuId(userId).getStuGroup(),date,date));
            list.addAll(assessRepository.findAllByTestObjectAndTestObjectIdAndStartTimeLessThanAndEndTimeGreaterThan(3,userId,date,date));
            return list;
        }else if(logintype==1){//老师
            if (assess.getSubId()!=null){//学科不为空
                return assessRepository.findAllBySubIdAndIssueIdAndStartTimeLessThanAndEndTimeGreaterThan(assess.getSubId(),userId,date,date);
            }
            return assessRepository.findAllByIssueIdAndStartTimeLessThanAndEndTimeGreaterThan(userId,date,date);
        }else{
            if (assess.getSubId()!=null){//学科不为空
                return assessRepository.findAllBySubIdAndStartTimeLessThanAndEndTimeGreaterThan(assess.getSubId(),date,date);
            }
            return assessRepository.findAllByStartTimeLessThanAndEndTimeGreaterThan(date,date);
        }
    }

    @Override
    public List<Assess> findAllInvalid(Assess assess, Integer logintype,Integer userId) {
            Date date=new Date();
            List<Assess> list =new ArrayList<Assess>();
            if(logintype==1){//老师
               if (assess.getSubId()!=null){//学科不为空
                    return assessRepository.findAllBySubIdAndIssueIdAndStartTimeGreaterThanAndEndTimeLessThan(assess.getSubId(),userId,date,date);
                }
                return assessRepository.findAllByIssueIdAndStartTimeGreaterThanAndEndTimeLessThan(userId,date,date);
            }else{
               if (assess.getSubId()!=null){//学科不为空
                    return assessRepository.findAllBySubIdAndStartTimeGreaterThanAndEndTimeLessThan(assess.getSubId(),date,date);
                }
                return assessRepository.findAllByStartTimeGreaterThanAndEndTimeLessThan(date,date);
            }
    }


    @Override
    public Assess save(Assess assess) {
        return assessRepository.save(assess);
    }

    @Override
    public void delete(Assess assess) {
        assessRepository.deleteById(assess.getId());
    }
}
