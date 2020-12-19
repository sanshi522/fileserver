package com.sanshi.fileserver.ytest;
import com.sanshi.fileserver.bean.FileSample;
import com.sanshi.fileserver.service.FileSampleService;
import com.sanshi.fileserver.vo.ScreenShareFile;
import org.junit.Test;
import org.springframework.data.domain.Page;

public class Tests {
   private FileSampleService fileSampleService;

    public Tests(FileSampleService fileSampleService) {
        this.fileSampleService = fileSampleService;
    }

    @Test
    public void test() {
       // ScreenShareFile screenShareFile=new ScreenShareFile(2,1,0,1,0,0,"jdk",null,null);
        //Page<FileSample> page = fileSampleService.ScreenALL(screenShareFile);
        //System.out.println(page);
    }
}
