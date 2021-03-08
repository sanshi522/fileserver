package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.repository.GroupRepository;
import com.sanshi.fileserver.vo.PageGet;
import com.sanshi.fileserver.vo.SessionUser;
import com.sanshi.fileserver.bean.Student;
import com.sanshi.fileserver.repository.StudentRepository;
import com.sanshi.fileserver.service.StudentService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("StudentServiceImpl")
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;
    private GroupRepository groupRepository;

    public StudentServiceImpl(StudentRepository studentRepository, GroupRepository groupRepository) {
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public List<Student> selectAll() {
        return null;
    }

    @Override
    public Student findOneById(Integer id) {
        return studentRepository.findOneByStuId(id);
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

    @Override
    public List<Student> findAllByStuGroup(Integer StuGroup) {
        return studentRepository.findAllByStuGroup(StuGroup);
    }

    @Override
    public Map findAllByClassId(PageGet pageGet) {
        Pageable pageable;
        pageable = PageRequest.of(pageGet.getPageIndex() , pageGet.getPageNumber());
        Map json = new HashMap();
        json.put("resoult", true);
        if (pageGet.getLikeName().isEmpty())
            json.put("page", studentRepository.findAllByStuGroupIn(groupRepository.findALLIdByCclassId(pageGet.getIssistId()),pageable));
        else
            json.put("page", studentRepository.findAllByStuGroupInAndStuNameLike(groupRepository.findALLIdByCclassId(pageGet.getIssistId()),pageGet.getLikeName(),pageable));

        return json;
    }
    @Override
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Integer deleteById(Integer val) {
        studentRepository.deleteById(val);
        return 1;
    }
}
