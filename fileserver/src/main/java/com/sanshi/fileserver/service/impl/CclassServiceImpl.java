package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.Cclass;
import com.sanshi.fileserver.repository.CclassRepository;
import com.sanshi.fileserver.repository.TeacherBindCclassRepository;
import com.sanshi.fileserver.service.CclassService;
import com.sanshi.fileserver.vo.PageGet;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("CclassServiceImpl")
public class CclassServiceImpl implements CclassService {
    private CclassRepository cclassRepository;
    private TeacherBindCclassRepository teacherBindCclassRepository;

    public CclassServiceImpl(CclassRepository cclassRepository, TeacherBindCclassRepository teacherBindCclassRepository) {
        this.cclassRepository = cclassRepository;
        this.teacherBindCclassRepository = teacherBindCclassRepository;
    }


    @Override
    public List<Cclass> findCclasesByGradeId(Integer ident, Integer id, Integer GradeId) {
        if (ident == 1) {//单重身份：老师
            return cclassRepository.findByGradeIdAndIdIn(GradeId, teacherBindCclassRepository.findCclasesIdByTeaid(id));
        } else {
            return cclassRepository.findByGradeId(GradeId);
        }
    }

    @Override
    public Map GetClasesByClassId(PageGet pageGet) {
        Pageable pageable;
        pageable = PageRequest.of(pageGet.getPageIndex(), pageGet.getPageNumber());
        Map json = new HashMap();
        json.put("resoult", true);
        if (pageGet.getLikeName().isEmpty())
            json.put("page", cclassRepository.findAllByGradeId(pageGet.getIssistId(), pageable));
        else
            json.put("page", cclassRepository.findAllByGradeIdAndNameLike(pageGet.getIssistId(), pageGet.getLikeName(), pageable));
        return json;
    }

    @Override
    public Cclass findOneById(Integer Id) {
        return cclassRepository.findOneById(Id);
    }

    @Override
    public Cclass save(Cclass cclass) {
        return cclassRepository.save(cclass);
    }
}
