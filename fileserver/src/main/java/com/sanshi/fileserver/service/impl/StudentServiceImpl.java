package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.vo.SessionUser;
import com.sanshi.fileserver.bean.Student;
import com.sanshi.fileserver.repository.StudentRepository;
import com.sanshi.fileserver.service.StudentService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service("StudentServiceImpl")
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> selectAll() {
        return null;
    }

    @Override
    public Student selectByNumber(String slumber) {
        List<Student> students = studentRepository.findByStuNumber(slumber);
        if (students == null || students.size() == 0) {
            return null;
        } else {
            return students.get(0);
        }
    }

    @Override
    public Integer Login(String slumber, String pass, Integer identity,HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<Student> students = studentRepository.findByStuNumber(slumber);
        if (students == null || students.size() == 0) {
            return -1;
        } else {
            Student student = students.get(0);
            if (student.getStuPass().equals(pass)) {
                SessionUser sessionUser = new SessionUser();
                sessionUser.setLogin(true);
                sessionUser.setLogintype(identity);
                sessionUser.setStudent(student);
                session.setAttribute("user", sessionUser);
                return 1;
            } else {
                return 0;
            }
        }
    }
}
