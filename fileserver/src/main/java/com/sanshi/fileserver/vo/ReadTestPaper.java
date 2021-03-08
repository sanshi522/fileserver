package com.sanshi.fileserver.vo;

import com.sanshi.fileserver.bean.Choice;
import com.sanshi.fileserver.bean.TestPaper;
import lombok.Data;

import java.util.List;
@Data
public class ReadTestPaper {
    private TestPaper testPaper;
    private List<ChoiceDetails> choices;

    public ReadTestPaper() {
    }

    public ReadTestPaper(TestPaper testPaper, List<ChoiceDetails> choices) {
        this.testPaper = testPaper;
        this.choices = choices;
    }

    public TestPaper getTestPaper() {
        return testPaper;
    }

    public void setTestPaper(TestPaper testPaper) {
        this.testPaper = testPaper;
    }

    public List<ChoiceDetails> getChoices() {
        return choices;
    }

    public void setChoices(List<ChoiceDetails> choices) {
        this.choices = choices;
    }

    @Override
    public String toString() {
        return "ReadTestPaper{" +
                "testPaper=" + testPaper +
                ", choices=" + choices +
                '}';
    }
}
