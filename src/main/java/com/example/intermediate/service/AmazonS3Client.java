package com.example.intermediate.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.domain.ImageMapper;
import com.example.intermediate.repository.ImageMapperRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Getter
public class AmazonS3Service {

    private final AmazonS3Client amazonS3Client;

    private final ImageMapperRepository imageMapperRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    //파일업로드
    @Transactional
    public ResponseDto<?> uploadFile (MultipartFile multipartFile) {
        if(validateFileExists(multipartFile))      // 빈 파일인지 확인
            return ResponseDto.fail("NO_EXIST_FILE", "등록된 이미지가 없습니다.");
        String fileName = createFileName(multipartFile.getOriginalFilename());  // 난수파일이름생성 (난수이름+파일이름)
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());          // ObjectMetadata에 파일 타입, byte크기 넣어주기. 넣지않으면 IDE상에서 설정하라는 권장로그가 뜸
        objectMetadata.setContentLength(multipartFile.getSize());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));        // S3에 업로드
        } catch (IOException e) {
            return ResponseDto.fail("FILE_UPLOAD_FAIL","파일 업로드 실패");
        }
        ImageMapper imageMapper = ImageMapper.builder()                         // 업로드한 파일들을 관리할 테이블에 파일이름, URL넣기
                .url(amazonS3Client.getUrl(bucketName, fileName).toString())
                .name(fileName)
                .build();
        imageMapperRepository.save(imageMapper);
        return ResponseDto.success(imageMapper);
    }

    //유니크한파일이름생성
    private String createFileName(String originalFileName) {
        return UUID.randomUUID().toString().concat(originalFileName);
    }

    //파일삭제
    @Transactional
    public boolean removeFile(String fileName) {
        Optional<ImageMapper> optionalImageMapper = imageMapperRepository.findByName(fileName); // 파일이름으로 파일가져오기
        if(optionalImageMapper.isEmpty())    // 실제있는 파일인지 확인
            return true;
        ImageMapper image = optionalImageMapper.get();
        imageMapperRepository.deleteById(image.getId());    // imageMapper에서 삭제
        DeleteObjectRequest request = new DeleteObjectRequest(bucketName, fileName); // 삭제 request생성
        amazonS3Client.deleteObject(request);      // s3에서 파일삭제
        return false;
    }

    //실제있는 파일인지 확인
    private boolean validateFileExists(MultipartFile multipartFile) {
        return multipartFile.isEmpty();
    }
}
