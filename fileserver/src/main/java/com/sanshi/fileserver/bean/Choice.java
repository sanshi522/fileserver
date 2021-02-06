package com.sanshi.fileserver.bean;

public class Choice {
    Integer id;
    /**
     * 学科id
     */
    Integer subId;
    /**
     *题目
     */
    String topic;
    /**
     * 类型（单选，多选，简答题，判断题    ）
     */
    Integer type;
    /**
     *附件id集合
     */
    String fileIds;
    /**
     * 选项个数
     */
    Integer optionNum;
    /**
     * 选项A
     */
    String optionA;
   /**
     * 选项B
     */
    String optionB;
   /**
     * 选项C
     */
    String optionC;
   /**
     * 选项D
     */
    String optionD;
   /**
     * 选项E
     */
    String optionE;
   /**
     * 选项F
     */
    String optionF;
    /**
     * 正确选项
     */
    String correct;
    /**
     * 解析
     */
    String analysis;
    /**
     * 知识点id集合
     */
    String abilityIds;

}
