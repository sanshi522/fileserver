package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.Assess;
import com.sanshi.fileserver.repository.AssessRepository;
import com.sanshi.fileserver.repository.CclassRepository;
import com.sanshi.fileserver.repository.GroupRepository;
import com.sanshi.fileserver.repository.StudentRepository;
import com.sanshi.fileserver.service.AssessService;
import com.sanshi.fileserver.vo.SessionUser;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service("AssessServiceImpl")
public class AssessServiceImpl implements AssessService {
    private StudentRepository studentRepository;
    private AssessRepository assessRepository;
    private GroupRepository groupRepository;
    private CclassRepository cclassRepository;

    public AssessServiceImpl(StudentRepository studentRepository, AssessRepository assessRepository, GroupRepository groupRepository, CclassRepository cclassRepository) {
        this.studentRepository = studentRepository;
        this.assessRepository = assessRepository;
        this.groupRepository = groupRepository;
        this.cclassRepository = cclassRepository;
    }

    /**
     *获取进行中考核
     * @param assess
     * @return
     */
    @Override
    public List<Assess> findAllValid(Assess assess, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map json = new HashMap();
        if (session != null && session.getAttribute("user") != null) {
            SessionUser sessionUser = new SessionUser();
            sessionUser = (SessionUser) session.getAttribute("user");
            Integer logintype = (sessionUser.getLogintype() == 0 ? 0 : sessionUser.getTeacher().getTeaIdentity());
            Integer userId = (sessionUser.getLogintype() == 0 ? sessionUser.getStudent().getStuId() : sessionUser.getTeacher().getTeaId());
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
        return null;
    }

    @Override
    public List<Assess> findAllInvalid(Assess assess, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map json = new HashMap();
        if (session != null && session.getAttribute("user") != null) {
            SessionUser sessionUser = new SessionUser();
            sessionUser = (SessionUser) session.getAttribute("user");
            Integer logintype = (sessionUser.getLogintype() == 0 ? 0 : sessionUser.getTeacher().getTeaIdentity());
            Integer userId = (sessionUser.getLogintype() == 0 ? sessionUser.getStudent().getStuId() : sessionUser.getTeacher().getTeaId());
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
        return null;
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
