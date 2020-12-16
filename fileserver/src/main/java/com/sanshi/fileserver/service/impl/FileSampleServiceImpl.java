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
import java.util.*;


@Service("FileSampleServiceImpl")
public class FileSampleServiceImpl implements FileSampleService {
    private FileSampleRepository fileSampleRepository;
    private ShareRightRepository shareRightRepository;
    private ShareFileRepository shareFileRepository;
    private CclassRepository cclassRepository;
    private StudentRepository studentRepository;
    private GroupRepository groupRepository;

    public FileSampleServiceImpl(FileSampleRepository fileSampleRepository, ShareRightRepository shareRightRepository, ShareFileRepository shareFileRepository, CclassRepository cclassRepository, GroupRepository groupRepository) {
        this.fileSampleRepository = fileSampleRepository;
        this.shareRightRepository = shareRightRepository;
        this.shareFileRepository = shareFileRepository;
        this.cclassRepository = cclassRepository;
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
        if(session!=null&&session.getAttribute("user") != null){
            SessionUser sessionUser=new SessionUser();
            sessionUser = (SessionUser) session.getAttribute("user");
            Integer shareIdent=sessionUser.getIdent();
            Integer shareId=sessionUser.getUserId();
            Sort sort;
            Pageable pageable;
            if (screenShareFile.getSort()!=null){
                if (screenShareFile.getSort().equals("esc"))//升序
                    sort=Sort.by(screenShareFile.getSortName()).ascending();
                else
                    sort=Sort.by(screenShareFile.getSortName()).descending();
                pageable = PageRequest.of(screenShareFile.getPageIndex()-1,screenShareFile.getPageNumber(),sort);
            }
            pageable = PageRequest.of(screenShareFile.getPageIndex()-1,screenShareFile.getPageNumber());
            List<Integer> ShareFileIdList=new ArrayList<Integer>();
            //screenShareFile.getQueryLevel()==0;//查询所有
            if (shareIdent==0) {//学生
                if (screenShareFile.getQueryLevel() == 1 || screenShareFile.getQueryLevel() == 0) {//查询权限开给班级

                }
                if (screenShareFile.getQueryLevel() == 2 || screenShareFile.getQueryLevel() == 0) {//查询权限开给小组

                }
                if (screenShareFile.getQueryLevel() == 3 || screenShareFile.getQueryLevel() == 0) {//查询权限开给学生
                    ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareId(shareIdent, shareId));//个人的
                }
            }
            ShareFileIdList=new ArrayList<Integer>(new LinkedHashSet<Integer>(ShareFileIdList));//去掉重复

            json.put("resoult", true);
            List<Integer> FileIdList=shareFileRepository.findFileIdByIdIn(ShareFileIdList);
            if (screenShareFile.getLikeName().isEmpty())
                json.put("page",fileSampleRepository.findALLByIdIn(FileIdList,pageable));
            else
                json.put("page",fileSampleRepository.findALLByIdInAndNameLike(FileIdList,"%"+screenShareFile.getLikeName()+"%",pageable));
        }else{
            json.put("resoult", false);
        }
        return  json;


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
            Integer shareIdent=sessionUser.getIdent();
            Integer shareId=sessionUser.getUserId();
            Sort sort;
            Pageable pageable;
            if (screenShareFile.getSort()!=null){
                if (screenShareFile.getSort().equals("esc"))//升序
                    sort=Sort.by(screenShareFile.getSortName()).ascending();
                else
                    sort=Sort.by(screenShareFile.getSortName()).descending();
                pageable = PageRequest.of(screenShareFile.getPageIndex()-1,screenShareFile.getPageNumber(),sort);
            }
            pageable = PageRequest.of(screenShareFile.getPageIndex()-1,screenShareFile.getPageNumber());
            List<Integer> ShareFileIdList=new ArrayList<Integer>();
            //screenShareFile.getQueryLevel()==0;//查询所有

            if (screenShareFile.getQueryLevel() == 1 || screenShareFile.getQueryLevel() == 0) {//查询权限开给班级
                    if (shareIdent==0){//学生查询
                        ShareFileIdList.addAll(shareRightRepository.findIdByShareIdentAndShareId(1,cclassRepository.findOneById(groupRepository.findOneById(studentRepository.findOneById(shareId).getStuGroup()).getId()).getId()));
                    }else if(shareIdent==0){
                       // ShareFileIdList.addAll(
                    }

            }
            if (screenShareFile.getQueryLevel() == 2 || screenShareFile.getQueryLevel() == 0) {//查询权限开给小组

            }
            if (screenShareFile.getQueryLevel() == 3 || screenShareFile.getQueryLevel() == 0) {//查询权限开给学生
                ShareFileIdList.addAll(shareRightRepository.findIdByShareIdent(0));
            }
            if (screenShareFile.getQueryLevel() == 4 || screenShareFile.getQueryLevel() == 0) {//查询权限开给老师
                ShareFileIdList.addAll(shareRightRepository.findIdByShareIdent(1));
            }
            if (screenShareFile.getQueryLevel() == 5 || screenShareFile.getQueryLevel() == 0) {//查询权限开给管理员/老师
                ShareFileIdList.addAll(shareRightRepository.findIdByShareIdent(2));
            }
            if (screenShareFile.getQueryLevel() == 6 || screenShareFile.getQueryLevel() == 0) {//查询权限开给管理员
                ShareFileIdList.addAll(shareRightRepository.findIdByShareIdent(3));
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
