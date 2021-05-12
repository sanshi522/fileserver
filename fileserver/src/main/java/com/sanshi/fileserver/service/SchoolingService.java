package com.sanshi.fileserver.service;

import com.sanshi.fileserver.bean.TeacherBindCclass;
import com.sanshi.fileserver.vo.PageGet;

import java.util.Map;

public interface SchoolingService {
    Map finAlltByClassId(PageGet val);

    TeacherBindCclass save(TeacherBindCclass teacherBindCclass);

    void deleteById(Integer id);
}
