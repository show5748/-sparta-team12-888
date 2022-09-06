package com.example.intermediate.controller;

import com.example.intermediate.controller.request.PostRequestDto;
import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class ImgUploadController {
    private final S3Service s3Service;

    @RequestMapping(value = "/api/upload", method = RequestMethod.POST)
    public String uploadFile(@RequestPart(value = "file", required = false) MultipartFile multipartFile, @RequestPart(value = "post") PostRequestDto postRequestDto,
                                     HttpServletRequest request)
            throws IllegalAccessException {
        System.out.println(postRequestDto.getTitle());
        System.out.println(postRequestDto.getContent());
        return s3Service.upload(multipartFile);
    }
}
