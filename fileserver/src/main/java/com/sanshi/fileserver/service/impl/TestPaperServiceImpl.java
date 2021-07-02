package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.*;
import com.sanshi.fileserver.repository.*;
import com.sanshi.fileserver.service.TestPaperService;
import com.sanshi.fileserver.vo.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service("TestPaperServiceImpl")
public class TestPaperServiceImpl implements TestPaperService {
    private TestPaperRepository testPaperRepository;
    private AssessRepository assessRepository;
    private RespondentsRepository respondentsRepository;
    private TestPaperBindChoiceRepository testPaperBindChoiceRepository;
    private AnswerRepository answerRepository;
    private ChoiceRepository choiceRepository;
    private SubjectRepository subjectRepository;
    private TeacherRepository teacherRepository;
    private HttpSession session;

    public TestPaperServiceImpl(TestPaperRepository testPaperRepository, AssessRepository assessRepository, RespondentsRepository respondentsRepository, TestPaperBindChoiceRepository testPaperBindChoiceRepository, AnswerRepository answerRepository, ChoiceRepository choiceRepository, SubjectRepository subjectRepository, TeacherRepository teacherRepository, HttpSession session) {
        this.testPaperRepository = testPaperRepository;
        this.assessRepository = assessRepository;
        this.respondentsRepository = respondentsRepository;
        this.testPaperBindChoiceRepository = testPaperBindChoiceRepository;
        this.answerRepository = answerRepository;
        this.choiceRepository = choiceRepository;
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
        this.session = session;
    }

    @Override
    public TestPaperVo Exam(Integer assessId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("user") != null) {
            SessionUser sessionUser = new SessionUser();
            sessionUser = (SessionUser) session.getAttribute("user");
            Integer logintype = (sessionUser.getLogintype() == 0 ? 0 : sessionUser.getTeacher().getTeaIdentity());
            Integer userId = (sessionUser.getLogintype() == 0 ? sessionUser.getStudent().getStuId() : sessionUser.getTeacher().getTeaId());
            if (logintype == 0) {
                TestPaperVo testPaperVo = new TestPaperVo();
                testPaperVo.setAssess(assessRepository.findOneById(assessId));//考核信息
                //答卷信息
                testPaperVo.setRespondents(respondentsRepository.findAllByAssessIdAndStuId(assessId, userId).size() == 0 ? respondentsRepository.save(new Respondents(null, assessId, userId, 0, 1, 0, null, null, null)) : respondentsRepository.findAllByAssessIdAndStuId(assessId, userId).get(0));
                testPaperVo.setTestPaper(testPaperRepository.findOneById(testPaperVo.getAssess().getTestPaperId()));//获取试卷信息
                List<TestPaperBindChoice> testPaperBindChoicesList = testPaperBindChoiceRepository.findAllByTestPaperIdOrderByIndexNumAsc(testPaperVo.getAssess().getTestPaperId());//获取试题集合
                List<MakeChoice> makeChoicesList = new ArrayList<MakeChoice>();
                for (int i = 0; i < testPaperBindChoicesList.size(); i++) {
                    makeChoicesList.add(new MakeChoice(testPaperBindChoicesList.get(i).getIndexNum(), testPaperBindChoicesList.get(i).getScore(), choiceRepository.findOneById(testPaperBindChoicesList.get(i).getChoiceId()), answerRepository.findOneByChoiceIdAndRespondentsId(testPaperBindChoicesList.get(i).getChoiceId(), testPaperVo.getRespondents().getId()) == null ? answerRepository.save(new Answer(null, testPaperBindChoicesList.get(i).getChoiceId(), testPaperVo.getRespondents().getId(), null, null, 0.0, 0, null, null, null)) : answerRepository.findOneByChoiceIdAndRespondentsId(testPaperBindChoicesList.get(i).getChoiceId(), testPaperVo.getRespondents().getId())));
                }
                Collections.sort(makeChoicesList);
                testPaperVo.setMakeChoiceList(makeChoicesList);
                return testPaperVo;
            }
            return null;
        }
        return null;
    }

    @Override
    public ReadTestPaper read(Integer testPaperId) {
        ReadTestPaper readTestPaper = new ReadTestPaper();
        readTestPaper.setTestPaper(testPaperRepository.findOneById(testPaperId));
        List<ChoiceDetails> choices = new ArrayList<ChoiceDetails>();
        List<TestPaperBindChoice> testPaperBindChoicesList = testPaperBindChoiceRepository.findAllByTestPaperIdOrderByIndexNumAsc(testPaperId);//获取试题bind集合
        for (int i = 0; i < testPaperBindChoicesList.size(); i++) {
            choices.add(new ChoiceDetails(testPaperBindChoicesList.get(i), choiceRepository.findOneById(testPaperBindChoicesList.get(i).getChoiceId())));
        }
        Collections.sort(choices);
        readTestPaper.setChoices(choices);
        return readTestPaper;
    }

    @Override
    public TestPaper findOneById(Integer id) {
        return testPaperRepository.findOneById(id);
    }

    @Override
    public Map findAll(PageGet val) {
        Sort sort = null;
        sort =  Sort.by(Sort.Direction.DESC,"createTime");
        Pageable pageable;
        pageable = PageRequest.of(val.getPageIndex(), val.getPageNumber(),sort);
        Map json = new HashMap();
        json.put("resoult", true);
        if (val.getLikeName().isEmpty())
            val.setLikeName("");
        if (val.getIssistId() != 0) {//科目
            json.put("page", testPaperRepository.findAllBySubIdAndNameLike(val.getIssistId(), "%" + val.getLikeName() + "%", pageable));
        } else {
            json.put("page", testPaperRepository.findAllByNameLike("%" + val.getLikeName() + "%", pageable));
        }
        return json;
    }

    @Override
    public TestPaper save(TestPaper testPaper) {
        return testPaperRepository.save(testPaper);
    }

    @Transactional
    @Override
    public Result deleteById(Integer testPaperId) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        TestPaper testPaper = testPaperRepository.findOneById(testPaperId);
        List<Assess> list = assessRepository.findAllByTestPaperId(testPaper.getId());
        if (sessionUser.getTeacher().getTeaIdentity() != 2) {
            if (sessionUser.getTeacher().getTeaId() != testPaper.getCreationId()) {
                return new Result(false, "您不是该试卷的所有者删除失败");
            }
        }
        if (list.size() > 0) {
            return new Result(false, "该试卷有考核任务您不能删除");
        }
        testPaperRepository.deleteById(testPaperId);
        testPaperBindChoiceRepository.deleteByTestPaperId(testPaper.getId());
        return new Result(true, "删除成功");
    }

    @Override
    public TestPaperMsg findMsg(Integer id) {
        TestPaper testPaper = testPaperRepository.findOneById(id);
        String name = teacherRepository.findOneByTeaId(testPaper.getCreationId()).getTeaName();
        Double scoreSum = testPaperBindChoiceRepository.findScoreSum(id);
        if (scoreSum == null) scoreSum = 0.0;
        return new TestPaperMsg(testPaperBindChoiceRepository.findAllByTestPaperIdOrderByIndexNumAsc(id).size(), scoreSum, subjectRepository.findOneById(testPaper.getSubId()).getName(), teacherRepository.findOneByTeaId(testPaper.getCreationId()).getTeaName());
    }

    //自动生成试卷
    @Transactional
    @Override
    public Result generateTestPaper(TestPaperUtils testPaperUtils) {
        //获取试卷生成难度

        SessionUser sessionUser = (SessionUser) session.getAttribute("user");

        TestPaper testPaper = testPaperRepository.findOneByName(testPaperUtils.getTestPaperName());
        if (testPaper != null) {
            return new Result(false, "试卷名称重复请更改");
        }

        List<List<Choice>> list = new ArrayList<List<Choice>>();
        switch (testPaperUtils.getDifficulty()) {
            case 1:     //简单  %90的 一至二星 的题  10%的 三星四星题
                //单选题
                if (testPaperUtils.getRodSum() > 0) {
                    Double a = Math.floor(testPaperUtils.getRodSum() * 0.9);
                    List<Choice> choiceList = choiceRepository.findAllByDifficultyLevelInAndTypeAndSubId(new Integer[]{1, 2}, 1, testPaperUtils.getSubId());
                    if (choiceList.size() < a) {
                        return new Result(false, "单选题一星二星难度数量不足");
                    }
                    list.add(TestPaperUtils.red(choiceList, a.intValue()));
                    int a1 = testPaperUtils.getRodSum() - a.intValue();
                    List<Choice> choiceList2 = choiceRepository.findAllByDifficultyLevelInAndTypeAndSubId(new Integer[]{3, 4}, 1, testPaperUtils.getSubId());
                    if (choiceList2.size() < a1) {
                        return new Result(false, "单选题三星四星难度数量不足");
                    }
                    list.add(TestPaperUtils.red(choiceList2, a1));
                }
                //多选题
                if (testPaperUtils.getCheckSum() > 0) {
                    Double b = Math.floor(testPaperUtils.getCheckSum() * 0.9);
                    int b1 = testPaperUtils.getCheckSum() - b.intValue();
                    List<Choice> choiceList3 = choiceRepository.findAllByDifficultyLevelInAndTypeAndSubId(new Integer[]{1, 2}, 2, testPaperUtils.getSubId());
                    if (choiceList3.size() < b) {
                        return new Result(false, "多选题一星二星难度数量不足");
                    }
                    list.add(TestPaperUtils.red(choiceList3, b.intValue()));

                    List<Choice> choiceList4 = choiceRepository.findAllByDifficultyLevelInAndTypeAndSubId(new Integer[]{3, 4}, 2, testPaperUtils.getSubId());
                    if (choiceList4.size() < b1) {
                        return new Result(false, "多选题三星四星难度数量不足");
                    }
                    list.add(TestPaperUtils.red(choiceList4, b1));
                }
                if (testPaperUtils.getJudgeSum() > 0) {
                    //判断题
                    Double c = Math.floor(testPaperUtils.getJudgeSum() * 0.9);
                    int c1 = testPaperUtils.getJudgeSum() - c.intValue();
                    List<Choice> choiceList5 = choiceRepository.findAllByDifficultyLevelInAndTypeAndSubId(new Integer[]{1, 2}, 3, testPaperUtils.getSubId());
                    if (choiceList5.size() < c) {
                        return new Result(false, "判断题一星二星难度数量不足");
                    }
                    list.add(TestPaperUtils.red(choiceList5, c.intValue()));

                    List<Choice> choiceList6 = choiceRepository.findAllByDifficultyLevelInAndTypeAndSubId(new Integer[]{3, 4}, 3, testPaperUtils.getSubId());
                    if (choiceList6.size() < c1) {
                        return new Result(false, "判断题三星四星难度数量不足");
                    }
                    list.add(TestPaperUtils.red(choiceList6, c1));
                }
                //简答题
                if (testPaperUtils.getAnswerSum() > 0) {
                    Double d = Math.floor(testPaperUtils.getAnswerSum() * 0.9);
                    int d1 = testPaperUtils.getAnswerSum() - d.intValue();
                    List<Choice> choiceList7 = choiceRepository.findAllByDifficultyLevelInAndTypeAndSubId(new Integer[]{1, 2}, 4, testPaperUtils.getSubId());
                    if (choiceList7.size() < d) {
                        return new Result(false, "简答题一星二星难度数量不足");
                    }
                    list.add(TestPaperUtils.red(choiceList7, d.intValue()));

                    List<Choice> choiceList8 = choiceRepository.findAllByDifficultyLevelInAndTypeAndSubId(new Integer[]{3, 4}, 4, testPaperUtils.getSubId());
                    if (choiceList8.size() < d1) {
                        return new Result(false, "简答题三星四星难度数量不足");
                    }
                    list.add(TestPaperUtils.red(choiceList8, d1));
                }


                //填空题
                if (testPaperUtils.getGapSum() > 0) {
                    Double e = Math.floor(testPaperUtils.getGapSum() * 0.9);
                    int d1 = testPaperUtils.getGapSum() - e.intValue();
                    List<Choice> List7 = choiceRepository.findAllByDifficultyLevelInAndTypeAndSubId(new Integer[]{1, 2}, 5, testPaperUtils.getSubId());
                    if (List7.size() < e) {
                        return new Result(false, "填空题一星二星难度数量不足");
                    }
                    list.add(TestPaperUtils.red(List7, e.intValue()));

                    List<Choice> List8 = choiceRepository.findAllByDifficultyLevelInAndTypeAndSubId(new Integer[]{3, 4}, 5, testPaperUtils.getSubId());
                    if (List8.size() < d1) {
                        return new Result(false, "填空题三星四星难度数量不足");
                    }
                    list.add(TestPaperUtils.red(List8, d1));
                }

                break;
            case 2:     //一般  %70的 一至三星题   30%的 四星五星题
                //单选题
                if (testPaperUtils.getRodSum() > 0) {
                    Double e = Math.floor(testPaperUtils.getRodSum() * 0.7);
                    List<Choice> choiceList9 = choiceRepository.findAllByDifficultyLevelInAndTypeAndSubId(new Integer[]{1, 2, 3}, 1, testPaperUtils.getSubId());
                    if (choiceList9.size() < e) {
                        return new Result(false, "单选题一星二星三星难度数量不足");
                    }
                    list.add(TestPaperUtils.red(choiceList9, e.intValue()));

                    int e1 = testPaperUtils.getRodSum() - e.intValue();
                    List<Choice> choiceList10 = choiceRepository.findAllByDifficultyLevelInAndTypeAndSubId(new Integer[]{4, 5}, 1, testPaperUtils.getSubId());
                    if (choiceList10.size() < e1) {
                        return new Result(false, "单选题四星五星难度数量不足");
                    }
                    list.add(TestPaperUtils.red(choiceList10, e1));
                }


                //多选题
                if (testPaperUtils.getCheckSum() > 0) {
                    Double f = Math.floor(testPaperUtils.getCheckSum() * 0.7);
                    int f1 = testPaperUtils.getCheckSum() - f.intValue();
                    List<Choice> choiceList11 = choiceRepository.findAllByDifficultyLevelInAndTypeAndSubId(new Integer[]{1, 2, 3}, 2, testPaperUtils.getSubId());
                    if (choiceList11.size() < f) {
                        return new Result(false, "多选题一星二星三星难度数量不足");
                    }
                    list.add(TestPaperUtils.red(choiceList11, f.intValue()));

                    List<Choice> choiceList12 = choiceRepository.findAllByDifficultyLevelInAndTypeAndSubId(new Integer[]{4, 5}, 2, testPaperUtils.getSubId());
                    if (choiceList12.size() < f1) {
                        return new Result(false, "多选题四星五星难度数量不足");
                    }
                    list.add(TestPaperUtils.red(choiceList12, f1));
                }
                if (testPaperUtils.getJudgeSum() > 0) {
                    //判断题
                    Double g = Math.floor(testPaperUtils.getJudgeSum() * 0.7);
                    int g1 = testPaperUtils.getJudgeSum() - g.intValue();
                    List<Choice> choiceList13 = choiceRepository.findAllByDifficultyLevelInAndTypeAndSubId(new Integer[]{1, 2, 3}, 3, testPaperUtils.getSubId());
                    if (choiceList13.size() < g) {
                        return new Result(false, "判断题一星二星三星难度数量不足");
                    }
                    list.add(TestPaperUtils.red(choiceList13, g.intValue()));

                    List<Choice> choiceList14 = choiceRepository.findAllByDifficultyLevelInAndTypeAndSubId(new Integer[]{4, 5}, 3, testPaperUtils.getSubId());
                    if (choiceList14.size() < g1) {
                        return new Result(false, "判断题四星五星难度数量不足");
                    }
                    list.add(TestPaperUtils.red(choiceList14, g1));
                }
                //简答题
                if (testPaperUtils.getAnswerSum() > 0) {
                    Double h = Math.floor(testPaperUtils.getAnswerSum() * 0.9);
                    int h1 = testPaperUtils.getAnswerSum() - h.intValue();
                    List<Choice> choiceList15 = choiceRepository.findAllByDifficultyLevelInAndTypeAndSubId(new Integer[]{1, 2, 3}, 4, testPaperUtils.getSubId());
                    if (choiceList15.size() < h) {
                        return new Result(false, "简答题一星二星三星难度数量不足");
                    }
                    list.add(TestPaperUtils.red(choiceList15, h.intValue()));

                    List<Choice> choiceList16 = choiceRepository.findAllByDifficultyLevelInAndTypeAndSubId(new Integer[]{4, 5}, 4, testPaperUtils.getSubId());
                    if (choiceList16.size() < h1) {
                        return new Result(false, "简答题四星五星难度数量不足");
                    }
                    list.add(TestPaperUtils.red(choiceList16, h1));
                }

                //填空题
                if (testPaperUtils.getGapSum() > 0) {
                    Double h = Math.floor(testPaperUtils.getGapSum() * 0.9);
                    int h1 = testPaperUtils.getGapSum() - h.intValue();
                    List<Choice> choiceList15 = choiceRepository.findAllByDifficultyLevelInAndTypeAndSubId(new Integer[]{1, 2, 3}, 5, testPaperUtils.getSubId());
                    if (choiceList15.size() < h) {
                        return new Result(false, "填空题一星二星三星难度数量不足");
                    }
                    list.add(TestPaperUtils.red(choiceList15, h.intValue()));

                    List<Choice> choiceList16 = choiceRepository.findAllByDifficultyLevelInAndTypeAndSubId(new Integer[]{4, 5}, 5, testPaperUtils.getSubId());
                    if (choiceList16.size() < h1) {
                        return new Result(false, "填空题四星五星难度数量不足");
                    }
                    list.add(TestPaperUtils.red(choiceList16, h1));
                }


                break;
            case 3:     //困难  %70的 三至四星题    %30的 五星题
                if (testPaperUtils.getRodSum() > 0) {
                    Double i1 = Math.floor(testPaperUtils.getRodSum() * 0.7);
                    List<Choice> choiceList18 = choiceRepository.findAllByDifficultyLevelInAndTypeAndSubId(new Integer[]{3, 4}, 1, testPaperUtils.getSubId());
                    if (choiceList18.size() < i1) {
                        return new Result(false, "单选题三星四星难度数量不足");
                    }
                    list.add(TestPaperUtils.red(choiceList18, i1.intValue()));

                    int i2 = testPaperUtils.getRodSum() - i1.intValue();
                    List<Choice> choiceList19 = choiceRepository.findAllByDifficultyLevelInAndTypeAndSubId(new Integer[]{5}, 1, testPaperUtils.getSubId());
                    if (choiceList19.size() < i2) {
                        return new Result(false, "单选题五星难度数量不足");
                    }
                    list.add(TestPaperUtils.red(choiceList19, i2));
                }
                //多选题
                if (testPaperUtils.getCheckSum() > 0) {
                    Double l1 = Math.floor(testPaperUtils.getCheckSum() * 0.7);
                    List<Choice> choiceList21 = choiceRepository.findAllByDifficultyLevelInAndTypeAndSubId(new Integer[]{3, 4}, 2, testPaperUtils.getSubId());
                    if (choiceList21.size() < l1) {
                        return new Result(false, "多选题三星四星难度数量不足");
                    }
                    list.add(TestPaperUtils.red(choiceList21, l1.intValue()));

                    int l2 = testPaperUtils.getCheckSum() - l1.intValue();
                    List<Choice> choiceList22 = choiceRepository.findAllByDifficultyLevelInAndTypeAndSubId(new Integer[]{5}, 2, testPaperUtils.getSubId());
                    if (choiceList22.size() < l2) {
                        return new Result(false, "多选题五星难度数量不足");
                    }
                    list.add(TestPaperUtils.red(choiceList22, l2));
                }
                //判断题
                if (testPaperUtils.getJudgeSum() > 0) {
                    Double m1 = Math.floor(testPaperUtils.getJudgeSum() * 0.7);
                    List<Choice> choiceList24 = choiceRepository.findAllByDifficultyLevelInAndTypeAndSubId(new Integer[]{3, 4}, 3, testPaperUtils.getSubId());
                    if (choiceList24.size() < m1) {
                        return new Result(false, "判断题三星四星难度数量不足");
                    }
                    list.add(TestPaperUtils.red(choiceList24, m1.intValue()));

                    int m2 = testPaperUtils.getJudgeSum() - m1.intValue();
                    List<Choice> choiceList25 = choiceRepository.findAllByDifficultyLevelInAndTypeAndSubId(new Integer[]{5}, 3, testPaperUtils.getSubId());
                    if (choiceList25.size() < m2) {
                        return new Result(false, "判断题五星难度数量不足");
                    }
                    list.add(TestPaperUtils.red(choiceList25, m2));
                }

                //简答题
                if (testPaperUtils.getAnswerSum() > 0) {
                    Double s1 = Math.floor(testPaperUtils.getAnswerSum() * 0.7);
                    List<Choice> choiceList27 = choiceRepository.findAllByDifficultyLevelInAndTypeAndSubId(new Integer[]{3, 4}, 4, testPaperUtils.getSubId());
                    if (choiceList27.size() < s1) {
                        return new Result(false, "简答题三星四星难度数量不足");
                    }
                    list.add(TestPaperUtils.red(choiceList27, s1.intValue()));

                    int s2 = testPaperUtils.getAnswerSum() - s1.intValue();
                    List<Choice> choiceList28 = choiceRepository.findAllByDifficultyLevelInAndTypeAndSubId(new Integer[]{5}, 4, testPaperUtils.getSubId());
                    if (choiceList28.size() < s2) {
                        return new Result(false, "简答题五星难度数量不足");
                    }
                    list.add(TestPaperUtils.red(choiceList28, s2));
                }



                //填空题
                if (testPaperUtils.getGapSum() > 0) {
                    Double s1 = Math.floor(testPaperUtils.getGapSum() * 0.7);
                    List<Choice> List27 = choiceRepository.findAllByDifficultyLevelInAndTypeAndSubId(new Integer[]{3, 4}, 5, testPaperUtils.getSubId());
                    if (List27.size() < s1) {
                        return new Result(false, "填空题三星四星难度数量不足");
                    }
                    list.add(TestPaperUtils.red(List27, s1.intValue()));
                    int s2 = testPaperUtils.getGapSum() - s1.intValue();
                    List<Choice> List28 = choiceRepository.findAllByDifficultyLevelInAndTypeAndSubId(new Integer[]{5}, 5, testPaperUtils.getSubId());
                    if (List28.size() < s2) {
                        return new Result(false, "填空题五星难度数量不足");
                    }
                    list.add(TestPaperUtils.red(List28, s2));
                }

                break;
            default:
                //单选题
                if (testPaperUtils.getRodSum() > 0) {
                    List<Choice> choiceList1 = choiceRepository.findAllByTypeAndSubId(1, testPaperUtils.getSubId());
                    if (choiceList1.size() < testPaperUtils.getRodSum()) {
                        return new Result(false, "单选题数量不足");
                    }
                    list.add(TestPaperUtils.red(choiceList1, testPaperUtils.getRodSum()));
                }
                if (testPaperUtils.getCheckSum() > 0) {
                    //多选题
                    List<Choice> choiceList30 = choiceRepository.findAllByTypeAndSubId(2, testPaperUtils.getSubId());
                    if (choiceList30.size() < testPaperUtils.getCheckSum()) {
                        return new Result(false, "多选题数量不足");
                    }
                    list.add(TestPaperUtils.red(choiceList30, testPaperUtils.getCheckSum()));
                }
                if (testPaperUtils.getJudgeSum() > 0) {
                    //判断题
                    List<Choice> choiceList31 = choiceRepository.findAllByTypeAndSubId(3, testPaperUtils.getSubId());
                    if (choiceList31.size() < testPaperUtils.getJudgeSum()) {
                        return new Result(false, "判断题数量不足");
                    }
                    list.add(TestPaperUtils.red(choiceList31, testPaperUtils.getJudgeSum()));
                }

                if (testPaperUtils.getAnswerSum() > 0) {
                    //简答题
                    List<Choice> choiceList32 = choiceRepository.findAllByTypeAndSubId(4, testPaperUtils.getSubId());
                    if (choiceList32.size() < testPaperUtils.getAnswerSum()) {
                        return new Result(false, "简答题数量不足");
                    }
                    list.add(TestPaperUtils.red(choiceList32, testPaperUtils.getAnswerSum()));
                }

                if (testPaperUtils.getGapSum() > 0) {
                    //填空题
                    List<Choice> choiceList33 = choiceRepository.findAllByTypeAndSubId(5, testPaperUtils.getSubId());
                    if (choiceList33.size() < testPaperUtils.getGapSum()) {
                        return new Result(false, "填空题数量不足");
                    }
                    list.add(TestPaperUtils.red(choiceList33, testPaperUtils.getGapSum()));
                }

                break;
        }
        List<Choice> list1 = testPaperUtils.conversion(list);
        Collections.sort(list1, new Comparator<Choice>() {
            @Override
            public int compare(Choice o1, Choice o2) {
                //升序
                return o1.getType().compareTo(o2.getType());
            }
        });
        TestPaper testPaper1 = new TestPaper();
        testPaper1.setName(testPaperUtils.getTestPaperName());
        testPaper1.setSubId(testPaperUtils.getSubId());
        testPaper1.setCreationId(sessionUser.getTeacher().getTeaId());
        TestPaper testPaper2 = testPaperRepository.save(testPaper1);
        for (int i = 0; i < list1.size(); i++) {
            TestPaperBindChoice testPaperBindChoice = new TestPaperBindChoice();
            switch (list1.get(i).getType()) {
                case 1:
                    testPaperBindChoice.setScore(testPaperUtils.getRodScore());
                    break;
                case 2:
                    testPaperBindChoice.setScore(testPaperUtils.getCheckScore());
                    break;
                case 3:
                    testPaperBindChoice.setScore(testPaperUtils.getJudgeScore());
                    break;
                case 4:
                    testPaperBindChoice.setScore(testPaperUtils.getAnswerScore());
                    break;
                case 5:
                    testPaperBindChoice.setScore(testPaperUtils.getGapScore());
                    break;
            }
            testPaperBindChoice.setIndexNum(i);
            testPaperBindChoice.setChoiceId(list1.get(i).getId());
            testPaperBindChoice.setTestPaperId(testPaper2.getId());
            testPaperBindChoiceRepository.save(testPaperBindChoice);
        }
        return new Result(true, "试卷生成成功");
    }

    @Override
    public List<TestPaper> findAllBySubjectId(Integer id) {
        return testPaperRepository.findAllBySubId(id);
    }

    @Override
    public List<TestPaper> findAllByTeacherId(Integer id) {
        return testPaperRepository.findAllByCreationId(id);
    }
}
