package com.roland.community.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {

    private List<QuestionDTO> questionDTOList;
    private Boolean showPrevious;
    private Boolean showFirstPage;
    private Boolean showNext;
    private Boolean showLastPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    public void setPagination(int totalCnt,int page,int size){

        if(totalCnt%size==0){
            totalPage = totalCnt/size;
        }else{
            totalPage = totalCnt/size+1;
        }

        this.page = page;

        for(int i=page-3;i<=page+3;i++){
            if(i>0 && i<=totalPage){
                this.pages.add(i);
            }
        }

        // 是否展示上一页
        if(page==1){
            this.showPrevious=false;
        }else{
            this.showPrevious=true;
        }

        // 是否展示下一页
        if(page==totalPage){
            this.showNext=false;
        }else{
            this.showNext=true;
        }

        // 是否展示第一页
        if(pages.contains(1)){
            this.showFirstPage=false;
        }else{
            this.showFirstPage=true;
        }

        // 是否展示最后一页
        if(pages.contains(totalPage)){
            this.showLastPage=false;
        }else{
            this.showLastPage=true;
        }
    }
}
