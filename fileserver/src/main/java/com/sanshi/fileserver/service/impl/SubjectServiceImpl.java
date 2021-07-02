package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.*;
import com.sanshi.fileserver.repository.*;
import com.sanshi.fileserver.service.SubjectService;
import com.sanshi.fileserver.service.TestPaperService;
import com.sanshi.fileserver.vo.PageGet;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("SubjectServiceImpl")
public class SubjectServiceImpl implements SubjectService {
    private SubjectRepository subjectRepository;
    private TestPaperRepository testPaperRepository;
    private AssessRepository  assessRepository;
    private RespondentsRepository  respondentsRepository;
    private AnswerRepository  answerRepository;
    private AssessUserRepository assessUserRepository;
    private ChoiceRepository  choiceRepository;
    private TestPaperBindChoiceRepository  testPaperBindChoiceRepository;
    private ChoiceFileRepository choiceFileRepository;
    private KnowledgePointRepository knowledgePointRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository, TestPaperRepository testPaperRepository, AssessRepository assessRepository, RespondentsRepository respondentsRepository, AnswerRepository answerRepository, AssessUserRepository assessUserRepository, ChoiceRepository choiceRepository, TestPaperBindChoiceRepository testPaperBindChoiceRepository, ChoiceFileRepository choiceFileRepository, KnowledgePointRepository knowledgePointRepository) {
        this.subjectRepository = subjectRepository;
        this.testPaperRepository = testPaperRepository;
        this.assessRepository = assessRepository;
        this.respondentsRepository = respondentsRepository;
        this.answerRepository = answerRepository;
        this.assessUserRepository = assessUserRepository;
        this.choiceRepository = choiceRepository;
        this.testPaperBindChoiceRepository = testPaperBindChoiceRepository;
        this.choiceFileRepository = choiceFileRepository;
        this.knowledgePointRepository = knowledgePointRepository;
    }

    @Override
    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    @Override
    public Subject findById(Integer id) {
        return subjectRepository.findOneById(id);
    }

    @Override
    public Subject save(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {

        List<TestPaper> testPaperList = testPaperRepository.findAllBySubId(id);
        for (TestPaper testPaper : testPaperList) {
            List<Assess> assessList = assessRepository.findAllByTestPaperId(testPaper.getId());
            for (Assess assess : assessList) {
                List<Respondents> respondentsList = respondentsRepository.findAllByAssessId(assess.getId());
                for (Respondents respondents : respondentsList) {
                    List<Answer> answerList = answerRepository.findAllByRespondentsId(respondents.getId());
                    for (Answer answer : answerList) {
                        answerRepository.deleteById(answer.getId());
                    }
                    respondentsRepository.deleteById(respondents.getId());
                }
                assessUserRepository.deleteByAssessId(assess.getId());
                assessRepository.deleteById(assess.getId());
            }
            testPaperRepository.deleteById(testPaper.getId());
            testPaperBindChoiceRepository.deleteByTestPaperId(testPaper.getId());
        }
        List<Choice>  choiceList=choiceRepository.findAllBySubId(id);
      for (Choice choice:choiceList ){
          testPaperBindChoiceRepository.deleteByChoiceId(choice.getId());
          choiceFileRepository.deleteByChoiceId(choice.getId());
      }
        knowledgePointRepository.deleteBySubId(id);
        subjectRepository.deleteById(id);
    }

    @Override
    public Map getAll(PageGet pageGet) {
        Pageable pageable;
        pageable = PageRequest.of(pageGet.getPageIndex(), pageGet.getPageNumber());
        Map json = new HashMap();
        json.put("resoult", true);
        if (pageGet.getLikeName().isEmpty())
            pageGet.setLikeName("");
        json.put("page", subjectRepository.findAllByNameLike("%" + pageGet.getLikeName() + "%", pageable));
        return json;
    }
}
