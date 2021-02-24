package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.TestPaperBindChoice;
import com.sanshi.fileserver.service.TestPaperBindChoiceService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("TestPaperBindChoiceServiceImpl")
public class TestPaperBindChoiceServiceImpl implements TestPaperBindChoiceService {
    @Override
    public List<TestPaperBindChoice> findAll(TestPaperBindChoice testPaperBindChoice) {
        return null;
    }

    @Override
    public TestPaperBindChoice save(TestPaperBindChoice testPaperBindChoice) {
        return null;
    }

    @Override
    public void deleteById(TestPaperBindChoice TestPaperBindChoice) {

    }
}
