package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.TestPaperBindChoice;
import com.sanshi.fileserver.repository.TestPaperBindChoiceRepository;
import com.sanshi.fileserver.service.TestPaperBindChoiceService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("TestPaperBindChoiceServiceImpl")
public class TestPaperBindChoiceServiceImpl implements TestPaperBindChoiceService {
    private TestPaperBindChoiceRepository testPaperBindChoiceRepository;

    public TestPaperBindChoiceServiceImpl(TestPaperBindChoiceRepository testPaperBindChoiceRepository) {
        this.testPaperBindChoiceRepository = testPaperBindChoiceRepository;
    }

    @Override
    public List<TestPaperBindChoice> findAll(TestPaperBindChoice testPaperBindChoice) {
        if(testPaperBindChoice.getTestPaperId()!=null){
            return  testPaperBindChoiceRepository.findAllByTestPaperId(testPaperBindChoice.getTestPaperId());
        }
        return null;
    }

    @Override
    public TestPaperBindChoice save(TestPaperBindChoice testPaperBindChoice) {
        return testPaperBindChoiceRepository.save(testPaperBindChoice);
    }

    @Override
    public Double findScoreSum(Integer id) {
        return testPaperBindChoiceRepository.findScoreSum(id);
    }

    @Override
    public void deleteById(TestPaperBindChoice testPaperBindChoice) {
        if (testPaperBindChoice.getId()!=null)
         testPaperBindChoiceRepository.deleteById(testPaperBindChoice.getId());
        else{
            if(testPaperBindChoice.getTestPaperId()!=null){
                testPaperBindChoiceRepository.deleteByTestPaperId(testPaperBindChoice.getTestPaperId());
            }else
                return;
        }
    }
}
