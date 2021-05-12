package com.sanshi.fileserver.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FileExists {
    /**
     * 文件 id
     */
    private Integer id;

    /**
     * 文件状态
     * -1: 不存在
     * 1: 已存在
     * 0: 部分存在
     */
    private Integer status;

    /**
     * 已上传的文件分片索引
     */
    private List<Integer> patchIndex;

    public FileExists(Integer id, Integer status, List<Integer> patchIndex) {
        this.id = id;
        this.status = status;
        this.patchIndex = patchIndex;
    }

    /**
     * -1: 不存在
     *
     * @return
     */
    public static FileExists nonExistent() {
        return new FileExists(null, -1, null);
    }

    /**
     * 已存在
     *
     * @param id
     * @return
     */
    public static FileExists exists(Integer id) {
        return new FileExists(id, 1, null);
    }

    /**
     * 部分存在
     *
     * @param id
     * @param patchIndex
     * @return
     */
    public static FileExists partExistent(Integer id, List<Integer> patchIndex) {
        return new FileExists(id, 0, patchIndex);
    }
}
