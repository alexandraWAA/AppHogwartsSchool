package com.example.ahs.service;

import com.example.ahs.dto.AvatarDTO;
import com.example.ahs.model.Avatar;
import com.example.ahs.model.Student;
import com.example.ahs.repository.AvatarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarService {
    @Value("${application.avatars.folder}")
    private String avatarsDir;
    private final StudentService studentService;
    private final AvatarRepository avatarRepository;
    public static final Logger logger = LoggerFactory.getLogger(AvatarService.class);
    public AvatarService(StudentService studentService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }

    public void updateAvatar(Long id, MultipartFile file) throws IOException {
        Student student = studentService.getStudentById(id).toStudent();
        logger.info("Uploading avatar for student with id: " + id);
        Path filePath = Path.of(avatarsDir, id + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(is, 1024);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(os, 1024)
        ) {
            bufferedInputStream.transferTo(bufferedOutputStream);
        }

        AvatarDTO avatarDTO = AvatarDTO.fromAvatar(getAvatar(id));

        avatarDTO.setFilePath(filePath.toString());
        avatarDTO.setFileSize(file.getSize());
        avatarDTO.setMediaType(file.getContentType());
        avatarDTO.setData(file.getBytes());
        avatarDTO.setStudentId(student.getId());

        Avatar avatar = avatarDTO.toAvatar();
        avatar.setStudent(student);
        avatarRepository.save(avatar);
        logger.info("Avatar for student with id: " + id + " upload successfully");
    }

    public Avatar getAvatar(Long id) {
        logger.info("Getting avatar for student with id " + id);
        return avatarRepository.findAvatarByStudentId(id).orElse(new Avatar());
    }

    public String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
