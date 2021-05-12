package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.*;
import com.sanshi.fileserver.repository.*;
import com.sanshi.fileserver.service.AnswerService;
import com.sanshi.fileserver.service.RespondentsService;
import com.sanshi.fileserver.vo.AnswerVo;
import com.sanshi.fileserver.vo.SessionUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 【答题ServiceImpl】
 */
@Service("AnswerServiceImpl")
public class AnswerServiceImpl implements AnswerService {
    private AnswerRepository answerRepository;
    private ChoiceRepository choiceRepository;
    private RespondentsRepository respondentsRepository;
    private AssessRepository assessRepository;
    private TestPaperRepository testPaperRepository;
    private TestPaperBindChoiceRepository testPaperBindChoiceRepository;
    private RespondentsService respondentsService;
    private HttpSession session;


    public AnswerServiceImpl(AnswerRepository answerRepository, ChoiceRepository choiceRepository, RespondentsRepository respondentsRepository, AssessRepository assessRepository, TestPaperRepository testPaperRepository, TestPaperBindChoiceRepository testPaperBindChoiceRepository, HttpSession session, RespondentsService respondentsService) {
        this.answerRepository = answerRepository;
        this.choiceRepository = choiceRepository;
        this.respondentsRepository = respondentsRepository;
        this.assessRepository = assessRepository;
        this.testPaperRepository = testPaperRepository;
        this.testPaperBindChoiceRepository = testPaperBindChoiceRepository;
        this.session = session;
        this.respondentsService = respondentsService;
    }

    @Override
    public List<Answer> findall(Answer answer) {
        if (answer.getRespondentsId() != null && answer.getChoiceId() != null) {
            List<Answer> list = new ArrayList<Answer>();
            list.add(answerRepository.findOneByChoiceIdAndRespondentsId(answer.getChoiceId(), answer.getRespondentsId()));
            return list;
        } else {
            return null;
        }
    }


    //保存答卷选择的题答案
    @Override
    @Transactional
    public int save(List<Answer> answerList) {

        for (Answer answer : answerList) {
//查询出试题
            Choice choice = choiceRepository.findOneById(answer.getChoiceId());
            //获取答卷
            Respondents respondents = respondentsRepository.findOneById(answer.getRespondentsId());
            //获取考核
            Assess assess = assessRepository.findOneById(respondents.getAssessId());
            //获取该试题卷
            TestPaper testPaper = testPaperRepository.findOneById(assess.getTestPaperId());
            //修改考核状态
            respondents.setSubmit(2);
            respondentsRepository.save(respondents);
            //获取试题在该试卷的分值
            TestPaperBindChoice testPaperBindChoice = testPaperBindChoiceRepository.findAllByChoiceIdAndTestPaperId(choice.getId(), testPaper.getId());
            switch (choice.getScaleRule()) {
                case 0:
                    if (answer.getAnswer() != null && answer.getAnswer().equals(choice.getCorrect())) {
                        answer.setScore(testPaperBindChoice.getScore());
                    } else {
                        answer.setScore(0d);
                    }
                    answer.setCorrect(1);
                    answer.setCorrectUserid(0);
                    break;
                case 1:
                    Double score = 0d;  //总分数
                    if (choice.getCorrect() != null && answer.getAnswer() != "") {
                        String[] arr = choice.getCorrect().split(",");  //正确答案
                        String str = answer.getAnswer();
                        String[] ans = str.split(",");
                        int sum = 0;
                        for (int i = 0; i < ans.length; i++) {
                            Boolean flag = Arrays.asList(arr).contains(ans[i]);
                            if (flag) {
                                sum++;
                            } else {
                                sum = 0;
                                score = 0d;
                                break;
                            }
                        }
                        if (sum > 0) {
                            Double single = testPaperBindChoice.getScore() / Double.valueOf(arr.length);
                            Double all = single * sum;
                            score = Math.floor(all) + (all % 1 >= 0.5 ? 0.5 : 0);
                        }
                    }
                    answer.setScore(score);
                    answer.setCorrect(1);
                    answer.setCorrectUserid(0);
                    break;
                default:
                    answer.setScore(0d);
                    answer.setCorrect(0);
            }
            answerRepository.save(answer);
        }

        return 1;
    }


    @Override
    public void delete(Answer answer) {
        answerRepository.deleteById(answer.getId());
    }

    @Override
    public List<AnswerVo> approval(Integer respondentId) {
        List<AnswerVo> answerVoList = new ArrayList<AnswerVo>();
        Respondents respondents = respondentsRepository.findOneById(respondentId);
        Assess assess = assessRepository.findOneById(respondents.getAssessId());
        TestPaper testPaper = testPaperRepository.findOneById(assess.getTestPaperId());
        List<TestPaperBindChoice> testPaperBindChoiceList = testPaperBindChoiceRepository.findAllByTestPaperIdOrderByIndexNumAsc(testPaper.getId());
        for (TestPaperBindChoice testPaperBindChoice : testPaperBindChoiceList) {
            AnswerVo answerVo = new AnswerVo();
            Choice choice = choiceRepository.findOneByIdAndScaleRule(testPaperBindChoice.getChoiceId(), 2);
            if (choice == null) {
                continue;
            }
            answerVo.setChoice(choice);
            answerVo.setAnswer(answerRepository.findOneByChoiceIdAndRespondentsId(testPaperBindChoice.getChoiceId(), respondents.getId()));
            answerVo.setScore(testPaperBindChoice.getScore());
            answerVoList.add(answerVo);
        }
        return answerVoList;
    }

    @Override
    @Transactional
    public int teachRead(List<Answer> answerList) {
        for (Answer answer : answerList) {
            double score = answer.getScore();
            answer = answerRepository.findOneById(answer.getId());
            answer.setCorrectUserid(1);
            answer.setCorrect(1);
            answer.setScore(score);
            answerRepository.save(answer);
        }
        respondentsService.Read(answerList.get(0).getRespondentsId());
        return 1;
    }

    @Override
    public int skip(Integer respondentId) {
        return respondentsRepository.findOneById(respondentId).getAssessId();
    }

    @Override
    public List<AnswerVo> particulars(Integer assessId) {
        //拿到考核
        Assess assess = assessRepository.findOneById(assessId);
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        //获取答卷
        Respondents respondents = respondentsRepository.findByStuIdAndAssessId(sessionUser.getStudent().getStuId(), assess.getId());
        //获取试卷
        TestPaper testPaper = testPaperRepository.findOneById(assess.getTestPaperId());
        //获取试卷的所有试题
        List<TestPaperBindChoice> testPaperBindChoiceList = testPaperBindChoiceRepository.findAllByTestPaperIdOrderByIndexNumAsc(testPaper.getId());
        List<AnswerVo> answerVoList = new ArrayList<AnswerVo>();
        for (TestPaperBindChoice testPaperBindChoice : testPaperBindChoiceList) {
            AnswerVo answerVo = new AnswerVo();
            Choice choice = choiceRepository.findOneById(testPaperBindChoice.getChoiceId());
            answerVo.setChoice(choice);
            if (respondents == null) {
                answerVo.setAnswer(null);
            } else {
                answerVo.setAnswer(answerRepository.findOneByChoiceIdAndRespondentsId(testPaperBindChoice.getChoiceId(), respondents.getId()));
            }
            answerVo.setScore(testPaperBindChoice.getScore());
            answerVoList.add(answerVo);
        }

        return answerVoList;
    }

    @Override
    public Answer findById(Integer id) {
        return answerRepository.findOneById(id);
    }
}
