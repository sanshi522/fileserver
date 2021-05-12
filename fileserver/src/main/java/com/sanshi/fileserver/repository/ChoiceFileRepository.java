package com.sanshi.fileserver.repository;

import com.sanshi.fileserver.bean.Cclass;
import com.sanshi.fileserver.bean.ChoiceFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ChoiceFileRepository  extends JpaRepository<ChoiceFile, Integer>, JpaSpecificationExecutor {

    int  deleteByChoiceId(Integer choiceId);

   ChoiceFile   save(ChoiceFile choiceFile);

   List<ChoiceFile> findAllByChoiceId(Integer id);
}
