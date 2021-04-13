package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.Assess;
import com.sanshi.fileserver.bean.Respondents;
import com.sanshi.fileserver.bean.Student;
import com.sanshi.fileserver.repository.*;
import com.sanshi.fileserver.service.AssessService;
import com.sanshi.fileserver.service.RespondentsService;
import com.sanshi.fileserver.vo.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private HttpSession session;
    private RespondentsRepository  respondentsRepository;

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
        this.session = session;
        this.respondentsRepository = respondentsRepository;
    }

    @Override
    public Map findAll(ScreenAssess screenAssess) {
        Map json = new HashMap();
        Pageable pageable;
        pageable = PageRequest.of(screenAssess.getPageIndex(), screenAssess.getPageNumber());
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        if(!screenAssess.getName().isEmpty()){
            screenAssess.setName("%"+screenAssess.getName()+"%");
        }
        if (sessionUser.getTeacher().getTeaIdentity() == 1) {
            if (screenAssess.getSubId() != 0) {
                if (!screenAssess.getName().isEmpty()) {
                    json.put("page", assessRepository.findAllByNameLikeAndIssueIdAndSubId(screenAssess.getName(), sessionUser.getTeacher().getTeaId(), screenAssess.getSubId(), pageable));
                } else {
                    json.put("page", assessRepository.findAllByIssueIdAndSubId(sessionUser.getTeacher().getTeaId(), screenAssess.getSubId(), pageable));
                }
            } else {
                if (!screenAssess.getName().isEmpty()) {
                    json.put("page", assessRepository.findAllByNameLikeAndIssueId(screenAssess.getName(), sessionUser.getTeacher().getTeaId(), pageable));
                } else {
                    json.put("page", assessRepository.findAllByIssueId(sessionUser.getTeacher().getTeaId(), pageable));
                }
            }
        } else if (sessionUser.getTeacher().getTeaIdentity() == 2) {
            if (screenAssess.getSubId() != 0) {
                if (screenAssess.getIssueId() != 0) {
                    if (!screenAssess.getName().isEmpty()) {
                        json.put("page", assessRepository.findAllByNameLikeAndIssueIdAndSubId(screenAssess.getName(), screenAssess.getIssueId(), screenAssess.getSubId(), pageable));
                    } else {
                        json.put("page", assessRepository.findAllByIssueIdAndSubId(screenAssess.getIssueId(), screenAssess.getSubId(), pageable));
                    }
                } else {
                    if (!screenAssess.getName().isEmpty()) {
                        json.put("page", assessRepository.findAllByNameLikeAndSubId(screenAssess.getName(), screenAssess.getSubId(), pageable));
                    } else {
                        json.put("page", assessRepository.findAllBySubId(screenAssess.getSubId(), pageable));
                    }
                }
            } else {
                if (screenAssess.getIssueId() != 0) {
                    if (!screenAssess.getName().isEmpty()) {
                        json.put("page", assessRepository.findAllByNameLikeAndIssueId(screenAssess.getName(), screenAssess.getIssueId(), pageable));
                    } else {
                        json.put("page", assessRepository.findAllByIssueId(screenAssess.getIssueId(), pageable));
                    }
                } else {
                    if (!screenAssess.getName().isEmpty()) {
                        json.put("page", assessRepository.findAllByNameLike(screenAssess.getName(), pageable));
                    } else {
                        json.put("page", assessRepository.findAll(pageable));
                    }
                }
            }
        }

        return json ;
    }

//    /**
//     *获取进行中考核
//     * @param assess
//     * @return
//     */
//    @Override
//    public List<Assess> findAllValid(Assess assess, Integer logintype,Integer userId ) {
//        Date date=new Date();
//        List<Assess> list =new ArrayList<Assess>();
//        if (logintype==0){//学生
//            if (assess.getSubId()!=null){//学科不为空
//                list.addAll(assessRepository.findAllBySubIdAndTestObjectAndTestObjectIdAndStartTimeLessThanAndEndTimeGreaterThan(assess.getSubId(),0,cclassRepository.getOne(groupRepository.getOne(studentRepository.findOneByStuId(userId).getStuGroup()).getCclassId()).getGradeId(),date,date));
//                list.addAll(assessRepository.findAllBySubIdAndTestObjectAndTestObjectIdAndStartTimeLessThanAndEndTimeGreaterThan(assess.getSubId(),1,groupRepository.getOne(studentRepository.findOneByStuId(userId).getStuGroup()).getCclassId(),date,date));
//                list.addAll(assessRepository.findAllBySubIdAndTestObjectAndTestObjectIdAndStartTimeLessThanAndEndTimeGreaterThan(assess.getSubId(),2,studentRepository.findOneByStuId(userId).getStuGroup(),date,date));
//                list.addAll(assessRepository.findAllBySubIdAndTestObjectAndTestObjectIdAndStartTimeLessThanAndEndTimeGreaterThan(assess.getSubId(),3,userId,date,date));
//                return list;
//            }
//            list.addAll(assessRepository.findAllByTestObjectAndTestObjectIdAndStartTimeLessThanAndEndTimeGreaterThan(0,cclassRepository.getOne(groupRepository.getOne(studentRepository.findOneByStuId(userId).getStuGroup()).getCclassId()).getGradeId(),date,date));
//            list.addAll(assessRepository.findAllByTestObjectAndTestObjectIdAndStartTimeLessThanAndEndTimeGreaterThan(1,groupRepository.getOne(studentRepository.findOneByStuId(userId).getStuGroup()).getCclassId(),date,date));
//            list.addAll(assessRepository.findAllByTestObjectAndTestObjectIdAndStartTimeLessThanAndEndTimeGreaterThan(2,studentRepository.findOneByStuId(userId).getStuGroup(),date,date));
//            list.addAll(assessRepository.findAllByTestObjectAndTestObjectIdAndStartTimeLessThanAndEndTimeGreaterThan(3,userId,date,date));
//            return list;
//        }else if(logintype==1){//老师
//            if (assess.getSubId()!=null){//学科不为空
//                return assessRepository.findAllBySubIdAndIssueIdAndStartTimeLessThanAndEndTimeGreaterThan(assess.getSubId(),userId,date,date);
//            }
//            return assessRepository.findAllByIssueIdAndStartTimeLessThanAndEndTimeGreaterThan(userId,date,date);
//        }else{
//            if (assess.getSubId()!=null){//学科不为空
//                return assessRepository.findAllBySubIdAndStartTimeLessThanAndEndTimeGreaterThan(assess.getSubId(),date,date);
//            }
//            return assessRepository.findAllByStartTimeLessThanAndEndTimeGreaterThan(date,date);
//        }
//    }

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

    @Override
    public AssessMsg findMsg(Integer id) {
        Assess assess = assessRepository.findOneById(id);
        String name = teacherRepository.findOneByTeaId(assess.getIssueId()).getTeaName();
        Double scoreSum = testPaperBindChoiceRepository.findScoreSum(assess.getTestPaperId());
        if (scoreSum == null) scoreSum = 0.0;
        return new AssessMsg(name, testPaperBindChoiceRepository.findAllByTestPaperId(assess.getTestPaperId()).size(), scoreSum, subjectRepository.findOneById(assess.getSubId()).getName(),respondentsRepository.findCountnotred(assess.getId()),respondentsRepository.findCountred(assess.getId()));
    }
}
