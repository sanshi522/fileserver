package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.FileSample;
import com.sanshi.fileserver.repository.*;
import com.sanshi.fileserver.service.FileSampleService;
import com.sanshi.fileserver.utils.MergeObjectsUtil;
import com.sanshi.fileserver.vo.FileExists;
import com.sanshi.fileserver.vo.ScreenShareFile;
import com.sanshi.fileserver.vo.SessionUser;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Year;
import java.util.*;


@Service("FileSampleServiceImpl")
public class FileSampleServiceImpl implements FileSampleService {
    private FileSampleRepository fileSampleRepository;
    private ShareRightRepository shareRightRepository;
    private ShareFileRepository shareFileRepository;
    private TeacherBindCclassRepository teacherBindCclassRepository;
    private GradeRepository gradeRepository;
    private CclassRepository cclassRepository;
    private StudentRepository studentRepository;
    private GroupRepository groupRepository;

    public FileSampleServiceImpl(FileSampleRepository fileSampleRepository, ShareRightRepository shareRightRepository, ShareFileRepository shareFileRepository, TeacherBindCclassRepository teacherBindCclassRepository, GradeRepository gradeRepository, CclassRepository cclassRepository, StudentRepository studentRepository, GroupRepository groupRepository) {
        this.fileSampleRepository = fileSampleRepository;
        this.shareRightRepository = shareRightRepository;
        this.shareFileRepository = shareFileRepository;
        this.teacherBindCclassRepository = teacherBindCclassRepository;
        this.gradeRepository = gradeRepository;
        this.cclassRepository = cclassRepository;
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
    }


    @Override
    public FileExists fileExists(String md5, Long size) {
        FileSample file = fileSampleRepository.findByMd5(md5);
        if(file == null) {
            return FileExists.nonExistent();
        }
        if(file.getSize().equals(size)) {
            return FileExists.exists(file.getId());
        }
        return FileExists.partExistent(file.getId(), fileSampleRepository.findPatchIndexByParent(file.getId()));
    }

    @Override
    public FileSample insertFileSample(FileSample fileSample) {
      //  fileSample.setId(fileSampleRepository.insertFileSample(fileSample));
        fileSampleRepository.save(fileSample);
        return fileSample;
       // return null;
    }
    @Override
    public FileSample findById(Integer Id) {
        return fileSampleRepository.findById(Id).get();
    }
    @Override
    public int deleteById(Integer id) {
        fileSampleRepository.deleteById(id);
        return 1;
    }
    @Override
    public int updateById(FileSample file){
        FileSample fileSample =new FileSample();
        FileSample fileSample2 =new FileSample();
        fileSample2=fileSampleRepository.findById(file.getId()).get();
        fileSample=(FileSample) MergeObjectsUtil.isNullNoCover(fileSample2, file);
        if (null != fileSample){
            fileSampleRepository.save(fileSample);
            return 1;
        }else{
            return -1;
        }
    }
    @Override
    public FileSample findByParentAndMd5(Integer parent, String md5) {
        return fileSampleRepository.findByParentAndMd5(parent,md5);
    }
    @Override
    public int deleteByParent(Integer parent) {
        fileSampleRepository.deleteByParent(parent);
        return 1;
    }

    @Override
    public List<FileSample> findByParentOrderByPatchIndexAsc(Integer id) {
        return fileSampleRepository.findByParentOrderByPatchIndexAsc(id);
    }

    @Override
    public int updateByIdSetPathAndSize(Integer parent, String path, Long total) {
        FileSample fileSample=new FileSample(parent,null,null,null,path,null,total,null);
        return this.updateById(fileSample);
    }

    @Override
    public FileSample findByMd5(String md5) {
        return fileSampleRepository.findByMd5(md5);
    }

    /**
     * 获取本人有权限下载的文件
     * @param screenShareFile
     * @param request
     * @return
     */
    @Override
    public Map ScreenALL(ScreenShareFile screenShareFile, HttpServletRequest request) {
//        //默认匹配器:字符串采用精准查询,忽略大小写(文档说不忽略大小写，本人测试时发现是忽略大小写的)
//        ExampleMatcher matcher = ExampleMatcher.matching()
//                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)  //改变默认字符串匹配为:模糊查询
//                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())  //name字段模糊匹配
//                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.startsWith())  //name字段开头模糊匹配
//                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.endsWith())  //name字段结尾模糊匹配
//                .withIgnorePaths("patch_index","parent","path","md5","size","create_time");//忽略属性：是否关注。因为是基本类型，需要忽略掉
//        //创建实例
//        Example<FileSample> example = Example.of(screenShareFile.getFileSample(), matcher);
        HttpSession session = request.getSession();
        Map json = new HashMap();
        if (session != null && session.getAttribute("user") != null) {
            SessionUser sessionUser = new SessionUser();
            sessionUser = (SessionUser) session.getAttribute("user");
            Integer shareIdent = (sessionUser.getLogintype() == 0 ? 0 : sessionUser.getTeacher().getTeaIdentity());
            Integer shareId = (sessionUser.getLogintype() == 0 ? sessionUser.getStudent().getStuId() : sessionUser.getTeacher().getTeaId());
            ;
            Sort sort;
            Pageable pageable;
            if (!screenShareFile.getSort().isEmpty()) {
                if (screenShareFile.getSort().equals("esc"))//升序
                    sort = Sort.by(screenShareFile.getSortName()).ascending();
                else
                    sort = Sort.by(screenShareFile.getSortName()).descending();
                pageable = PageRequest.of(screenShareFile.getPageIndex() , screenShareFile.getPageNumber(), sort);
            }
            pageable = PageRequest.of(screenShareFile.getPageIndex() , screenShareFile.getPageNumber());
            List<Integer> ShareFileIdList = new ArrayList<Integer>();
            //screenShareFile.getQueryLevel()==0;//查询所有
            if (shareIdent == 0) {//学生查询
                Integer stuGroupid = studentRepository.findOneByStuId(shareId).getStuGroup();//小组id
                Integer classid= groupRepository.findOneById(stuGroupid).getId();//班级id
                Integer gradeid= cclassRepository.findOneById(classid).getGradeId();//院系id
                Integer year=gradeRepository.findOneById(gradeid).getYear();//学年
                //List<Integer> gradeids = gradeRepository.findIdsByYear(year);
                if (screenShareFile.getQueryLevel() == 1 || screenShareFile.getQueryLevel() == 0)  //查询权限开给学年(所在学年){}
                    ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareId(1, year));
                if (screenShareFile.getQueryLevel() == 2 || screenShareFile.getQueryLevel() == 0) //查询权限开给院系(所在院系)
                    ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareId(2, gradeid));
                if (screenShareFile.getQueryLevel() == 3 || screenShareFile.getQueryLevel() == 0) //查询权限开给班级(所在班级)
                    ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareId(3, classid));
                if (screenShareFile.getQueryLevel() == 4 || screenShareFile.getQueryLevel() == 0) //查询权限开给小组(所在小组)
                    ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareId(4, stuGroupid));
                if (screenShareFile.getQueryLevel() == 5 || screenShareFile.getQueryLevel() == 0)  //查询权限开给学生
                        ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareId(5, shareId));
                } else if (shareIdent == 1) {//老师查询
                    List<Integer> clases=teacherBindCclassRepository.findCclasesIdByTeaid(shareId); //授课班级组
                    List<Integer> grades=cclassRepository.findGradeIdsByIdIn(clases);//授课院系组
                    List<Integer> years = gradeRepository.findYearsByIdIn(grades);//授课学年组
                    if (screenShareFile.getQueryLevel() == 1 || screenShareFile.getQueryLevel() == 0) {//查询权限开给学年
                        if (screenShareFile.getScreenLevel() == 0)//任课班级的院系所有学年
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(1, years));
                        else
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareId(1, screenShareFile.getIssistId()));
                    }
                    if (screenShareFile.getQueryLevel() == 2 || screenShareFile.getQueryLevel() == 0) {//查询权限开给院系(所在院系)
                        if (screenShareFile.getScreenLevel() == 0)//0-所有任课班级-所有院系-所有学年
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(2,grades));
                        else if (screenShareFile.getScreenLevel() == 1)//1-所有任课班级-所有院系-某一学年
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(2, gradeRepository.findIdByIdInAndYear(grades, screenShareFile.getIssistId())));
                        else if (screenShareFile.getScreenLevel() == 2)//2-所有任课班级-某一院系
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareId(2, screenShareFile.getIssistId()));
                    }
                    if (screenShareFile.getQueryLevel() == 3 || screenShareFile.getQueryLevel() == 0) {//查询权限开给班级
                        if (screenShareFile.getScreenLevel() == 0)//0-所有学年
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(3, clases));
                        else if (screenShareFile.getScreenLevel() == 1)//1-某一学年(学年)
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(3, cclassRepository.findIdsByIdInAndGradeIdIn(clases, gradeRepository.findIdsByYear(screenShareFile.getIssistId()))));
                        else if (screenShareFile.getScreenLevel() == 2)//2-某一院系
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(3, cclassRepository.findIdsByIdInAndGradeId(clases, screenShareFile.getIssistId())));
                        else if (screenShareFile.getScreenLevel() == 3)//2-某一班级
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareId(3, screenShareFile.getIssistId()));
                    }
                    if (screenShareFile.getQueryLevel() == 4 || screenShareFile.getQueryLevel() == 0) {//查询权限开给小组
                        if (screenShareFile.getScreenLevel() == 0)//0-所有学年
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(4, groupRepository.findALLIdByCclassIdIn(clases)));
                        else if (screenShareFile.getScreenLevel() == 1)//1-某一学年(学年)
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(4, groupRepository.findALLIdByCclassIdIn(cclassRepository.findIdsByIdInAndGradeIdIn(clases, gradeRepository.findIdsByYear(screenShareFile.getIssistId())))));
                        else if (screenShareFile.getScreenLevel() == 2)//2-某一院系
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(4, groupRepository.findALLIdByCclassIdIn(cclassRepository.findIdsByIdInAndGradeId(clases, screenShareFile.getIssistId()))));
                        else if (screenShareFile.getScreenLevel() == 3)//3-某一班级
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(4, groupRepository.findALLIdByCclassId(screenShareFile.getIssistId())));
                        else if (screenShareFile.getScreenLevel() == 4)//4-某一小组
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareId(4, screenShareFile.getIssistId()));
                    }
                    if (screenShareFile.getQueryLevel() == 5 || screenShareFile.getQueryLevel() == 0) {//查询权限开给学生
                        if (screenShareFile.getScreenLevel() == 0)//0-所有学年
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(5, studentRepository.findIdsByStuGroupIn(groupRepository.findALLIdByCclassIdIn(clases))));
                        else if (screenShareFile.getScreenLevel() == 1)//1-某一学年
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(5, studentRepository.findIdsByStuGroupIn(groupRepository.findALLIdByCclassIdIn(cclassRepository.findIdsByIdInAndGradeIdIn(clases, gradeRepository.findIdsByYear(screenShareFile.getIssistId()))))));
                        else if (screenShareFile.getScreenLevel() == 2)//2-某一院系
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(5, studentRepository.findIdsByStuGroupIn(groupRepository.findALLIdByCclassIdIn(cclassRepository.findIdsByIdInAndGradeId(clases, screenShareFile.getIssistId())))));
                        else if (screenShareFile.getScreenLevel() == 3)//3-某一班级
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(5, studentRepository.findIdsByStuGroupIn(groupRepository.findALLIdByCclassId(screenShareFile.getIssistId()))));
                        else if (screenShareFile.getScreenLevel() == 4)//4-某一小组
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(5, studentRepository.findIdsByStuGroup(screenShareFile.getIssistId())));
                        else if (screenShareFile.getScreenLevel() == 5)//5-某一学生
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareId(5, screenShareFile.getIssistId()));
                    }
                    if (screenShareFile.getQueryLevel() == 6 || screenShareFile.getQueryLevel() == 0) {//查询权限开给老师
                        List<Integer>  teas=teacherBindCclassRepository.findCclasesIdByTeaid(shareId);//所在班级所有老师
                        if (screenShareFile.getScreenLevel() == 0)//0-所有学年
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(6, teacherBindCclassRepository.findIdsByTeaCclasesIdIn(teas)));
                        else if (screenShareFile.getScreenLevel() == 1)//1-某一学年
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(6, teacherBindCclassRepository.findIdsByTeaCclasesIdIn(cclassRepository.findIdsByIdInAndGradeIdIn(teas, gradeRepository.findIdsByYear(screenShareFile.getIssistId())))));
                        else if (screenShareFile.getScreenLevel() == 2)//2-某一院系
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(6, teacherBindCclassRepository.findIdsByTeaCclasesIdIn(cclassRepository.findIdsByIdInAndGradeId(teas, screenShareFile.getIssistId()))));
                        else if (screenShareFile.getScreenLevel() == 3)//2-某一班级
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(6, teacherBindCclassRepository.findIdsByTeaCclasesId(screenShareFile.getIssistId())));
                        else if (screenShareFile.getScreenLevel() == 6)
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareId(6, screenShareFile.getIssistId()));
                    }
                } else if (shareIdent == 2 || shareIdent == 3) {//老师兼管理员查询
                List<Integer> classes=cclassRepository.findAllId();//所有班级id
                List<Integer> grades=gradeRepository.findAllId();//所有院系
                    if (screenShareFile.getQueryLevel() == 1 || screenShareFile.getQueryLevel() == 0) {//查询权限开给学年
                        if (screenShareFile.getScreenLevel() == 0)//任课班级的院系所有学年
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(0, gradeRepository.findYearsByIdIn(grades)));
                        else
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareId(0, screenShareFile.getIssistId()));
                    }
                    if (screenShareFile.getQueryLevel() == 2 || screenShareFile.getQueryLevel() == 0) {//查询权限开给院系(所有院系)
                        if (screenShareFile.getScreenLevel() == 0)//0-所有任课班级-所有院系-所有学年
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(2, grades));
                        else if (screenShareFile.getScreenLevel() == 1)//1-所有任课班级-所有院系-某一学年
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(2, gradeRepository.findIdByIdInAndYear(grades, screenShareFile.getIssistId())));
                        else if (screenShareFile.getScreenLevel() == 2)//2-所有任课班级-某一院系
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareId(2, screenShareFile.getIssistId()));
                    }
                    if (screenShareFile.getQueryLevel() == 3 || screenShareFile.getQueryLevel() == 0) {//查询权限开给班级
                        if (screenShareFile.getScreenLevel() == 0)//0-所有学年
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(3, classes));
                        else if (screenShareFile.getScreenLevel() == 1)//1-某一学年(学年)
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(3, cclassRepository.findIdsByGradeIdIn(gradeRepository.findIdsByYear(screenShareFile.getIssistId()))));
                        else if (screenShareFile.getScreenLevel() == 2)//2-某一院系
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(3, cclassRepository.findIdsByGradeId(screenShareFile.getIssistId())));
                        else if (screenShareFile.getScreenLevel() == 3)//2-某一班级
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareId(3, screenShareFile.getIssistId()));
                    }
                    if (screenShareFile.getQueryLevel() == 4 || screenShareFile.getQueryLevel() == 0) {//查询权限开给小组
                        if (screenShareFile.getScreenLevel() == 0)//0-所有学年
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(4, groupRepository.findALLIdByCclassIdIn(classes)));
                        else if (screenShareFile.getScreenLevel() == 1)//1-某一学年(学年)
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(4, groupRepository.findALLIdByCclassIdIn(cclassRepository.findIdsByIdInAndGradeIdIn(classes, gradeRepository.findIdsByYear(screenShareFile.getIssistId())))));
                        else if (screenShareFile.getScreenLevel() == 2)//2-某一院系
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(4, groupRepository.findALLIdByCclassIdIn(cclassRepository.findIdsByIdInAndGradeId(classes, screenShareFile.getIssistId()))));
                        else if (screenShareFile.getScreenLevel() == 3)//3-某一班级
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(4, groupRepository.findALLIdByCclassId(screenShareFile.getIssistId())));
                        else if (screenShareFile.getScreenLevel() == 4)//4-某一小组
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareId(4, screenShareFile.getIssistId()));
                    }
                    if (screenShareFile.getQueryLevel() == 5 || screenShareFile.getQueryLevel() == 0) {//查询权限开给学生
                        if (screenShareFile.getScreenLevel() == 0)//0-所有学年
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(5, studentRepository.findIdsByStuGroupIn(groupRepository.findALLIdByCclassIdIn(classes))));
                        else if (screenShareFile.getScreenLevel() == 1)//1-某一学年
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(5, studentRepository.findIdsByStuGroupIn(groupRepository.findALLIdByCclassIdIn(cclassRepository.findIdsByIdInAndGradeIdIn(classes, gradeRepository.findIdsByYear(screenShareFile.getIssistId()))))));
                        else if (screenShareFile.getScreenLevel() == 2)//2-某一院系
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(5, studentRepository.findIdsByStuGroupIn(groupRepository.findALLIdByCclassIdIn(cclassRepository.findIdsByIdInAndGradeId(classes, screenShareFile.getIssistId())))));
                        else if (screenShareFile.getScreenLevel() == 3)//3-某一班级
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(5, studentRepository.findIdsByStuGroupIn(groupRepository.findALLIdByCclassId(screenShareFile.getIssistId()))));
                        else if (screenShareFile.getScreenLevel() == 4)//4-某一小组
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(5, studentRepository.findIdsByStuGroup(screenShareFile.getIssistId())));
                        else if (screenShareFile.getScreenLevel() == 5)//5-某一学生
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareId(5, screenShareFile.getIssistId()));
                    }
                    if (screenShareFile.getQueryLevel() == 6 || screenShareFile.getQueryLevel() == 0) {//查询权限开给老师
                        if (screenShareFile.getScreenLevel() == 0)//0-所有学年
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(6, teacherBindCclassRepository.findIdsByTeaCclasesIdIn(classes)));
                        else if (screenShareFile.getScreenLevel() == 1)//1-某一学年
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(6, teacherBindCclassRepository.findIdsByTeaCclasesIdIn(cclassRepository.findIdsByIdInAndGradeIdIn(classes, gradeRepository.findIdsByYear(screenShareFile.getIssistId())))));
                        else if (screenShareFile.getScreenLevel() == 2)//2-某一院系
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(6, teacherBindCclassRepository.findIdsByTeaCclasesIdIn(cclassRepository.findIdsByIdInAndGradeId(classes, screenShareFile.getIssistId()))));
                        else if (screenShareFile.getScreenLevel() == 3)//2-某一班级
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(6, teacherBindCclassRepository.findIdsByTeaCclasesId(screenShareFile.getIssistId())));
                        else if (screenShareFile.getScreenLevel() == 6) {//某个老师
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareId(6, screenShareFile.getIssistId()));
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareId(7, screenShareFile.getIssistId()));
                        }
                    }
                    if (screenShareFile.getQueryLevel() == 7 || screenShareFile.getQueryLevel() == 0) {//查询权限开给管理7
                        //if (screenShareFile.getScreenLevel() == 0)//0-所有管理员
                           // ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareId(7, ));
                       // else
                            ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareId(7, screenShareFile.getIssistId()));
                    }
                    }
                ShareFileIdList = new ArrayList<Integer>(new LinkedHashSet<Integer>(ShareFileIdList));//去掉重复

                json.put("resoult", true);
                List<Integer> FileIdList = shareFileRepository.findFileIdByIdIn(ShareFileIdList);
                if (screenShareFile.getLikeName().isEmpty())
                    json.put("page", fileSampleRepository.findALLByIdIn(FileIdList, pageable));
                else
                    json.put("page", fileSampleRepository.findALLByIdInAndNameLike(FileIdList, "%" + screenShareFile.getLikeName() + "%", pageable));
        } else {
            json.put("resoult", false);
        }
        return json;
    }
    /**
     * 获取用户共享出去的文件
     * @param screenShareFile
     * @param request
     * @return
     */
    @Override
    public Map ScreenMyALL(ScreenShareFile screenShareFile,HttpServletRequest request) {

        HttpSession session = request.getSession();
        Map json = new HashMap();
        if(session!=null&&session.getAttribute("user") != null){
            SessionUser sessionUser=new SessionUser();
            sessionUser = (SessionUser) session.getAttribute("user");
            Integer shareIdent=(sessionUser.getLogintype()==0?0:sessionUser.getTeacher().getTeaIdentity());
            Integer shareId=(sessionUser.getLogintype()==0?sessionUser.getStudent().getStuId():sessionUser.getTeacher().getTeaId());;
            Sort sort;
            Pageable pageable;
            if (!screenShareFile.getSort().isEmpty()){
                if (screenShareFile.getSort().equals("esc"))//升序
                    sort=Sort.by(screenShareFile.getSortName()).ascending();
                else
                    sort=Sort.by(screenShareFile.getSortName()).descending();
                pageable = PageRequest.of(screenShareFile.getPageIndex(),screenShareFile.getPageNumber(),sort);
            }
            pageable = PageRequest.of(screenShareFile.getPageIndex(),screenShareFile.getPageNumber());
            List<Integer> ShareFileIdList=new ArrayList<Integer>();
            //screenShareFile.getQueryLevel()==0;//查询所有
            List<Integer> classes=cclassRepository.findAllId();//所有班级id
            List<Integer> grades=gradeRepository.findAllId();//所有院系

            if (screenShareFile.getQueryLevel() == 1 || screenShareFile.getQueryLevel() == 0) {//查询权限开给学年
                if (screenShareFile.getScreenLevel() == 0)//任课班级的院系所有学年
                    ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(1, gradeRepository.findYearsByIdIn(grades)));
                else
                    ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareId(1, screenShareFile.getIssistId()));
            }
            if (screenShareFile.getQueryLevel() == 2 || screenShareFile.getQueryLevel() == 0) {//查询权限开给院系(所有院系)
                if (screenShareFile.getScreenLevel() == 0)//0-所有任课班级-所有院系-所有学年
                    ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(2, grades));
                else if (screenShareFile.getScreenLevel() == 1)//1-所有任课班级-所有院系-某一学年
                    ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(2, gradeRepository.findIdByIdInAndYear(grades, screenShareFile.getIssistId())));
                else if (screenShareFile.getScreenLevel() == 2)//2-所有任课班级-某一院系
                    ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareId(2, screenShareFile.getIssistId()));
            }
            if (screenShareFile.getQueryLevel() == 3 || screenShareFile.getQueryLevel() == 0) {//查询权限开给班级
                if (screenShareFile.getScreenLevel() == 0)//0-所有学年
                    ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(3, classes));
                else if (screenShareFile.getScreenLevel() == 1)//1-某一学年(学年)
                    ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(3, cclassRepository.findIdsByGradeIdIn(gradeRepository.findIdsByYear(screenShareFile.getIssistId()))));
                else if (screenShareFile.getScreenLevel() == 2)//2-某一院系
                    ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(3, cclassRepository.findIdsByGradeId(screenShareFile.getIssistId())));
                else if (screenShareFile.getScreenLevel() == 3)//2-某一班级
                    ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareId(3, screenShareFile.getIssistId()));
            }
            if (screenShareFile.getQueryLevel() == 4 || screenShareFile.getQueryLevel() == 0) {//查询权限开给小组
                if (screenShareFile.getScreenLevel() == 0)//0-所有学年
                    ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(4, groupRepository.findALLIdByCclassIdIn(classes)));
                else if (screenShareFile.getScreenLevel() == 1)//1-某一学年(学年)
                    ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(4, groupRepository.findALLIdByCclassIdIn(cclassRepository.findIdsByIdInAndGradeIdIn(classes, gradeRepository.findIdsByYear(screenShareFile.getIssistId())))));
                else if (screenShareFile.getScreenLevel() == 2)//2-某一院系
                    ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(4, groupRepository.findALLIdByCclassIdIn(cclassRepository.findIdsByIdInAndGradeId(classes, screenShareFile.getIssistId()))));
                else if (screenShareFile.getScreenLevel() == 3)//3-某一班级
                    ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(4, groupRepository.findALLIdByCclassId(screenShareFile.getIssistId())));
                else if (screenShareFile.getScreenLevel() == 4)//4-某一小组
                    ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareId(4, screenShareFile.getIssistId()));
            }
            if (screenShareFile.getQueryLevel() == 5 || screenShareFile.getQueryLevel() == 0) {//查询权限开给学生
                if (screenShareFile.getScreenLevel() == 0)//0-所有学年
                    ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(5, studentRepository.findIdsByStuGroupIn(groupRepository.findALLIdByCclassIdIn(classes))));
                else if (screenShareFile.getScreenLevel() == 1)//1-某一学年
                    ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(5, studentRepository.findIdsByStuGroupIn(groupRepository.findALLIdByCclassIdIn(cclassRepository.findIdsByIdInAndGradeIdIn(classes, gradeRepository.findIdsByYear(screenShareFile.getIssistId()))))));
                else if (screenShareFile.getScreenLevel() == 2)//2-某一院系
                    ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(5, studentRepository.findIdsByStuGroupIn(groupRepository.findALLIdByCclassIdIn(cclassRepository.findIdsByIdInAndGradeId(classes, screenShareFile.getIssistId())))));
                else if (screenShareFile.getScreenLevel() == 3)//3-某一班级
                    ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(5, studentRepository.findIdsByStuGroupIn(groupRepository.findALLIdByCclassId(screenShareFile.getIssistId()))));
                else if (screenShareFile.getScreenLevel() == 4)//4-某一小组
                    ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(5, studentRepository.findIdsByStuGroup(screenShareFile.getIssistId())));
                else if (screenShareFile.getScreenLevel() == 5)//5-某一学生
                    ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareId(5, screenShareFile.getIssistId()));
            }
            if (screenShareFile.getQueryLevel() == 6 || screenShareFile.getQueryLevel() == 0) {//查询权限开给老师
                if (screenShareFile.getScreenLevel() == 0)//0-所有学年
                    ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(6, teacherBindCclassRepository.findIdsByTeaCclasesIdIn(classes)));
                else if (screenShareFile.getScreenLevel() == 1)//1-某一学年
                    ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(6, teacherBindCclassRepository.findIdsByTeaCclasesIdIn(cclassRepository.findIdsByIdInAndGradeIdIn(classes, gradeRepository.findIdsByYear(screenShareFile.getIssistId())))));
                else if (screenShareFile.getScreenLevel() == 2)//2-某一院系
                    ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(6, teacherBindCclassRepository.findIdsByTeaCclasesIdIn(cclassRepository.findIdsByIdInAndGradeId(classes, screenShareFile.getIssistId()))));
                else if (screenShareFile.getScreenLevel() == 3)//2-某一班级
                    ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareIdIn(6, teacherBindCclassRepository.findIdsByTeaCclasesId(screenShareFile.getIssistId())));
                else if (screenShareFile.getScreenLevel() == 6) {//查询权限开给老师
                    ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareId(6, screenShareFile.getIssistId()));
                    ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareId(7, screenShareFile.getIssistId()));
                }
            }
            if (screenShareFile.getQueryLevel() == 7 || screenShareFile.getQueryLevel() == 0) {//查询权限开给管理员
                ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareId(7, screenShareFile.getIssistId()));
                ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareId(8, screenShareFile.getIssistId()));
            }
            ShareFileIdList=new ArrayList<Integer>(new LinkedHashSet<Integer>(ShareFileIdList));//去掉重复
            json.put("resoult", true);
            List<Integer> FileIdList=shareFileRepository.findFileIdByIdInAndOwnerIdentAndownerId(ShareFileIdList,shareIdent,shareId);//

            if (screenShareFile.getLikeName().isEmpty())
                json.put("page",fileSampleRepository.findALLByIdIn(FileIdList,pageable));
            else
                json.put("page",fileSampleRepository.findALLByIdInAndNameLike(FileIdList,"%"+screenShareFile.getLikeName()+"%",pageable));
        }else{
            json.put("resoult", false);
        }
        return  json;
    }
}
