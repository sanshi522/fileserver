package com.sanshi.fileserver.vo;

import com.sanshi.fileserver.bean.Assess;
import com.sanshi.fileserver.bean.AssessUser;
import com.sanshi.fileserver.bean.TestPaper;

import java.util.List;

public class AssessUerGVo {
    /**
     * 考核
     */
    private Assess assess;
    /**
     * 考核对象
     */
    private List<AssessUnameVo> listAssessUser;
    /**
     * 试卷对象
     */
    private TestPaper testPaper;

    public AssessUerGVo() {
    }

    public AssessUerGVo(Assess assess, List<AssessUnameVo> listAssessUser, TestPaper testPaper) {
        this.assess = assess;
        this.listAssessUser = listAssessUser;
        this.testPaper = testPaper;
    }

    public Assess getAssess() {
        return assess;
    }

    public void setAssess(Assess assess) {
        this.assess = assess;
    }

    public List<AssessUnameVo> getListAssessUser() {
        return listAssessUser;
    }

    public void setListAssessUser(List<AssessUnameVo> listAssessUser) {
        this.listAssessUser = listAssessUser;
    }

    public TestPaper getTestPaper() {
        return testPaper;
    }

    public void setTestPaper(TestPaper testPaper) {
        this.testPaper = testPaper;
    }


    public static void listAll(List a, List<List<AssessUser>> b) {
        for (int i = 0; i < b.size(); i++) {
            for (int j = 0; j < b.get(i).size(); j++) {
                a.add(b.get(i).get(j).getAssessId());
            }
        }
    }


    public static void listAdd(List<Integer> a, List<Integer> b) {
        for (Integer id : b) {
            a.add(id);
        }
    }


    @Override
    public String toString() {
        return "AssessUerGVo{" +
                "assess=" + assess +
                ", listAssessUser=" + listAssessUser +
                ", testPaper=" + testPaper +
                '}';
    }
}
