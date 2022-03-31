//package com.example.gongdon.file.service;
//
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.model.CannedAccessControlList;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.net.URLDecoder;
//import java.util.Optional;
//import java.util.UUID;
//
//@Slf4j
//@RequiredArgsConstructor
//@Component
//public class S3Service {
//
//    private final AmazonS3Client amazonS3Client;
//
//    @Value("${cloud.aws.s3.bucket}")
//    public String bucket;
//
//    @Value("${s3Url}")
//    public String s3Url;
//
//    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
//        File uploadFile = convert(multipartFile).orElseThrow(()->new IllegalArgumentException("error: MultipartFile -> File convert fail..."));
//        return upload(uploadFile, dirName);
//    }
//
//    public void delete(String url) {
//        try {
//            deleteS3(getSource(URLDecoder.decode(url, "UTF-8")));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public String update(String oldSource, String newSource){
//        try {
//            oldSource = getSource(URLDecoder.decode(oldSource, "UTF-8"));
//            newSource = newSource + UUID.randomUUID() + "_s3" + getFileName(oldSource);
//
//            log.info("old source = {}", oldSource);
//            log.info("new source = {}", newSource);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        String url = moveS3(oldSource, newSource);
//        deleteS3(oldSource);
//
//        return url;
//    }
//
//    //
//    // private
//    //
//
//    // S3 로 파일 업로드하기
//    private String upload(File uploadFile, String dirName) {
//        String fileName = dirName + "/" + UUID.randomUUID()+ "_" + uploadFile.getName(); // S3 에 지정된 파일 이름
//        String uploadImageUrl = putS3(uploadFile, fileName);
//        removeNewFile(uploadFile);
//        return uploadImageUrl;
//    }
//
//    // S3 로 업로드
//    private String putS3(File uploadFile, String fileName) {
//        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
//        return amazonS3Client.getUrl(bucket, fileName).toString();
//    }
//
//    // Local 에 저장된 파일 지우기
//    private void removeNewFile(File target) {
//        if (target.delete()) {
//            log.info("Local File delete success");
//            return;
//        }
//        log.info("Local File delete fail");
//    }
//
//    // Local 에 파일 업로드 하기
//    private Optional<File> convert(MultipartFile file) throws IOException {
//        File convertFile = new File("file" + file.getOriginalFilename());
//        log.info("original name: " + file.getOriginalFilename());
//
//        // 바로 위에서 지정한 경로에 File 이 생성됨
//        if (convertFile.createNewFile()) {
//            // 경로가 잘못되었다면 생성 불가능
//            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
//                // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
//                fos.write(file.getBytes());
//            }
//            log.info("file convert success");
//            return Optional.of(convertFile);
//        }
//        log.info("file convert failed");
//        return Optional.empty();
//    }
//
//    // file 이동 시키기
//    private String moveS3(String oldSource, String newSource) {
//        amazonS3Client.copyObject(bucket, oldSource, bucket, newSource);
//        return amazonS3Client.getUrl(bucket, newSource).toString();
//    }
//
//    // file 삭제하기
//    private void deleteS3(String oldSource) {
//        amazonS3Client.deleteObject(bucket, oldSource);
//    }
//
//    private String getSource(String url) {
//        String[] token = url.split(s3Url);
//        return token[1];
//    }
//
//    private String getFileName(String url) {
//        String[] token = url.split("_s3");
//        return token[1];
//    }
//}
