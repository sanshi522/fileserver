package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.ChoiceFile;
import com.sanshi.fileserver.repository.ChoiceFileRepository;
import com.sanshi.fileserver.service.ChoiceFileService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("ChoiceFileServiceImpl")
public class ChoiceFileServiceImpl implements ChoiceFileService {

    private ChoiceFileRepository  choiceFileRepository;

    public ChoiceFileServiceImpl(ChoiceFileRepository choiceFileRepository) {
        this.choiceFileRepository = choiceFileRepository;
    }

    @Override
    public int delete(Integer choiceId) {
        return choiceFileRepository.deleteByChoiceId(choiceId);
    }

    @Override
    public List<ChoiceFile> findByChoiceId(Integer choiceId) {
        return choiceFileRepository.findAllByChoiceId(choiceId);
    }
}
