package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.Cclass;
import com.sanshi.fileserver.repository.CclassRepository;
import com.sanshi.fileserver.repository.TeacherBindCclassRepository;
import com.sanshi.fileserver.service.CclassService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("CclassServiceImpl")
public class CclassServiceImpl implements CclassService {
    private CclassRepository cclassRepository;
    private TeacherBindCclassRepository teacherBindCclassRepository;
    public CclassServiceImpl(CclassRepository cclassRepository, TeacherBindCclassRepository teacherBindCclassRepository) {
        this.cclassRepository = cclassRepository;
        this.teacherBindCclassRepository = teacherBindCclassRepository;
    }


    @Override
    public List<Cclass> findCclasesByGradeId(Integer ident, Integer id,Integer GradeId) {
        if (ident==1){//单重身份：老师
            return cclassRepository.findByGradeIdAndIdIn(GradeId,teacherBindCclassRepository.findCclasesIdByTeaid(id));
        }else{
            return cclassRepository.findByGradeId(GradeId);
        }
    }

    @Override
    public Cclass findOneById(Integer Id) {
        return cclassRepository.findOneById(Id);
    }
}
