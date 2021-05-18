package com.sanshi.fileserver.service.impl;

import com.sanshi.fileserver.bean.*;
import com.sanshi.fileserver.repository.*;
import com.sanshi.fileserver.service.ChoiceService;
import com.sanshi.fileserver.utils.ChoiceUtil;
import com.sanshi.fileserver.vo.ScreenChoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("ChoiceServiceImpl")
public class ChoiceServiceImpl implements ChoiceService {
    private ChoiceRepository choiceRepository;
    private TestPaperBindChoiceRepository testPaperBindChoiceRepository;
    private TestPaperRepository testPaperRepository;
    private SubjectRepository subjectRepository;
    private ChoiceFileRepository choiceFileRepository;
    private KnowledgePointRepository  knowledgePointRepository;

    public ChoiceServiceImpl(ChoiceRepository choiceRepository, TestPaperBindChoiceRepository testPaperBindChoiceRepository, TestPaperRepository testPaperRepository, SubjectRepository subjectRepository,ChoiceFileRepository choiceFileRepository,KnowledgePointRepository  knowledgePointRepository) {
        this.choiceRepository = choiceRepository;
        this.testPaperBindChoiceRepository = testPaperBindChoiceRepository;
        this.testPaperRepository = testPaperRepository;
        this.subjectRepository = subjectRepository;
        this.choiceFileRepository=choiceFileRepository;
        this.knowledgePointRepository=knowledgePointRepository;
    }

    @Override
    public Choice findOneById(Integer id) {
        return choiceRepository.findOneById(id);
    }

    @Override
    @Transactional
    public Choice save(ChoiceUtil choiceUtil) {
        Choice  choice=choiceRepository.save(choiceUtil.getChoice());
       choiceFileRepository.deleteByChoiceId(choice.getId());
        for (ChoiceFile  choiceFile :choiceUtil.getChoiceFileList() ){
            choiceFile.setChoiceId(choice.getId());
           choiceFileRepository.save(choiceFile);
        }
        return choice ;
    }

//    @Override
//    public Map findAll(ScreenChoice choice) {
//        Map json = new HashMap();
//        Sort sort;
//        Pageable pageable;
//
//
//        if (!choice.getSort().isEmpty()) {
//            if (choice.getSort().equals("esc"))//升序
//                sort = Sort.by(choice.getSortName()).ascending();
//            else
//                sort = Sort.by(choice.getSortName()).descending();
//            pageable = PageRequest.of(choice.getPageIndex(), choice.getPageNumber(), sort);
//        }
//        pageable = PageRequest.of(choice.getPageIndex(), choice.getPageNumber());
//        json.put("resoult", true);
//        if (choice.getName().isEmpty()) choice.setName("");
//        if (choice.getSubId() == 0) {
//            if (choice.getType() == 0) {
//                if (choice.getAbilityIds() != null && !choice.getAbilityIds().equals("0")) {
//                    if (choice.getDifficultyLevel() != null && choice.getDifficultyLevel() != 0) {
//                        json.put("page", choiceRepository.findAllByAbilityIdsAndTopicLikeAndDifficultyLevel(choice.getAbilityIds(), choice.getName(), choice.getDifficultyLevel(), pageable));
//                    } else {
//                        json.put("page", choiceRepository.findAllByAbilityIdsAndTopicLike(choice.getAbilityIds(), choice.getName(), pageable));
//                    }
//                } else {
//                    if (choice.getDifficultyLevel() != null && choice.getDifficultyLevel() != 0) {
//                        json.put("page", choiceRepository.findAllByTopicLikeAndDifficultyLevel("%" + choice.getName() + "%", choice.getDifficultyLevel(), pageable));
//                    } else {
//                        json.put("page", choiceRepository.findAllByTopicLike("%" + choice.getName() + "%", pageable));
//                    }
//                }
//            } else {
//                if (choice.getAbilityIds() != null && !choice.getAbilityIds().equals("0")) {
//                    if (choice.getDifficultyLevel() != null && choice.getDifficultyLevel() != 0) {
//                        json.put("page", choiceRepository.findAllByTypeAndTopicLikeAndAbilityIdsAndDifficultyLevel(choice.getType(), choice.getName(), choice.getAbilityIds(), choice.getDifficultyLevel(), pageable));
//                    } else {
//                        json.put("page", choiceRepository.findAllByTypeAndTopicLikeAndAbilityIds(choice.getType(), choice.getName(), choice.getAbilityIds(), pageable));
//                    }
//                } else {
//                    if (choice.getDifficultyLevel() != null && choice.getDifficultyLevel() != 0) {
//                        json.put("page", choiceRepository.findAllByTypeAndTopicLikeAndDifficultyLevel(choice.getType(), "%" + choice.getName() + "%", choice.getDifficultyLevel(), pageable));
//                    } else {
//                        json.put("page", choiceRepository.findAllByTypeAndTopicLike(choice.getType(), "%" + choice.getName() + "%", pageable));
//                    }
//                }
//            }
//        } else {
//            if (choice.getType() == 0) {
//                if (choice.getAbilityIds() != null && !choice.getAbilityIds().equals("0")) {
//                    if (choice.getDifficultyLevel() != null && choice.getDifficultyLevel() != 0) {
//                        json.put("page", choiceRepository.findAllBySubIdAndTopicLikeAndAbilityIdsAndDifficultyLevel(choice.getSubId(), "%" + choice.getName() + "%", choice.getAbilityIds(), choice.getDifficultyLevel(), pageable));
//                    } else {
//                        json.put("page", choiceRepository.findAllBySubIdAndTopicLikeAndAbilityIds(choice.getSubId(), "%" + choice.getName() + "%", choice.getAbilityIds(), pageable));
//                    }
//                } else {
//                    if (choice.getDifficultyLevel() != null && choice.getDifficultyLevel() != 0) {
//                        json.put("page", choiceRepository.findAllBySubIdAndTopicLikeAndDifficultyLevel(choice.getSubId(), "%" + choice.getName() + "%", choice.getDifficultyLevel(), pageable));
//                    } else {
//                        json.put("page", choiceRepository.findAllBySubIdAndTopicLike(choice.getSubId(), "%" + choice.getName() + "%", pageable));
//                    }
//                }
//            } else {
//                if (choice.getAbilityIds() != null && !choice.getAbilityIds().equals("0")) {
//                    if (choice.getDifficultyLevel() != null && choice.getDifficultyLevel() != 0) {
//                        json.put("page", choiceRepository.findAllBySubIdAndTypeAndTopicLikeAndAbilityIdsAndDifficultyLevel(choice.getSubId(), choice.getType(), "%" + choice.getName() + "%", choice.getAbilityIds(), choice.getDifficultyLevel(), pageable));
//                    } else {
//                        json.put("page", choiceRepository.findAllBySubIdAndTypeAndTopicLikeAndAbilityIds(choice.getSubId(), choice.getType(), "%" + choice.getName() + "%", choice.getAbilityIds(), pageable));
//                    }
//                } else {
//                    if (choice.getDifficultyLevel() != null && choice.getDifficultyLevel() != 0) {
//                        json.put("page", choiceRepository.findAllBySubIdAndTypeAndTopicLikeAndDifficultyLevel(choice.getSubId(), choice.getType(), "%" + choice.getName() + "%", choice.getDifficultyLevel(), pageable));
//                    } else {
//                        json.put("page", choiceRepository.findAllBySubIdAndTypeAndTopicLike(choice.getSubId(), choice.getType(), "%" + choice.getName() + "%", pageable));
//                    }
//                }
//            }
//        }
//
//        return json;
//    }


    @Override
    public Map findAll(ScreenChoice choice) {
        Map json = new HashMap();
        Sort sort;

        Page<Choice> choicePage=null;
        Pageable pageable;


        if (!choice.getSort().isEmpty()) {
            if (choice.getSort().equals("esc"))//升序
                sort = Sort.by(choice.getSortName()).ascending();
            else
                sort = Sort.by(choice.getSortName()).descending();
            pageable = PageRequest.of(choice.getPageIndex(), choice.getPageNumber(), sort);
        }
        pageable = PageRequest.of(choice.getPageIndex(), choice.getPageNumber());
        json.put("resoult", true);
        if (choice.getName().isEmpty()) choice.setName("");
        if (choice.getSubId() == 0) {
            if (choice.getType() == 0) {
                if (choice.getAbilityIds() != null && !choice.getAbilityIds().equals("0")) {
                    if (choice.getDifficultyLevel() != null && choice.getDifficultyLevel() != 0) {
                        choicePage= choiceRepository.findAllByAbilityIdsAndTopicLikeAndDifficultyLevel(choice.getAbilityIds(), choice.getName(), choice.getDifficultyLevel(), pageable);
                    } else {
                        choicePage =choiceRepository.findAllByAbilityIdsAndTopicLike(choice.getAbilityIds(), choice.getName(), pageable);
                    }
                } else {
                    if (choice.getDifficultyLevel() != null && choice.getDifficultyLevel() != 0) {
                        choicePage = choiceRepository.findAllByTopicLikeAndDifficultyLevel("%" + choice.getName() + "%", choice.getDifficultyLevel(), pageable);
                    } else {
                        choicePage =   choiceRepository.findAllByTopicLike("%" + choice.getName() + "%", pageable);
                    }
                }
            } else {
                if (choice.getAbilityIds() != null && !choice.getAbilityIds().equals("0")) {
                    if (choice.getDifficultyLevel() != null && choice.getDifficultyLevel() != 0) {
                        choicePage = choiceRepository.findAllByTypeAndTopicLikeAndAbilityIdsAndDifficultyLevel(choice.getType(), choice.getName(), choice.getAbilityIds(), choice.getDifficultyLevel(), pageable);
                    } else {
                        choicePage = choiceRepository.findAllByTypeAndTopicLikeAndAbilityIds(choice.getType(), choice.getName(), choice.getAbilityIds(), pageable);
                    }
                } else {
                    if (choice.getDifficultyLevel() != null && choice.getDifficultyLevel() != 0) {
                        choicePage =choiceRepository.findAllByTypeAndTopicLikeAndDifficultyLevel(choice.getType(), "%" + choice.getName() + "%", choice.getDifficultyLevel(), pageable);
                    } else {
                        choicePage = choiceRepository.findAllByTypeAndTopicLike(choice.getType(), "%" + choice.getName() + "%", pageable);
                    }
                }
            }
        } else {
            if (choice.getType() == 0) {
                if (choice.getAbilityIds() != null && !choice.getAbilityIds().equals("0")) {
                    if (choice.getDifficultyLevel() != null && choice.getDifficultyLevel() != 0) {
                        choicePage =choiceRepository.findAllBySubIdAndTopicLikeAndAbilityIdsAndDifficultyLevel(choice.getSubId(), "%" + choice.getName() + "%", choice.getAbilityIds(), choice.getDifficultyLevel(), pageable);
                    } else {
                        choicePage =choiceRepository.findAllBySubIdAndTopicLikeAndAbilityIds(choice.getSubId(), "%" + choice.getName() + "%", choice.getAbilityIds(), pageable);
                    }
                } else {
                    if (choice.getDifficultyLevel() != null && choice.getDifficultyLevel() != 0) {
                        choicePage = choiceRepository.findAllBySubIdAndTopicLikeAndDifficultyLevel(choice.getSubId(), "%" + choice.getName() + "%", choice.getDifficultyLevel(), pageable);
                    } else {
                        choicePage = choiceRepository.findAllBySubIdAndTopicLike(choice.getSubId(), "%" + choice.getName() + "%", pageable);
                    }
                }
            } else {
                if (choice.getAbilityIds() != null && !choice.getAbilityIds().equals("0")) {
                    if (choice.getDifficultyLevel() != null && choice.getDifficultyLevel() != 0) {
                        choicePage = choiceRepository.findAllBySubIdAndTypeAndTopicLikeAndAbilityIdsAndDifficultyLevel(choice.getSubId(), choice.getType(), "%" + choice.getName() + "%", choice.getAbilityIds(), choice.getDifficultyLevel(), pageable);
                    } else {
                        choicePage = choiceRepository.findAllBySubIdAndTypeAndTopicLikeAndAbilityIds(choice.getSubId(), choice.getType(), "%" + choice.getName() + "%", choice.getAbilityIds(), pageable);
                    }
                } else {
                    if (choice.getDifficultyLevel() != null && choice.getDifficultyLevel() != 0) {
                        choicePage =choiceRepository.findAllBySubIdAndTypeAndTopicLikeAndDifficultyLevel(choice.getSubId(), choice.getType(), "%" + choice.getName() + "%", choice.getDifficultyLevel(), pageable);
                    } else {
                        choicePage =choiceRepository.findAllBySubIdAndTypeAndTopicLike(choice.getSubId(), choice.getType(), "%" + choice.getName() + "%", pageable);
                    }
                }
            }
        }

        for (int i=0;i<choicePage.getContent().size();i++){
            String   abilityIds="";
            Choice choice1=(Choice) choicePage.getContent().get(i);
            if(choice1.getAbilityIds()!=null && !choice1.getAbilityIds().equals("")){
                String   arr []  =choice1.getAbilityIds().split(",");
                List<Integer>    a=new ArrayList<>();
                for (int x=0;x<arr.length;x++){
                    if(arr[x].equals("")){
                        continue;
                    }
                    a.add(Integer.parseInt(arr[x]));
                }
                List<KnowledgePoint>  knowledgePointList=    knowledgePointRepository.findAllByIdIn(a);
                for (KnowledgePoint  knowledgePoint: knowledgePointList) {
                    abilityIds+=knowledgePoint.getName()+",";
                }

            }
            choice1.setAbilityIds(abilityIds);
        }
        json.put("page",choicePage);
        return json;
    }

    @Override
    public Map findAll2(ScreenChoice choice) {
        return null;
    }


    @Override
    public Map deleteById(Integer id) {
        Map json = new HashMap();
        List<TestPaperBindChoice> testPaperBindChoices = testPaperBindChoiceRepository.findAllByChoiceId(id);
        if (testPaperBindChoices.size() == 0) {
            choiceRepository.deleteById(id);
            json.put("resoult", true);
        } else {
            json.put("resoult", false);
            json.put("msg", "已被" + testPaperBindChoices.size() + "张试卷关联，无删除权限");
            String testPaperNames = "";
            for (int i = 0; i < testPaperBindChoices.size(); i++) {
                testPaperNames += "\r\n" + (i + 1) + "." + testPaperRepository.findOneById(testPaperBindChoices.get(i).getTestPaperId()).getName();
            }
            json.put("testPaperNames", testPaperNames);
        }
        return json;
    }

    @Override
    public List<Choice> selectChoiceByTestPaperId(Integer id) {
        return choiceRepository.findByIdIn(testPaperBindChoiceRepository.findChoicesIdByTestPaperId(id));
    }

    @Override
    public Subject findOneByName(String name) {
        return subjectRepository.findOneByName(name);
    }

    @Override
    public int saves(List<Choice> choiceList) {
        for (Choice choice : choiceList) {
            if (choiceRepository.findAllByTopic(choice.getTopic()).size() > 0) {
                continue;
            }
            choiceRepository.save(choice);
        }
        return 1;
    }

    @Override
    public List<Choice> findAllByTopic(String topic) {
        return choiceRepository.findAllByTopic(topic);
    }
}
