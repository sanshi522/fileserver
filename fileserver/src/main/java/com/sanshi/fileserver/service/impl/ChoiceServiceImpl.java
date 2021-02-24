package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.Choice;
import com.sanshi.fileserver.service.ChoiceService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("ChoiceServiceImpl")
public class ChoiceServiceImpl implements ChoiceService {
    @Override
    public Choice findOneById(Integer id) {
        return null;
    }

    @Override
    public List<Choice> findAll(Choice choice) {
        return null;
    }

    @Override
    public void delete(Choice choice) {

    }
}
