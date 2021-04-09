package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.TeacherBindCclass;
import com.sanshi.fileserver.repository.TeacherBindCclassRepository;
import com.sanshi.fileserver.service.SchoolingService;
import com.sanshi.fileserver.vo.PageGet;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("SchoolingServiceImpl")
public class SchoolingServiceImpl implements SchoolingService {
    private TeacherBindCclassRepository teacherBindCclassRepository;

    public SchoolingServiceImpl(TeacherBindCclassRepository teacherBindCclassRepository) {
        this.teacherBindCclassRepository = teacherBindCclassRepository;
    }

    @Override
    public Map finAlltByClassId(PageGet pageGet) {
        Pageable pageable;
        pageable = PageRequest.of(pageGet.getPageIndex() , pageGet.getPageNumber());
        Map json = new HashMap();
        json.put("resoult", true);
        if (pageGet.getLikeName().isEmpty()) pageGet.setLikeName("");
        json.put("page",teacherBindCclassRepository.findAllByCclassId(pageGet.getIssistId(),pageable));
        return json;
    }
    @Override
    public TeacherBindCclass save(TeacherBindCclass teacherBindCclass) {
        return teacherBindCclassRepository.save(teacherBindCclass);
    }

    @Override
    public void deleteById(Integer id) {
        teacherBindCclassRepository.deleteById(id);
    }
}
