package com.sanshi.fileserver.vo;

import com.sanshi.fileserver.bean.Choice;
import com.sanshi.fileserver.bean.TestPaperBindChoice;
import lombok.Data;

/**
 * 试卷vo
 */
@Data
public class ChoiceDetails implements Comparable<ChoiceDetails>{
    TestPaperBindChoice testPaperBindChoice;
    Choice choice;

    public ChoiceDetails() {
    }

    public ChoiceDetails(TestPaperBindChoice testPaperBindChoice, Choice choice) {
        this.testPaperBindChoice = testPaperBindChoice;
        this.choice = choice;
    }

    public TestPaperBindChoice getTestPaperBindChoice() {
        return testPaperBindChoice;
    }

    public void setTestPaperBindChoice(TestPaperBindChoice testPaperBindChoice) {
        this.testPaperBindChoice = testPaperBindChoice;
    }

    public Choice getChoice() {
        return choice;
    }

    public void setChoice(Choice choice) {
        this.choice = choice;
    }

    @Override
    public String toString() {
        return "ChoiceDetails{" +
                "testPaperBindChoice=" + testPaperBindChoice +
                ", choice=" + choice +
                '}';
    }
        @Override
    public int compareTo(ChoiceDetails o) {
        return this.getTestPaperBindChoice().getIndexNum()-o.getTestPaperBindChoice().getIndexNum();
    }
}
