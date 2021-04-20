package com.sanshi.fileserver.bean;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
@Entity
public class Choice {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    /**
     * 学科id
     */
    private Integer subId;
    /**
     *题目
     */
    private String topic;
    /**
     * 类型（单选，多选，判断题，简答题）
     */
    private Integer type;
    /**
     *附件id集合
     */
    private String fileIds;
    /**
     * 选项个数
     */
    private Integer optionNum;
    /**
     * 选项A
     */
    private String optionA;
   /**
     * 选项B
     */
   private String optionB;
   /**
     * 选项C
     */
   private String optionC;
   /**
     * 选项D
     */
   private String optionD;
   /**
     * 选项E
     */
   private String optionE;
   /**
     * 选项F
     */
   private String optionF;
    /**
     * 正确选项
     */
    private String correct;
    /**
     * 解析
     */
    private String analysis;
    /**
     * 知识点id集合
     */
    private String abilityIds;
    /**
     *难易程度
     */
    private Integer difficultyLevel;
    /**
     * 评分标准(0:全自动相等;1:少选百分比多选不得分；3：人工)
     */
    private Integer scaleRule;
    @CreationTimestamp
    private Date createTime;
    @UpdateTimestamp
    private Date uapdateTime;

    public Choice() {
    }

    public Choice(Integer id, Integer subId, String topic, Integer type, String fileIds, Integer optionNum, String optionA, String optionB, String optionC, String optionD, String optionE, String optionF, String correct, String analysis, String abilityIds, Integer difficultyLevel, Integer scaleRule, Date createTime, Date uapdateTime) {
        this.id = id;
        this.subId = subId;
        this.topic = topic;
        this.type = type;
        this.fileIds = fileIds;
        this.optionNum = optionNum;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.optionE = optionE;
        this.optionF = optionF;
        this.correct = correct;
        this.analysis = analysis;
        this.abilityIds = abilityIds;
        this.difficultyLevel = difficultyLevel;
        this.scaleRule = scaleRule;
        this.createTime = createTime;
        this.uapdateTime = uapdateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        this.subId = subId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFileIds() {
        return fileIds;
    }

    public void setFileIds(String fileIds) {
        this.fileIds = fileIds;
    }

    public Integer getOptionNum() {
        return optionNum;
    }

    public void setOptionNum(Integer optionNum) {
        this.optionNum = optionNum;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getOptionE() {
        return optionE;
    }

    public void setOptionE(String optionE) {
        this.optionE = optionE;
    }

    public String getOptionF() {
        return optionF;
    }

    public void setOptionF(String optionF) {
        this.optionF = optionF;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getAbilityIds() {
        return abilityIds;
    }

    public void setAbilityIds(String abilityIds) {
        this.abilityIds = abilityIds;
    }

    public Integer getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(Integer difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public Integer getScaleRule() {
        return scaleRule;
    }

    public void setScaleRule(Integer scaleRule) {
        this.scaleRule = scaleRule;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUapdateTime() {
        return uapdateTime;
    }

    public void setUapdateTime(Date uapdateTime) {
        this.uapdateTime = uapdateTime;
    }

    @Override
    public String toString() {
        return "Choice{" +
                "id=" + id +
                ", subId=" + subId +
                ", topic='" + topic + '\'' +
                ", type=" + type +
                ", fileIds='" + fileIds + '\'' +
                ", optionNum=" + optionNum +
                ", optionA='" + optionA + '\'' +
                ", optionB='" + optionB + '\'' +
                ", optionC='" + optionC + '\'' +
                ", optionD='" + optionD + '\'' +
                ", optionE='" + optionE + '\'' +
                ", optionF='" + optionF + '\'' +
                ", correct='" + correct + '\'' +
                ", analysis='" + analysis + '\'' +
                ", abilityIds='" + abilityIds + '\'' +
                ", difficultyLevel=" + difficultyLevel +
                ", scaleRule=" + scaleRule +
                ", createTime=" + createTime +
                ", uapdateTime=" + uapdateTime +
                '}';
    }
}
