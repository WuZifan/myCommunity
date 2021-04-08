package com.rolamd.community.community.service;


import com.rolamd.community.community.dto.PaginationDTO;
import com.rolamd.community.community.dto.QuestionDTO;
import com.rolamd.community.community.mapper.QuestionMapper;
import com.rolamd.community.community.mapper.UserMapper;
import com.rolamd.community.community.model.Question;
import com.rolamd.community.community.model.User;
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

    public List<QuestionDTO> selectAll(){
        List<Question> questions = questionMapper.selectAll();
        List<QuestionDTO> questionDTOS= new ArrayList<>();
        for (Question question: questions
             ) {
            User user = userMapper.selectById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        return questionDTOS;

    }

    public PaginationDTO selectPage(Integer page,Integer size){

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

}
