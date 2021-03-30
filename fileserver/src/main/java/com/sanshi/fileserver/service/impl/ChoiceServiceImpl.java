package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.Choice;
import com.sanshi.fileserver.bean.TestPaperBindChoice;
import com.sanshi.fileserver.repository.ChoiceRepository;
import com.sanshi.fileserver.repository.TestPaperBindChoiceRepository;
import com.sanshi.fileserver.repository.TestPaperRepository;
import com.sanshi.fileserver.service.ChoiceService;
import com.sanshi.fileserver.vo.ScreenChoice;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("ChoiceServiceImpl")
public class ChoiceServiceImpl implements ChoiceService {
    private ChoiceRepository choiceRepository;
    private TestPaperBindChoiceRepository testPaperBindChoiceRepository;
    private TestPaperRepository testPaperRepository;

    public ChoiceServiceImpl(ChoiceRepository choiceRepository, TestPaperBindChoiceRepository testPaperBindChoiceRepository, TestPaperRepository testPaperRepository) {
        this.choiceRepository = choiceRepository;
        this.testPaperBindChoiceRepository = testPaperBindChoiceRepository;
        this.testPaperRepository = testPaperRepository;
    }

    @Override
    public Choice findOneById(Integer id) {
        return choiceRepository.findOneById(id);
    }

    @Override
    public Choice save(Choice choice) {
        return choiceRepository.save(choice);
    }

    @Override
    public Map findAll(ScreenChoice choice) {
        Map json = new HashMap();
        Sort sort;
        Pageable pageable;
        if (!choice.getSort().isEmpty()) {
            if (choice.getSort().equals("esc"))//升序
                sort = Sort.by(choice.getSortName()).ascending();
            else
                sort = Sort.by(choice.getSortName()).descending();
            pageable = PageRequest.of(choice.getPageIndex() , choice.getPageNumber(), sort);
        }
        pageable = PageRequest.of(choice.getPageIndex() , choice.getPageNumber());
        json.put("resoult", true);
        if (choice.getName().isEmpty()){
            if (choice.getSubId() == 0) {
                if (choice.getType() == 0) {
                    json.put("page",  choiceRepository.findAll(pageable));
                } else {
                    json.put("page",  choiceRepository.findAllByType(choice.getType(),pageable));
                }
            } else {
                if (choice.getType() == 0) {
                    json.put("page",  choiceRepository.findAllBySubId(choice.getSubId(),pageable));
                } else {
                    json.put("page",  choiceRepository.findAllBySubIdAndType(choice.getSubId(),choice.getType(),pageable));
                }
            }
        }else {
            if (choice.getSubId() == 0) {
                if (choice.getType() == 0) {
                    json.put("page",  choiceRepository.findAllByTopicLike("%"+choice.getName()+"%",pageable));
                } else {
                    json.put("page",  choiceRepository.findAllByTypeAndTopicLike(choice.getType(),"%"+choice.getName()+"%",pageable));
                }
            } else {
                if (choice.getType() == 0) {
                    json.put("page",  choiceRepository.findAllBySubIdAndTopicLike(choice.getSubId(),"%"+choice.getName()+"%",pageable));
                } else {
                    json.put("page",  choiceRepository.findAllBySubIdAndTypeAndTopicLike(choice.getSubId(),choice.getType(),"%"+choice.getName()+"%",pageable));
                }
            }
        }
        return json;
    }

    @Override
    public Map deleteById(Integer id) {
        Map json = new HashMap();
        List<TestPaperBindChoice> testPaperBindChoices = testPaperBindChoiceRepository.findAllByChoiceId(id);
        if (testPaperBindChoices.size()==0){
            choiceRepository.deleteById(id);
            json.put("resoult",true);
        }else{
            json.put("resoult",false);
            json.put("msg","已被"+testPaperBindChoices.size()+"张试卷关联，无删除权限");
            String testPaperNames = "" ;
            for(int i = 0;i < testPaperBindChoices.size(); i ++){
                testPaperNames+="\r\n"+(i+1)+"."+testPaperRepository.findOneById(testPaperBindChoices.get(i).getTestPaperId()).getName();
            }
            json.put("testPaperNames",testPaperNames);
        }
        return json;
    }
}
