package com.roland.community.community.cache;

import com.roland.community.community.dto.TagDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TagCache {

    public static List<TagDTO> get(){
        List<TagDTO> tagDTOS = new ArrayList<>();

        TagDTO program = new TagDTO();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("java","python","c++","javascript"));

        TagDTO framework = new TagDTO();
        framework.setCategoryName("框架");
        framework.setTags(Arrays.asList("Spring","SpringBoot","Django","Flask","Vue"));

        tagDTOS.add(program);
        tagDTOS.add(framework);


        return tagDTOS;
    }

    public static String filterTags(String tags){

        String[] split = tags.split(",");

        List<TagDTO> localTags = get();

        List<String> allTags = new ArrayList<>();
        for(TagDTO tagDTO:localTags){
            allTags.addAll(tagDTO.getTags());
        }

        List<String> inValidTags = new ArrayList<>();
        for(String tg :split){
            if(!allTags.contains(tg)){
                inValidTags.add(tg);
            }
        }


        return String.join(",",inValidTags);
    }


}
