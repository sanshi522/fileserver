package com.sanshi.fileserver.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.sanshi.fileserver.bean.*;
import com.sanshi.fileserver.service.*;
import com.sanshi.fileserver.utils.ChoiceExcel;
import com.sanshi.fileserver.utils.StudentExcel;
import com.sanshi.fileserver.vo.ExcelUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/exportExcel")
public class ExportExcelController {
    private StudentService studentService;
    private ChoiceService choiceService;
    private StuGroupService stuGroupService;
    private SubjectService subjectService;
    private KnowledgePointService knowledgePointService;


    public ExportExcelController(StudentService studentService, ChoiceService choiceService, StuGroupService stuGroupService, SubjectService subjectService, KnowledgePointService knowledgePointService) {
        this.studentService = studentService;
        this.choiceService = choiceService;
        this.stuGroupService = stuGroupService;
        this.subjectService = subjectService;
        this.knowledgePointService = knowledgePointService;
    }

    //导出模板
    @RequestMapping("/studentexportExcel")
    public void studentexportExcel(HttpServletResponse response, HttpServletRequest request, Integer cclassId) throws IOException {
        List<StudentExcel> studentList = new ArrayList<StudentExcel>();
        List<StuGroup> stuGroupList = stuGroupService.findGroupsByCclassId(cclassId);
        int size = 1;
        StudentExcel student = new StudentExcel("123", "小名", "男", "123456", null, 1, "是个狠人");
        if (stuGroupList.size() > 0) {
            size = stuGroupList.size();
            student.setStuGroup(stuGroupList.get(0).getName());
            studentList.add(student);
        }
        String[] arr = new String[size];
        for (int i = 0; i < stuGroupList.size(); i++) {
            arr[i] = stuGroupList.get(i).getName();
        }
        ExportParams exportParams = new ExportParams("学生信息表", "导出");
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, StudentExcel.class, studentList);
        ExcelUtils.selectList(workbook, 4, 4, arr);
        ExcelUtils.selectList(workbook, 5, 5, new String[]{"在读", "毕业"});
        ExcelUtils.downLoadExcel("学生信息模板", workbook, request, response);
    }

    @ResponseBody
    @PostMapping("/importExcel")
    public String importExcel(@RequestParam("file") MultipartFile file, Integer classId) throws Exception {

        ImportParams importParams = new ImportParams();
        // 数据处理
        //表格标题行数,默认0
        importParams.setHeadRows(1);
        //表头行数,默认1
        importParams.setTitleRows(1);
        //是否需要校验上传的Excel,默认false
        importParams.setNeedVerfiy(false);

        try {
            ExcelImportResult<StudentExcel> result = ExcelImportUtil.importExcelMore(file.getInputStream(), StudentExcel.class, importParams);
            List<StudentExcel> studentExcelList = result.getList();
            List<Student> studentList = new ArrayList<Student>();
            for (StudentExcel student : studentExcelList) {
                Student st = new Student();
                st.setStuName(student.getStuName());
                st.setStuState(student.getStuState());
                st.setStuPass(student.getStuPass());
                st.setStuRemake(student.getStuRemake());
                st.setStuNumber(student.getStuNumber());
                st.setStuGender(student.getStuGender());
                StuGroup stuGroup = stuGroupService.findOneByName(classId, student.getStuGroup());
                if (stuGroup != null) {
                    st.setStuGroup(stuGroup.getId());
                }
                studentList.add(st);
            }
            studentService.saveStudents(studentList);

        } catch (IOException e) {
            // log.error("导入失败：{}", e.getMessage());
        } catch (Exception e1) {
            //  log.error("导入失败：{}", e1.getMessage());
        }
        return "导入成功";
    }

    //导出模板
    @RequestMapping("/choiceexportExcel")
    public void choiceexportExcel(HttpServletResponse response, HttpServletRequest request) throws IOException {
        List<ChoiceExcel> choiceExcelList = new ArrayList<ChoiceExcel>();
        List<Subject> subjectList = subjectService.findAll();
        int size = 1;
        ChoiceExcel choiceExcel = new ChoiceExcel(null, "北京所在的时区是？", 1, 4, "西四区", "东九区", "东八区", "西九区", null, null,
                "C", "北京是中国的首都位于东八区", 1, 0, "世界时区");
        if (subjectList.size() > 0) {
            size = subjectList.size();
            choiceExcel.setSubId(subjectList.get(0).getName());
        }
        choiceExcelList.add(choiceExcel);
        String[] arr = new String[size];
        for (int i = 0; i < subjectList.size(); i++) {
            arr[i] = subjectList.get(i).getName();
        }
        ExportParams exportParams = new ExportParams("试题信息", "导出");
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, ChoiceExcel.class, choiceExcelList);
        ExcelUtils.selectList(workbook, 0, 0, arr);
        ExcelUtils.selectList(workbook, 2, 2, new String[]{"单选题", "多选题", "判读题", "简答题"});
        ExcelUtils.selectList(workbook, 12, 12, new String[]{"1", "2", "3", "4", "5"});
        ExcelUtils.selectList(workbook, 13, 13, new String[]{"全自动相等", "少选百分比多选不得分", "人工"});
        ExcelUtils.downLoadExcel("试题信息模板", workbook, request, response);
    }

    @ResponseBody
    @PostMapping("/importExcels")
    public String importExcels(@RequestParam("file") MultipartFile file) throws Exception {
        ImportParams importParams = new ImportParams();
        // 数据处理
        //表格标题行数,默认0
        importParams.setHeadRows(1);
        //表头行数,默认1
        importParams.setTitleRows(1);
        //是否需要校验上传的Excel,默认false
        importParams.setNeedVerfiy(false);

        try {
            ExcelImportResult<ChoiceExcel> result = ExcelImportUtil.importExcelMore(file.getInputStream(), ChoiceExcel.class, importParams);
            List<ChoiceExcel> choiceExcelList = result.getList();
            List<Choice> choicesList = new ArrayList<Choice>();
            for (ChoiceExcel choiceExcel : choiceExcelList) {
                Choice choice = new Choice();
                Subject subject = choiceService.findOneByName(choiceExcel.getSubId());
                if (subject != null) {
                    choice.setSubId(subject.getId());
                }
                if (choiceExcel.getType().equals("") || choiceExcel.getType() == null || subject == null) {
                    continue;
                }
                choice.setType(choiceExcel.getType());
                choice.setOptionA(choiceExcel.getOptionA());
                choice.setOptionB(choiceExcel.getOptionB());
                choice.setOptionC(choiceExcel.getOptionC());
                choice.setOptionD(choiceExcel.getOptionD());
                choice.setOptionE(choiceExcel.getOptionE());
                choice.setOptionF(choiceExcel.getOptionF());
                choice.setTopic(choiceExcel.getTopic());
                choice.setOptionNum(choiceExcel.getOptionNum());

                if (choiceExcel.getType() == 1 || choiceExcel.getType() == 2) {

                    choice.setCorrect(choiceExcel.getCorrect().toUpperCase());
                } else {
                    choice.setCorrect(choiceExcel.getCorrect());
                }
                choice.setDifficultyLevel(choiceExcel.getDifficultyLevel());
                choice.setScaleRule(choiceExcel.getScaleRule());
                choice.setAnalysis(choiceExcel.getAnalysis());
                //知识点
                if (choiceExcel.getAbilityIds() != null && !choiceExcel.getAbilityIds().equals("")) {
                    String[] strArray = choiceExcel.getAbilityIds().split(",");
                    String values = "";
                    for (int i = 0; i < strArray.length; i++) {
                        if (!strArray[i].equals("")) {
                            KnowledgePoint knowledgePoint = knowledgePointService.findOneBySubIdAndName(subject.getId(), strArray[i]);
                            if (knowledgePoint != null) {
                                values += knowledgePoint.getId() + ",";
                            } else {
                                KnowledgePoint ko = knowledgePointService.save(new KnowledgePoint(null, subject.getId(), strArray[i], strArray[i]));
                                values += ko.getId() + ",";
                            }
                        }
                    }
                    values = values.substring(0, values.length() - 1);
                    choice.setAbilityIds(values);
                }
                choicesList.add(choice);
            }
            choiceService.saves(choicesList);
        } catch (IOException e) {
            // log.error("导入失败：{}", e.getMessage());
        } catch (Exception e1) {
            //  log.error("导入失败：{}", e1.getMessage());
        }
        return "导入成功";
    }

}
