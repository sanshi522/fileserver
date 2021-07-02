package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.*;
import com.sanshi.fileserver.repository.*;
import com.sanshi.fileserver.service.TestPaperService;
import com.sanshi.fileserver.vo.PageGet;
import com.sanshi.fileserver.vo.SessionUser;
import com.sanshi.fileserver.service.TeacherService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("TeacherServiceImpl")
public class TeacherServiceImpl implements TeacherService {
    private TeacherRepository teacherRepository;
    private TeacherBindCclassRepository teacherBindCclassRepository;
    private TestPaperService testPaperService;
    private AssessRepository assessRepository;
    private RespondentsRepository respondentsRepository;
    private AnswerRepository answerRepository;
    private TestPaperRepository testPaperRepository;
    private AssessUserRepository assessUserRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository, TeacherBindCclassRepository teacherBindCclassRepository, TestPaperService testPaperService, AssessRepository assessRepository, RespondentsRepository respondentsRepository, AnswerRepository answerRepository, TestPaperRepository testPaperRepository, AssessUserRepository assessUserRepository) {
        this.teacherRepository = teacherRepository;
        this.teacherBindCclassRepository = teacherBindCclassRepository;
        this.testPaperService = testPaperService;
        this.assessRepository = assessRepository;
        this.respondentsRepository = respondentsRepository;
        this.answerRepository = answerRepository;
        this.testPaperRepository = testPaperRepository;
        this.assessUserRepository = assessUserRepository;
    }

    @Override
    public List<Teacher> findAllNoInClass(Integer clasId) {
        List<Integer> ids = teacherBindCclassRepository.findIdsByTeaCclasesId(clasId);
        if (ids.size() == 0) ;
        ids.add(0);
        return teacherRepository.findAllByTeaIdNotIn(ids);
    }


    @Override
    public Teacher findOneByTeaId(Integer id) {
        return teacherRepository.findOneByTeaId(id);
    }


    @Override
    public Integer Login(String name, String pass, Integer identity, HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<Teacher> techers = teacherRepository.findByTeaName(name);
        if (techers == null || techers.size() == 0) {
            return -1;
        } else {
            Teacher teacher = techers.get(0);
            if (teacher.getTeaPass().equals(pass)) {
                SessionUser sessionUser = new SessionUser();
                sessionUser.setLogin(true);
                sessionUser.setLogintype(identity);
                sessionUser.setTeacher(teacher);
                session.setAttribute("user", sessionUser);
                return 1;
            } else {
                return 0;
            }
        }
    }

    @Override
    public List<Teacher> findTeacherByClassId(Integer classId) {
        return teacherRepository.findAllByTeaIdIn(teacherBindCclassRepository.findIdsByTeaCclasesId(classId));
    }

    @Override
    public List<Teacher> findAdmin() {
        List<Integer> idents = new ArrayList<Integer>();
        idents.add(2);
        idents.add(3);
        return teacherRepository.findAllByTeaIdentityIn(idents);
    }

    @Override
    public Map finTeachers(PageGet pageGet, HttpServletRequest request) {
        Map json = new HashMap();
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("user") != null) {
            SessionUser sessionUser = new SessionUser();
            sessionUser = (SessionUser) session.getAttribute("user");
            Integer ident = (sessionUser.getLogintype() == 0 ? 0 : sessionUser.getTeacher().getTeaIdentity());
            Integer id = (sessionUser.getLogintype() == 0 ? sessionUser.getStudent().getStuId() : sessionUser.getTeacher().getTeaId());
            json.put("resoult", true);
            Pageable pageable;
            pageable = PageRequest.of(pageGet.getPageIndex(), pageGet.getPageNumber());
            if (ident == 3) {
                if (pageGet.getIssistId() == 2) {
                    List<Integer> list = new ArrayList<Integer>();
                    list.add(1);
                    list.add(2);
                    if (pageGet.getLikeName().isEmpty())
                        json.put("page", teacherRepository.findAllByTeaIdentityIn(list, pageable));
                    else
                        json.put("page", teacherRepository.findAllByTeaIdentityInAndTeaNameLike(list, pageGet.getLikeName(), pageable));
                } else {
                    if (pageGet.getLikeName().isEmpty())
                        json.put("page", teacherRepository.findAll(pageable));
                    else
                        json.put("page", teacherRepository.findAllByTeaNameLike(pageGet.getLikeName(), pageable));
                }
            } else {
                if (pageGet.getLikeName().isEmpty())
                    json.put("page", teacherRepository.findAll(pageable));
                else
                    json.put("page", teacherRepository.findAllByTeaNameLike(pageGet.getLikeName(), pageable));
            }
            return json;
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public void deleteByTeaId(Integer id) {
        teacherBindCclassRepository.deleteByTeeaId(id);
        List<TestPaper> testPaperList = testPaperService.findAllByTeacherId(id);
        for (TestPaper testPaper : testPaperList) {
            List<Assess> assessList = assessRepository.findAllByTestPaperId(testPaper.getId());
            for (Assess assess : assessList) {
                List<Respondents> respondentsList = respondentsRepository.findAllByAssessId(assess.getId());
                for (Respondents respondents : respondentsList) {
                    List<Answer> answerList = answerRepository.findAllByRespondentsId(respondents.getId());
                    for (Answer answer : answerList) {
                        answerRepository.deleteById(answer.getId());
                    }
                    respondentsRepository.deleteById(respondents.getId());
                }
                assessUserRepository.deleteByAssessId(assess.getId());
                assessRepository.deleteById(assess.getId());
            }
            testPaperRepository.deleteById(testPaper.getId());
        }

        teacherRepository.deleteByTeaId(id);
    }

    @Override
    public Teacher save(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }

    @Override
    public List<Integer> findAllByTeacherId(Integer id) {
        return teacherBindCclassRepository.findCclasesIdByTeaid(id);
    }

    @Override
    public void deleteTeacherBindClass(Integer id) {
        teacherBindCclassRepository.deleteById(id);
    }
}
