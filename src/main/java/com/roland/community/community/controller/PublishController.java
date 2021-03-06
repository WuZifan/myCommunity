package com.roland.community.community.controller;

import com.roland.community.community.cache.TagCache;
import com.roland.community.community.dto.TagDTO;
import com.roland.community.community.mapper.QuestionMapper;
import com.roland.community.community.model.Question;
import com.roland.community.community.model.User;
import com.roland.community.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;


    @GetMapping("/publish")
    public String publish(Model model){
        List<TagDTO> tagDTOS = TagCache.get();
        model.addAttribute("tags",tagDTOS);

        return "publish";
    }

    @GetMapping("/publish/{id}")
    public String editQuestion(@PathVariable(name = "id")Long id,
                               Model model){
        Question question =questionMapper.selectByPrimaryKey(id);
        List<TagDTO> tagDTOS = TagCache.get();

        model.addAttribute("title",question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id",question.getId());
        model.addAttribute("tags",tagDTOS);


        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value="title",required = false) String title,
            @RequestParam(value="description",required = false) String description,
            @RequestParam(value="tag",required = false) String tag,
            @RequestParam(value = "id",required = false) Long id,
            HttpServletRequest request,
            Model model
    ){
        System.out.println("aaa");
        List<TagDTO> tagDTOS = TagCache.get();

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("tags",tagDTOS);



        User user =(User) request.getSession().getAttribute("user");
        if(user == null){
            System.out.println("user is none");
            model.addAttribute("error","???????????????");
            return "publish";
        }

        String filterTags = TagCache.filterTags(tag);
        if(StringUtils.isNotBlank(filterTags)){
            model.addAttribute("error","??????tag???"+filterTags);
            return "publish";
        }


        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setViewCount(0);
        question.setLikeCount(0);
        question.setCommentCount(0);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setId(id);



        questionService.createOrUpdate(question);
//        questionMapper.insertQuestion(question);
        return "redirect:/";
    }
}
