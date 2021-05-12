package com.sanshi.fileserver.service;

import com.sanshi.fileserver.bean.ChoiceFile;

import java.util.List;

public interface ChoiceFileService {

    public  int delete(Integer choiceId);

    List<ChoiceFile> findByChoiceId(Integer choiceId);


}
