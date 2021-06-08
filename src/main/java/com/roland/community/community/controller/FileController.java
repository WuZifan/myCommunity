package com.roland.community.community.controller;

import com.roland.community.community.dto.FileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Controller
public class FileController {

    @PostMapping("/file/upload")
    @ResponseBody
    public Object uploadReceiver(@RequestParam(value = "editormd-image-file") MultipartFile file){
        if (file.isEmpty()) {
            System.out.println("文件为空空");
        }

        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = "/Users/roland/IntellJProject/mycommunity/src/main/resources/uploadImg/"; // 上传后的路径
        fileName = UUID.randomUUID() + suffixName; // 新文件名

        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }

        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }


        FileDTO fileDTO=new FileDTO();
        fileDTO.setMessage("ok");
        fileDTO.setSuccess(1);
        fileDTO.setUrl("/uploadImg/"+fileName);

        return fileDTO;
    }
}
