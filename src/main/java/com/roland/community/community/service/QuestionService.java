package com.roland.community.community.service;


import com.roland.community.community.dto.PaginationDTO;
import com.roland.community.community.dto.QuestionDTO;
import com.roland.community.community.mapper.QuestionMapper;
import com.roland.community.community.mapper.UserMapper;
import com.roland.community.community.model.Question;
import com.roland.community.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public PaginationDTO selectPage(Integer page, Integer size){

        // 总页数
        Integer totalCnt = questionMapper.countQuestion();

        if(page> totalCnt/size){
            page =totalCnt/size;
        }else if(page<1){
            page=1;
        }

        // 计算当前页起始位置
        Integer offset = size*(page-1);



        // 拿到当前页问题
        List<Question> questions = questionMapper.selectPage(offset,size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();


        for (Question question: questions
                ) {
            User user = userMapper.selectById(question.getCreator());
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

    public PaginationDTO getQuestionByUserId(Integer id, Integer page, Integer size) {
        // 总页数
        Integer totalCnt = questionMapper.countQuestionByUserId(id);

        if(page> totalCnt/size){
            page =totalCnt/size;
        }else if(page<1){
            page=1;
        }

        // 计算当前页起始位置
        Integer offset = size*(page-1);



        // 拿到当前页问题
        List<Question> questions = questionMapper.selectPageByUserId(id,offset,size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();

        User user = userMapper.selectById(id);
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

    public QuestionDTO getQuestionById(Integer id) {
        Question question = questionMapper.getQuestionById(id);
        User user = userMapper.getUserById(question.getCreator());

        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);

        return questionDTO;
    }

    public void createOrUpdate(Question que){
        Question question = questionMapper.getQuestionById(que.getId());
        if(question==null){
            questionMapper.insertQuestion(que);
        }else{
            questionMapper.updateQuestion(que);

        }
    }
}
