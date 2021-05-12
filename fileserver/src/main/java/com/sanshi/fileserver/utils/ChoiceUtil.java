package com.sanshi.fileserver.utils;

import com.sanshi.fileserver.bean.Choice;
import com.sanshi.fileserver.bean.ChoiceFile;

import java.util.List;

public class ChoiceUtil {
    private Choice  choice;
    private List<ChoiceFile> choiceFileList;

    public ChoiceUtil() {
    }

    public ChoiceUtil(Choice choice, List<ChoiceFile> choiceFileList) {
        this.choice = choice;
        this.choiceFileList = choiceFileList;
    }

    public Choice getChoice() {
        return choice;
    }

    public void setChoice(Choice choice) {
        this.choice = choice;
    }

    public List<ChoiceFile> getChoiceFileList() {
        return choiceFileList;
    }

    public void setChoiceFileList(List<ChoiceFile> choiceFileList) {
        this.choiceFileList = choiceFileList;
    }

    @Override
    public String toString() {
        return "ChoiceUtil{" +
                "choice=" + choice +
                ", choiceFileList=" + choiceFileList +
                '}';
    }


}
