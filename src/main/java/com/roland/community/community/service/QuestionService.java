package com.roland.community.community.service;


import com.roland.community.community.Exception.CustomizeErrorCode;
import com.roland.community.community.Exception.CustomizeException;
import com.roland.community.community.dto.PaginationDTO;
import com.roland.community.community.dto.QuestionDTO;
import com.roland.community.community.mapper.QuestionExtMapper;
import com.roland.community.community.mapper.QuestionMapper;
import com.roland.community.community.mapper.UserMapper;
import com.roland.community.community.model.Question;
import com.roland.community.community.model.QuestionExample;
import com.roland.community.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    public PaginationDTO selectPage(Integer page, Integer size){

        // 总页数
        Integer totalCnt =(int)questionMapper.countByExample(new QuestionExample());

        if(page> totalCnt/size){
            page =totalCnt/size;
        }else if(page<1){
            page=1;
        }

        // 计算当前页起始位置
        Integer offset = size*(page-1);



        // 拿到当前页问题
        Map<String,Integer> map = new HashMap<>();
        map.put("offset",offset);
        map.put("limit",size);
        List<Question> questions = questionExtMapper.selectPage(map);
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();


        for (Question question: questions
                ) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }

        paginationDTO.setQuestionDTOList(questionDTOS);
        paginationDTO.setPagination(totalCnt,page,size);

//        if (page<1 || page >paginationDTO.getTotalPage()){
//            return null;
//        }
        return paginationDTO;


    }

    public PaginationDTO getQuestionByUserId(Long id, Integer page, Integer size) {
        // 总页数
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(id);

        Integer totalCnt = (int)questionMapper.countByExample(questionExample);

        if(page> totalCnt/size){
            page =totalCnt/size;
        }else if(page<1){
            page=1;
        }

        // 计算当前页起始位置
        Integer offset = size*(page-1);



        // 拿到当前页问题
        Map<String,Object> map = new HashMap<>();
        map.put("creator",id);
        map.put("offset",offset);
        map.put("limit",size);
        List<Question> questions = questionExtMapper.selectPageByUser(map);
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();

        User user = userMapper.selectByPrimaryKey(id);
        for (Question question: questions
                ) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }

        paginationDTO.setQuestionDTOList(questionDTOS);
        paginationDTO.setPagination(totalCnt,page,size);

        return paginationDTO;

    }

    public QuestionDTO getQuestionById(Long id) {

        Question question = questionMapper.selectByPrimaryKey(id);
        if(question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }

        User user = userMapper.selectByPrimaryKey(question.getCreator());

        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);

        return questionDTO;
    }

    public void createOrUpdate(Question que){
        Question question = questionMapper.selectByPrimaryKey(que.getId());
        if(question==null){
            questionMapper.insert(que);
        }else{
            que.setGmtCreate(question.getGmtCreate());
            questionMapper.updateByPrimaryKeySelective(que);

        }
    }

    public void increseView(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        questionExtMapper.increseView(question);
    }
}
