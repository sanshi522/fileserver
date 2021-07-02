package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.*;
import com.sanshi.fileserver.repository.*;
import com.sanshi.fileserver.service.RespondentsService;
import com.sanshi.fileserver.vo.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;

@Service("RespondentsServiceImpl")
public class RespondentsServiceImpl implements RespondentsService {
    private RespondentsRepository respondentsRepository;
    private HttpSession session;
    private AssessRepository assessRepository;
    private SubjectRepository subjectRepository;
    private AnswerRepository answerRepository;
    private AssessUserRepository assessUserRepository;
    private StudentRepository studentRepository;
    private GroupRepository groupRepository;
    private GradeRepository gradeRepository;
    private CclassRepository cclassRepository;

    public RespondentsServiceImpl(RespondentsRepository respondentsRepository, HttpSession session, AssessRepository assessRepository, SubjectRepository subjectRepository, AnswerRepository answerRepository, AssessUserRepository assessUserRepository, StudentRepository studentRepository, GroupRepository groupRepository, GradeRepository gradeRepository, CclassRepository cclassRepository) {
        this.respondentsRepository = respondentsRepository;
        this.session = session;
        this.assessRepository = assessRepository;
        this.subjectRepository = subjectRepository;
        this.answerRepository = answerRepository;
        this.assessUserRepository = assessUserRepository;
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
        this.gradeRepository = gradeRepository;
        this.cclassRepository = cclassRepository;
        ;
    }

    /**
     * 1.获取某考核下的所有答卷
     *
     * @param respondents
     * @return
     */
    @Override
    public List<Respondents> findAll(Respondents respondents) {
        if (respondents.getAssessId() != null) {
            if (respondents.getStuId() != null) {//指定考核id , 答卷人
                return respondentsRepository.findAllByAssessIdAndStuId(respondents.getAssessId(), respondents.getStuId());
            } else {
                if (respondents.getCorrect() != null) { //指定考核 批阅状态
                    return respondentsRepository.findAllByAssessIdAndCorrect(respondents.getAssessId(), respondents.getCorrect());
                } else {//指定考核id
                    return respondentsRepository.findAllByAssessId(respondents.getAssessId());
                }
            }
        } else {//不筛选考核id
            if (respondents.getStuId() != null) {//指定人所有考核
                if (respondents.getCorrect() != null) {//批阅状态
                    return respondentsRepository.findAllBystuIdAndCorrect(respondents.getStuId(), respondents.getCorrect());
                } else {
                    return respondentsRepository.findAllBystuId(respondents.getStuId());
                }
            } else {//不筛选答卷人
                if (respondents.getCorrect() != null) {//批阅状态
                    return respondentsRepository.findAllByCorrect(respondents.getCorrect());
                } else {
                    return respondentsRepository.findAll();
                }
            }
        }
    }

    @Override
    public Respondents save(Respondents respondents) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        respondents.setStuId(sessionUser.getStudent().getStuId());
        Respondents respondents1 = respondentsRepository.findByStuIdAndAssessId(respondents.getStuId(), respondents.getAssessId());
        if (respondents1 != null) {
            return respondents1;
        }
        return respondentsRepository.save(respondents);
    }

    @Override
    public void delete(Respondents respondents) {
        if (respondents.getId() != null)
            respondentsRepository.deleteById(respondents.getId());
        else {
            if (respondents.getAssessId() != null)
                respondentsRepository.deleteByAssessId(respondents.getAssessId());
        }

    }

    @Override
    public Respondents findById(Integer id) {
        return respondentsRepository.findOneById(id);
    }

    @Override
    public Respondents selectSubmit(Integer assessId) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        return respondentsRepository.findByStuIdAndAssessId(sessionUser.getStudent().getStuId(), assessId);
    }

    @Override
    public Map selectScore(PageGet pageGet) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        Pageable pageable;
        pageable = PageRequest.of(pageGet.getPageIndex(), pageGet.getPageNumber());
        Map json = new HashMap();
        //获取所有考核
        List<Integer> assessList = this.selectAssess(sessionUser.getStudent().getStuId());
        //获取已提交的考核
        List<Respondents> respondentsList = respondentsRepository.findAllByAssessIdInAndStuIdAndCorrect(assessList, sessionUser.getStudent().getStuId(), 2);
        List<Assess> assessList2 = assessRepository.findAllByIdInAndEndTimeLessThan(assessList, new Date());
        List<Integer> list = new ArrayList<Integer>();
        for (Respondents respondents : respondentsList) {
            list.add(respondents.getAssessId());
        }
        for (Assess assess : assessList2) {
            list.add(assess.getId());
        }
        json.put("page", assessRepository.findAllByIdInOrderByCreateTimeDesc(list, pageable));
        return json;
    }

    @Override
    public RespondentsMsg selectRespondentsMsg(Integer id) {
        Respondents respondents = respondentsRepository.findOneById(id);
        return new RespondentsMsg(assessRepository.findOneById(respondents.getAssessId()), subjectRepository.findOneById(assessRepository.findOneById(respondents.getAssessId()).getSubId()).getName(), answerRepository.selectScore(respondents.getId()), studentRepository.findOneByStuId(respondents.getStuId()));
    }

    @Override
    public Map selectRespondents(RespondentsPage respondentsPage) {
        Pageable pageable;
        pageable = PageRequest.of(respondentsPage.getPageIndex(), respondentsPage.getPageNumber());
        Map json = new HashMap();
        switch (respondentsPage.getType()) {
            case 5:     //查询出未参加考试的学生
                //获得应该参加学生的考核
                List<Integer> StudentIdList = this.selectStudent(respondentsPage.getAssessId());
                //查询所有已参加的学生集合
                List<Integer> list = respondentsRepository.findStuIdsAndAssessId(respondentsPage.getAssessId());
                // List<Integer> allList=studentRepository.findAllByStuIdAndAssessIdMinus(StudentIdList,list);
                break;
            case 2:  //查询未提交的试卷
                json.put("page", respondentsRepository.findAllByAssessIdAndSubmit(respondentsPage.getAssessId(), 1, pageable));
                break;
            case 3: //查询未批阅的试卷
                json.put("page", respondentsRepository.findAllByAssessIdAndSubmitAndCorrect(respondentsPage.getAssessId(), 2, 1, pageable));
                break;
            case 4: //查询已批阅的试卷
                json.put("page", respondentsRepository.findAllByAssessIdAndSubmitAndCorrect(respondentsPage.getAssessId(), 2, 2, pageable));
                break;
            case 1://查询所有的试卷
                json.put("page", respondentsRepository.findAllByAssessId(respondentsPage.getAssessId(), pageable));
                break;

        }
        return json;
    }


    /**
     * 获取这个考核的学生ID集合
     */
    @Override
    public List<Integer> selectStudent(Integer id) {
        //获取这个考核
        Assess assess = assessRepository.findOneById(id);
        //获取这个考核的对象
        List<AssessUser> assessUserList = assessUserRepository.findAllByAssessId(assess.getId());
        //返回数组
        List<Integer> list = new ArrayList<Integer>();

        for (AssessUser assessUser : assessUserList) {
            switch (assessUser.getTestObject()) {
                case 1:   //学年级别
                  List<Integer>  list1=    gradeRepository.findIdsByYear(assessUser.getTestObjectId());
                    List<Integer> list2= cclassRepository.findIdsByGradeIdIn(list1);
                    List<Integer> list3= groupRepository.findALLIdByCclassIdIn(list2);
                    List<Integer> studentList = studentRepository.findIdsByStuGroupIn(list3);
                    for (Integer studentId : studentList) {
                        list.add(studentId);
                    }
                    break;
                case 2:   //学院级别
                    List<Integer> studentList1 = studentRepository.findIdsByStuGroupIn(groupRepository.findALLIdByCclassIdIn(cclassRepository.findIdsByGradeId(assessUser.getTestObjectId())));
                    for (Integer stuId : studentList1) {
                        list.add(stuId);
                    }
                    break;
                case 3:   //班级级别
                    List<Integer> stuGroupList = groupRepository.findALLIdByCclassId(assessUser.getTestObjectId());
                    List<Integer> studentList2 = studentRepository.findIdsByStuGroupIn(stuGroupList);
                    for (Integer stuId : studentList2) {
                        list.add(stuId);
                    }
                    break;
                case 4:   //小组级别
                    List<Student> studentList3 = studentRepository.findAllByStuGroup(assessUser.getTestObjectId());
                    for (Student student : studentList3) {
                        list.add(student.getStuId());
                    }
                    break;
                case 5:   //学生级别
                    list.add(studentRepository.findOneByStuId(assessUser.getTestObjectId()).getStuId());
                    break;

            }

        }

        return list;
    }


    /**
     * 通过学生ID获取所有考核
     */
    public List<Integer> selectAssess(Integer stuId) {
        //考核ID集合
        List<Integer> assessList = new ArrayList<Integer>();
        //学生对象
        Student student = studentRepository.findOneByStuId(stuId);
        //考试级别为学生的考核
        List<Integer> list1 = assessUserRepository.findAssessIdAllByTestObjectAndTestObjectId(5, student.getStuId());
        AssessUerGVo.listAdd(assessList, list1);
        //小组对象
        StuGroup stuGroup = groupRepository.findOneById(student.getStuGroup());
        List<Integer> list2 = assessUserRepository.findAssessIdAllByTestObjectAndTestObjectId(4, stuGroup.getId());
        AssessUerGVo.listAdd(assessList, list2);
        //班级对象
        Cclass cclass = cclassRepository.findOneById(stuGroup.getCclassId());
        List<Integer> list3 = assessUserRepository.findAssessIdAllByTestObjectAndTestObjectId(3, cclass.getId());
        AssessUerGVo.listAdd(assessList, list3);
        //学校对象
        Grade grade = gradeRepository.findOneById(cclass.getGradeId());
        List<Integer> list4 = assessUserRepository.findAssessIdAllByTestObjectAndTestObjectId(2, grade.getId());
        AssessUerGVo.listAdd(assessList, list4);
        //学年对象
        List<Integer> list5 = assessUserRepository.findAssessIdAllByTestObjectAndTestObjectId(1, grade.getYear());
        AssessUerGVo.listAdd(assessList, list5);
        return assessList;

    }


    @Override
    public RespondentsMsg selectRespondentsMsg2(Integer id) {
        Assess assess = assessRepository.findOneById(id);
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        Respondents respondents = respondentsRepository.findOneByAssessIdAndStuId(assess.getId(), sessionUser.getStudent().getStuId());
        if (respondents == null) {
            return new RespondentsMsg(assess, subjectRepository.findOneById(assess.getSubId()).getName(), 0d, null, 0);
        }
        return new RespondentsMsg(assess, subjectRepository.findOneById(assess.getSubId()).getName(), answerRepository.selectScore(respondents.getId()), null, 1);
    }


    @Override
    public int Read(Integer respondentsId) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        Respondents respondent = respondentsRepository.findOneById(respondentsId);
        respondent.setCorrectId(sessionUser.getTeacher().getTeaId());
        respondent.setCorrect(2);
        respondentsRepository.save(respondent);
        return 1;
    }


    @Override
    public Respondents selectAssessIdAndStuId(Integer assessId, Integer stuId) {
        return respondentsRepository.findOneByAssessIdAndStuId(assessId,stuId);
    }

    @Override
    public List<Respondents> findByStudentId(Integer id) {
        return respondentsRepository.findAllBystuId(id);
    }

}
