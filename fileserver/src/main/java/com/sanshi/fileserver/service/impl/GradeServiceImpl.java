package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.Grade;
import com.sanshi.fileserver.repository.CclassRepository;
import com.sanshi.fileserver.repository.GradeRepository;
import com.sanshi.fileserver.repository.TeacherBindCclassRepository;
import com.sanshi.fileserver.service.GradeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("GradeServiceImpl")
public class GradeServiceImpl implements GradeService {
    private GradeRepository gradeRepository;
    private CclassRepository cclassRepository;
    private TeacherBindCclassRepository teacherBindCclassRepository;

    public GradeServiceImpl(GradeRepository gradeRepository, CclassRepository cclassRepository, TeacherBindCclassRepository teacherBindCclassRepository) {
        this.gradeRepository = gradeRepository;
        this.cclassRepository = cclassRepository;
        this.teacherBindCclassRepository = teacherBindCclassRepository;
    }

    @Override
    public List<Grade> findAll(Integer ident, Integer id, Integer yearNumber) {
        if (ident==1){//老师
            return gradeRepository.findAllByYearAndIdIn(yearNumber,cclassRepository.findGradeIdsByIdIn(teacherBindCclassRepository.findCclasesIdByTeaid(id)));
        }else{//管理员
            return gradeRepository.findAllByYear(yearNumber);
        }

    }

    @Override
    public List<Integer> findAllYear(Integer Ident, Integer id) {
        return gradeRepository.findYearsByIdIn(cclassRepository.findGradeIdsByIdIn(teacherBindCclassRepository.findCclasesIdByTeaid(id)));
    }


    @Override
    public Grade findOneById(Integer id) {
        return gradeRepository.findOneById(id);
    }
}
