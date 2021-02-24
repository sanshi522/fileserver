package com.sanshi.fileserver.vo;

import com.sanshi.fileserver.bean.Choice;
import lombok.Data;

/**
 * 试卷vo
 */
@Data
public class ChoiceDetails {
    /**
     * 试题下标
     */
    Integer index;
    /**
     * 分值
     */
    Double score;
    /**
     * 试题
     */
    Choice choice;
}
