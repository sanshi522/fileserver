package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.Choice;
import com.sanshi.fileserver.repository.ChoiceRepository;
import com.sanshi.fileserver.service.ChoiceService;
import com.sanshi.fileserver.vo.ScreenChoice;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("ChoiceServiceImpl")
public class ChoiceServiceImpl implements ChoiceService {
    private ChoiceRepository choiceRepository;

    public ChoiceServiceImpl(ChoiceRepository choiceRepository) {
        this.choiceRepository = choiceRepository;
    }

    @Override
    public Choice findOneById(Integer id) {
        return choiceRepository.findOneById(id);
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
            if (choice.getSubId() == null) {
                if (choice.getType() == null) {
                    json.put("page",  choiceRepository.findAll());
                } else {
                    json.put("page",  choiceRepository.findAllByType(choice.getType()));
                }
            } else {
                if (choice.getType() == null) {
                    json.put("page",  choiceRepository.findAllBySubId(choice.getSubId()));
                } else {
                    json.put("page",  choiceRepository.findAllBySubIdAndType(choice.getSubId(),choice.getType()));
                }
            }
        }else {
            if (choice.getSubId() == null) {
                if (choice.getType() == null) {
                    json.put("page",  choiceRepository.findAllByTopicLike("%"+choice.getName()+"%"));
                } else {
                    json.put("page",  choiceRepository.findAllByTypeAndTopicLike(choice.getType(),"%"+choice.getName()+"%"));
                }
            } else {
                if (choice.getType() == null) {
                    json.put("page",  choiceRepository.findAllBySubIdAndTopicLike(choice.getSubId(),"%"+choice.getName()+"%"));
                } else {
                    json.put("page",  choiceRepository.findAllBySubIdAndTypeAndTopicLike(choice.getSubId(),choice.getType(),"%"+choice.getName()+"%"));
                }
            }
        }
        return json;
    }

    @Override
    public void delete(Choice choice) {
        choiceRepository.deleteById(choice.getId());
    }
}
