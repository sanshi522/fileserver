package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.TestPaper;
import com.sanshi.fileserver.service.TestPaperService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("TestPaperServiceImpl")
public class TestPaperServiceImpl implements TestPaperService {
    @Override
    public List<TestPaper> findAll(TestPaper testPaper) {
        return null;
    }

    @Override
    public TestPaper save(TestPaper testPaper) {
        return null;
    }

    @Override
    public void deleteById(TestPaper testPaper) {

    }
}
