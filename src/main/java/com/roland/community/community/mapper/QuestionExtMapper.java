package com.roland.community.community.mapper;

import com.roland.community.community.dto.QuestionSearchDTO;
import com.roland.community.community.model.Question;
import com.roland.community.community.model.QuestionExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface QuestionExtMapper {

    int increseView(Question record);

    int increseComment(Question record);

    List<Question> selectPage(Map map);

    List<Question> selectPageByUser(Map map);

    List<Question> selectRelated(Question question);

    Integer countByQuestion(@Param("search") String search);

    List<Question> selectPageBySearch(QuestionSearchDTO questionSearchDTO);
}