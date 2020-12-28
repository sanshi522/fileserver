package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.repository.TeacherBindCclassRepository;
import com.sanshi.fileserver.vo.SessionUser;
import com.sanshi.fileserver.bean.Student;
import com.sanshi.fileserver.bean.Teacher;
import com.sanshi.fileserver.repository.TeacherRepository;
import com.sanshi.fileserver.service.TeacherService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Service("TeacherServiceImpl")
public class TeacherServiceImpl implements TeacherService {
    private  TeacherRepository teacherRepository;
    private TeacherBindCclassRepository teacherBindCclassRepository;
    public TeacherServiceImpl(TeacherRepository teacherRepository, TeacherBindCclassRepository teacherBindCclassRepository) {
        this.teacherRepository = teacherRepository;
        this.teacherBindCclassRepository = teacherBindCclassRepository;
    }

    @Override
    public List<Student> selectAll() {
        return null;
    }

    @Override
    public Student selectByName(String name) {
        return null;
    }

    @Override
    public Integer Login(String name, String pass,Integer identity, HttpServletRequest request) {
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
        List<Integer> idents=new ArrayList<Integer>();
        idents.add(2);
        idents.add(3);
        return teacherRepository.findAllByTeaIdentityIn(idents);
    }
}
