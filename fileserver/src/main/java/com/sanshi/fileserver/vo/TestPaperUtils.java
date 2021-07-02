package com.sanshi.fileserver.vo;

import com.sanshi.fileserver.bean.Choice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestPaperUtils {
    /**
     * 试卷名称
     */
    private String testPaperName;

    /**
     * 学科
     */

    private Integer subId;
    /**
     * 难度
     */
    private Integer difficulty;
    /**
     * 单选题数量
     */
    private int rodSum;

    /**
     * 单选题分
     */
    private double rodScore;

    /**
     * 多选题数量
     */
    private int checkSum;

    /**
     * 多选题分
     */
    private double checkScore;

    /**
     * 判断题数量
     */
    private int judgeSum;

    /**
     * 判断题分
     */

    private double judgeScore;

    /**
     * 简答题数量
     */
    private int answerSum;


    /**
     * 简答题分
     */
    private double answerScore;

    /**
     * 判断题数量
     */

    private  int   gapSum;

    /**
     * 判断题分数
     */
    private  double  gapScore;


    /**
     * 总分
     */
    private double totalScore;


    public TestPaperUtils() {
    }


    public TestPaperUtils(String testPaperName, Integer subId, Integer difficulty, int rodSum, double rodScore, int checkSum, double checkScore, int judgeSum, double judgeScore, int answerSum, double answerScore, double totalScore) {
        this.testPaperName = testPaperName;
        this.subId = subId;
        this.difficulty = difficulty;
        this.rodSum = rodSum;
        this.rodScore = rodScore;
        this.checkSum = checkSum;
        this.checkScore = checkScore;
        this.judgeSum = judgeSum;
        this.judgeScore = judgeScore;
        this.answerSum = answerSum;
        this.answerScore = answerScore;
        this.totalScore = totalScore;
    }


    public TestPaperUtils(String testPaperName, Integer subId, Integer difficulty, int rodSum, double rodScore, int checkSum, double checkScore, int judgeSum, double judgeScore, int answerSum, double answerScore, int gapSum, double gapScore, double totalScore) {
        this.testPaperName = testPaperName;
        this.subId = subId;
        this.difficulty = difficulty;
        this.rodSum = rodSum;
        this.rodScore = rodScore;
        this.checkSum = checkSum;
        this.checkScore = checkScore;
        this.judgeSum = judgeSum;
        this.judgeScore = judgeScore;
        this.answerSum = answerSum;
        this.answerScore = answerScore;
        this.gapSum = gapSum;
        this.gapScore = gapScore;
        this.totalScore = totalScore;
    }

    public int getGapSum() {
        return gapSum;
    }

    public void setGapSum(int gapSum) {
        this.gapSum = gapSum;
    }

    public double getGapScore() {
        return gapScore;
    }

    public void setGapScore(double gapScore) {
        this.gapScore = gapScore;
    }

    public double getRodScore() {
        return rodScore;
    }

    public void setRodScore(double rodScore) {
        this.rodScore = rodScore;
    }

    public double getCheckScore() {
        return checkScore;
    }

    public void setCheckScore(double checkScore) {
        this.checkScore = checkScore;
    }

    public double getJudgeScore() {
        return judgeScore;
    }

    public void setJudgeScore(double judgeScore) {
        this.judgeScore = judgeScore;
    }

    public double getAnswerScore() {
        return answerScore;
    }

    public void setAnswerScore(double answerScore) {
        this.answerScore = answerScore;
    }

    public String getTestPaperName() {
        return testPaperName;
    }

    public void setTestPaperName(String testPaperName) {
        this.testPaperName = testPaperName;
    }

    public Integer getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        this.subId = subId;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public int getRodSum() {
        return rodSum;
    }

    public void setRodSum(int rodSum) {
        this.rodSum = rodSum;
    }

    public int getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(int checkSum) {
        this.checkSum = checkSum;
    }

    public int getJudgeSum() {
        return judgeSum;
    }

    public void setJudgeSum(int judgeSum) {
        this.judgeSum = judgeSum;
    }

    public int getAnswerSum() {
        return answerSum;
    }

    public void setAnswerSum(int answerSum) {
        this.answerSum = answerSum;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    @Override
    public String toString() {
        return "TestPaperUtils{" +
                "difficulty=" + difficulty +
                ", rodSum=" + rodSum +
                ", checkSum=" + checkSum +
                ", judgeSum=" + judgeSum +
                ", answerSum=" + answerSum +
                ", TotalScore=" + totalScore +
                '}';
    }

    /**
     * 获取随机试题
     *
     * @param choiceList 试题集合
     * @param sum        数量
     * @return
     */
    public static List<Choice> red(List<Choice> choiceList, int sum) {
        List<Choice> list = new ArrayList<Choice>();
        Random rand = new Random();
        int r = 0;
        boolean s = false;
        while (list.size() < sum) {
            s = false;
            r = rand.nextInt(choiceList.size());
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId() ==choiceList.get(r).getId()) {
                    s = true;
                    break;
                }
            }
            if (s) {
                continue;
            }
            list.add(choiceList.get(r));
        }
        return list;
    }

    public static List<Choice> conversion(List<List<Choice>> arr) {
        List<Choice> a = new ArrayList<Choice>();
        for (List<Choice> list : arr) {
            for (Choice l : list) {
                a.add(l);
            }
        }
        return a;
    }


}
