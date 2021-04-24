package com.roland.community.community.mapper;

import com.roland.community.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question " +
            "(title,description,gmt_create,gmt_modified,creator,tag) " +
            "values " +
            "(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    public int insertQuestion(Question question);


    @Select("SELECT * FROM question")
    public List<Question> selectAll();

    @Select("SELECT * FROM question Limit #{size} OFFSET #{offset}")
    public List<Question> selectPage(@Param("offset")Integer offset,
                                     @Param("size")Integer size);


    @Select("SELECT COUNT(1) FROM question")
    public Integer countQuestion();

    @Select("SELECT COUNT(1) FROM question where creator=#{id}")
    Integer countQuestionByUserId(Integer id);

    @Select("SELECT * FROM question where creator=#{id} Limit #{size} OFFSET #{offset}")
    List<Question> selectPageByUserId(@Param("id") Integer id,
                              @Param("offset")Integer offset,
                              @Param("size")Integer size);

    @Select("SELECT * from question where id=#{id}")
    Question getQuestionById(@Param("id") Integer id);

    @Update("update question set title=#{title},description=#{description},gmt_modified=#{gmtModified},tag=#{tag} where id=#{id}")
    void updateQuestion(Question que);
}