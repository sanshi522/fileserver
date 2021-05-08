package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.Grade;
import com.sanshi.fileserver.repository.CclassRepository;
import com.sanshi.fileserver.repository.GradeRepository;
import com.sanshi.fileserver.repository.TeacherBindCclassRepository;
import com.sanshi.fileserver.service.GradeService;
import com.sanshi.fileserver.vo.PageGet;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if (ident == 1) {//老师
            return gradeRepository.findAllByYearAndIdIn(yearNumber, cclassRepository.findGradeIdsByIdIn(teacherBindCclassRepository.findCclasesIdByTeaid(id)));
        } else {//管理员
            return gradeRepository.findAllByYear(yearNumber);
        }

    }

    @Override
    public List<Integer> findAllYear(Integer ident, Integer id) {
        if (ident == 1) {//老师
            return gradeRepository.findYearsByIdIn(cclassRepository.findGradeIdsByIdIn(teacherBindCclassRepository.findCclasesIdByTeaid(id)));
        } else {//管理员
            return gradeRepository.findAllYears();
        }
    }

    @Override
    public Map GetGradesByyearNumber(PageGet pageGet) {
        Pageable pageable;
        pageable = PageRequest.of(pageGet.getPageIndex(), pageGet.getPageNumber());
        Map json = new HashMap();
        json.put("resoult", true);
        if (pageGet.getLikeName().isEmpty())
            json.put("page", gradeRepository.findAllByYear(pageGet.getIssistId(), pageable));
        else
            json.put("page", gradeRepository.findAllByYearAndNameLike(pageGet.getIssistId(), pageGet.getLikeName(), pageable));
        return json;
    }


    @Override
    public Grade findOneById(Integer id) {
        return gradeRepository.findOneById(id);
    }

    @Override
    public Grade save(Grade grade) {
        return gradeRepository.save(grade);
    }

    public Integer deleteById(Integer id) {
        gradeRepository.deleteById(id);
        return 1;
    }
}
