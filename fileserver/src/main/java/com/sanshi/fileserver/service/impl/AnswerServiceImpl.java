package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.Answer;
import com.sanshi.fileserver.repository.AnswerRepository;
import com.sanshi.fileserver.service.AnswerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
/**
 * 【答题ServiceImpl】
 */
@Service("AnswerServiceImpl")
public class AnswerServiceImpl implements AnswerService {
    private AnswerRepository answerRepository;

    public AnswerServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    public List<Answer> findall(Answer answer) {
        if(answer.getRespondentsId()!=null && answer.getChoiceId()!=null){
            List<Answer> list=new ArrayList<Answer>();
            list.add(answerRepository.findOneByChoiceIdAndRespondentsId(answer.getChoiceId(),answer.getRespondentsId()));
            return list;
        }else{
            return null;
        }
    }

    @Override
    public Answer save(Answer answer) {
        return answerRepository.save(answer);
    }

    @Override
    public void delete(Answer answer) {
        answerRepository.deleteById(answer.getId());
    }
}
