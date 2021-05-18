package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.*;
import com.sanshi.fileserver.repository.*;
import com.sanshi.fileserver.service.AssessService;
import com.sanshi.fileserver.service.RespondentsService;
import com.sanshi.fileserver.vo.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private RespondentsRepository respondentsRepository;
    private AssessUserRepository assessUserRepository;
    private ChoiceRepository choiceRepository;


    public AssessServiceImpl(StudentRepository studentRepository, TeacherRepository teacherRepository, AssessRepository assessRepository, GroupRepository groupRepository, CclassRepository cclassRepository, GradeRepository gradeRepository, TestPaperRepository testPaperRepository, SubjectRepository subjectRepository, TestPaperBindChoiceRepository testPaperBindChoiceRepository, RespondentsService respondentsService, HttpSession session, RespondentsRepository respondentsRepository, AssessUserRepository assessUserRepository, ChoiceRepository choiceRepository) {
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
        this.assessUserRepository = assessUserRepository;
        this.choiceRepository = choiceRepository;
    }

    @Override
    public Map findAll(ScreenAssess screenAssess) {
        Map json = new HashMap();
        Sort sort = null;
        sort =  Sort.by(Sort.Direction.DESC,"createTime");
        Pageable pageable;
        pageable = PageRequest.of(screenAssess.getPageIndex(), screenAssess.getPageNumber(),sort);
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        if (!screenAssess.getName().isEmpty()) {
            screenAssess.setName("%" + screenAssess.getName() + "%");
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

        return json;
    }


    @Override
    public List<Assess> findAllInvalid(Assess assess, Integer logintype, Integer userId) {
        Date date = new Date();
        List<Assess> list = new ArrayList<Assess>();
        if (logintype == 1) {//老师
            if (assess.getSubId() != null) {//学科不为空
                return assessRepository.findAllBySubIdAndIssueIdAndStartTimeGreaterThanAndEndTimeLessThan(assess.getSubId(), userId, date, date);
            }
            return assessRepository.findAllByIssueIdAndStartTimeGreaterThanAndEndTimeLessThan(userId, date, date);
        } else {
            if (assess.getSubId() != null) {//学科不为空
                return assessRepository.findAllBySubIdAndStartTimeGreaterThanAndEndTimeLessThan(assess.getSubId(), date, date);
            }
            return assessRepository.findAllByStartTimeGreaterThanAndEndTimeLessThan(date, date);
        }
    }

    @Override
    @Transactional
    public int save(AssessUserVo assessUserVo) {
        if(assessUserVo.getAssess().getId()!=null){
       Assess  assess=     assessRepository.findOneById(assessUserVo.getAssess().getId());
            assessUserVo.getAssess().setCreateTime(assess.getCreateTime());
            assessUserVo.getAssess().setIssueId(assess.getIssueId());
        }else {
            SessionUser sessionUser = (SessionUser) session.getAttribute("user");
            assessUserVo.getAssess().setIssueId(sessionUser.getTeacher().getTeaId());
        }
        //添加试卷
        Assess asses = assessRepository.save(assessUserVo.getAssess());
        //删除该试卷所有绑定的对象
        assessUserRepository.deleteByAssessId(asses.getId());
        //添加绑定对象
        for (int i = 0; i < assessUserVo.getListAssessUser().size(); i++) {
            assessUserVo.getListAssessUser().get(i).setAssessId(asses.getId());
            assessUserRepository.save(assessUserVo.getListAssessUser().get(i));
        }
        return 1;
    }

    @Override
    @Transactional
    public Result delete(Integer id) {
        List<Respondents> list = respondentsRepository.findAllByAssessId(id);
        if (list.size() < 1) {
            assessRepository.deleteById(id);
            assessUserRepository.deleteByAssessId(id);
            return new Result(false, "删除成功");
        }
        return new Result(true, "该考核已有学生参加考试您不能删除");
    }

    @Override
    public AssessMsg findMsg(Integer id) {
        Assess assess = assessRepository.findOneById(id);
        String name = teacherRepository.findOneByTeaId(assess.getIssueId()).getTeaName();
        Double scoreSum = testPaperBindChoiceRepository.findScoreSum(assess.getTestPaperId());

        if (scoreSum == null) scoreSum = 0.0;
        return new AssessMsg(name, testPaperBindChoiceRepository.findAllByTestPaperIdOrderByIndexNumAsc(assess.getTestPaperId()).size(), scoreSum, subjectRepository.findOneById(assess.getSubId()).getName(), respondentsRepository.findCountnotred(assess.getId()), respondentsRepository.findCountred(assess.getId()));
    }

    @Override
    public AssessMsg findMsg2(Integer id) {
        Assess assess = assessRepository.findOneById(id);
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        String name = teacherRepository.findOneByTeaId(assess.getIssueId()).getTeaName();
        Double scoreSum = testPaperBindChoiceRepository.findScoreSum(assess.getTestPaperId());
        if (scoreSum == null) scoreSum = 0.0;
        int submit = 0;
        Respondents respondents = respondentsRepository.findOneByAssessIdAndStuId(id, sessionUser.getStudent().getStuId());
        if (respondents != null) {
            submit = respondents.getSubmit();
        }
        return new AssessMsg(name, testPaperBindChoiceRepository.findAllByTestPaperIdOrderByIndexNumAsc(assess.getTestPaperId()).size(), scoreSum, subjectRepository.findOneById(assess.getSubId()).getName(), respondentsRepository.findCountnotred(assess.getId()), respondentsRepository.findCountred(assess.getId()), submit);
    }

    @Override
    public String fullname(Integer testObject, Integer testObjectId) {
        String name = "";
        if (testObject == 1) {
            name = "" + testObjectId;

        } else if (testObject == 2) {
            name = gradeRepository.findOneById(testObjectId).getYear() + "-" + gradeRepository.findOneById(testObjectId).getName();

        } else if (testObject == 3) {
            name = gradeRepository.findOneById(cclassRepository.findOneById(testObjectId).getGradeId()).getYear() + "-" + gradeRepository.findOneById(cclassRepository.findOneById(testObjectId).getGradeId()).getName() + "-" + cclassRepository.findOneById(testObjectId).getName();
        } else if (testObject == 4) {
            name = gradeRepository.findOneById(cclassRepository.findOneById(groupRepository.findOneById(testObjectId).getCclassId()).getGradeId()).getYear() + "-" + gradeRepository.findOneById(cclassRepository.findOneById(groupRepository.findOneById(testObjectId).getCclassId()).getGradeId()).getName() + "-" + cclassRepository.findOneById(groupRepository.findOneById(testObjectId).getCclassId()).getName() + "-" + groupRepository.findOneById(testObjectId).getName();
        } else if (testObject == 5) {
            name = gradeRepository.findOneById(cclassRepository.findOneById(groupRepository.findOneById(studentRepository.findOneByStuId(testObjectId).getStuGroup()).getCclassId()).getGradeId()).getYear() + "-" + gradeRepository.findOneById(cclassRepository.findOneById(groupRepository.findOneById(studentRepository.findOneByStuId(testObjectId).getStuGroup()).getCclassId()).getGradeId()).getName() + "-" + cclassRepository.findOneById(groupRepository.findOneById(studentRepository.findOneByStuId(testObjectId).getStuGroup()).getCclassId()).getName() + "-" + groupRepository.findOneById(studentRepository.findOneByStuId(testObjectId).getStuGroup()).getName() + "-" + studentRepository.findOneByStuId(testObjectId).getStuName();
        }
        return name;
    }

    @Override
    public AssessUerGVo findbyId(Integer assessId) {
        Assess assess = assessRepository.findOneById(assessId);
        List<AssessUser> list = assessUserRepository.findAllByAssessId(assess.getId());
        TestPaper testPaper = testPaperRepository.findOneById(assess.getTestPaperId());
        AssessUerGVo assessUerGVo = new AssessUerGVo();
        assessUerGVo.setAssess(assess);
        assessUerGVo.setTestPaper(testPaper);
        List<AssessUnameVo> assessUnameVoList = new ArrayList<AssessUnameVo>();
        for (AssessUser assessUser : list) {
            AssessUnameVo assessUnameVo = new AssessUnameVo();
            assessUnameVo.setAssessUser(assessUser);
            String name = "";
            switch (assessUser.getTestObject()) {
                case 1:
                    name = "" + assessUser.getTestObjectId();
                    break;
                case 2:
                    name = gradeRepository.findOneById(assessUser.getTestObjectId()).getName();
                    break;
                case 3:
                    name = cclassRepository.findOneById(assessUser.getTestObjectId()).getName();
                    break;
                case 4:
                    name = groupRepository.findOneById(assessUser.getTestObjectId()).getName();
                    break;
                case 5:
                    name = studentRepository.findOneByStuId(assessUser.getTestObjectId()).getStuName();
                    break;
            }
            assessUnameVo.setName(name);
            assessUnameVoList.add(assessUnameVo);
        }
        assessUerGVo.setListAssessUser(assessUnameVoList);

        return assessUerGVo;
    }

    @Override
    public Assess findByOneId(Integer assessId) {
        return assessRepository.findOneById(assessId);
    }


    //根据学生获取考核
    @Override
    public Map StudentAssess(StudentAssessVo studentAssessVo) {
        Pageable pageable;
        Date data = new Date();
        pageable = PageRequest.of(studentAssessVo.getPageIndex(), studentAssessVo.getPageNumber());
        List<Integer> list = new ArrayList<Integer>();
        Map json = new HashMap();
        List<List<AssessUser>> assesslist = new ArrayList<List<AssessUser>>();
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        //学生对象
        Student studente = studentRepository.findOneByStuId(sessionUser.getStudent().getStuId());
        //考试级别为学生的考核
        List<AssessUser> list1 = assessUserRepository.findAllByTestObjectAndTestObjectId(5, studente.getStuId());
        if (list1.size() > 0) {
            assesslist.add(list1);
        }
        //小组对象
        StuGroup stuGroup = groupRepository.findOneById(studente.getStuGroup());
        List<AssessUser> list2 = assessUserRepository.findAllByTestObjectAndTestObjectId(4, stuGroup.getId());
        if (list2.size() > 0) {
            assesslist.add(list2);
        }
        //班级对象
        Cclass cclass = cclassRepository.findOneById(stuGroup.getCclassId());
        List<AssessUser> list3 = assessUserRepository.findAllByTestObjectAndTestObjectId(3, cclass.getId());
        if (list3.size() > 0) {
            assesslist.add(list3);
        }
        //学校对象
        Grade grade = gradeRepository.findOneById(cclass.getGradeId());
        List<AssessUser> list4 = assessUserRepository.findAllByTestObjectAndTestObjectId(2, grade.getId());
        if (list4.size() > 0) {
            assesslist.add(list4);
        }
        //学年对象
        List<AssessUser> list5 = assessUserRepository.findAllByTestObjectAndTestObjectId(1, grade.getYear());
        if (list5.size() > 0) {
            assesslist.add(list5);
        }
        AssessUerGVo.listAll(list, assesslist);
        json.put("page", assessRepository.findAllByIdInAndStartTimeLessThanAndEndTimeGreaterThan(list, data, data, pageable));
        return json;
    }

    //学生进入考核查询试题信息
    @Override
    public StudentAssessVo studentChoice(Integer assessId) {

        //获取考核
        Assess assess = assessRepository.findOneById(assessId);
        //获取考卷
        TestPaper testPaper = testPaperRepository.findOneById(assess.getTestPaperId());
        //获取学科
        Subject subject = subjectRepository.findOneById(testPaper.getSubId());
        //获取试题与试卷绑定
        List<TestPaperBindChoice> testPaperBindChoiceList = testPaperBindChoiceRepository.findAllByTestPaperIdOrderByIndexNumAsc(testPaper.getId());
        List<ChoiceVo> choiceVoList = new ArrayList<ChoiceVo>();
        for (TestPaperBindChoice BindChoiceList : testPaperBindChoiceList) {
            ChoiceVo choiceVo = new ChoiceVo();

            choiceVo.setChoice(choiceRepository.findOneById(BindChoiceList.getChoiceId()));
            choiceVo.setScore(BindChoiceList.getScore());
            choiceVo.setIndexNum(BindChoiceList.getIndexNum());
            choiceVoList.add(choiceVo);
        }
        StudentAssessVo studentAssessVo = new StudentAssessVo(subject, assess, testPaper, choiceVoList);
        return studentAssessVo;
    }

}
