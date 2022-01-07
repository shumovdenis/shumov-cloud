package ru.netology.shumovcloud.service.impl;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.shumovcloud.entity.FileInfo;
import ru.netology.shumovcloud.entity.User;
import ru.netology.shumovcloud.exceptions.FileNotUniqException;
import ru.netology.shumovcloud.repository.FileRepository;
import ru.netology.shumovcloud.service.FileService;
import ru.netology.shumovcloud.service.UserService;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@NoArgsConstructor
public class FileServiceImpl implements FileService {
    private FileRepository fileRepository;
    private UserService userService;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public FileServiceImpl(FileRepository fileRepository, UserService userService) {
        this.fileRepository = fileRepository;
        this.userService = userService;
    }

    @Override
    public void uploadFile(MultipartFile file, User user) throws FileNotUniqException, IOException {
        if(file != null) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
        }

        String checksumMD5 = DigestUtils.md5Hex(file.getBytes());
        if(!fileRepository.existsByNameAndChecksum(file.getOriginalFilename(), checksumMD5)) {
            file.transferTo(new File(uploadPath + "/" + file.getOriginalFilename()));
        } else {
            throw new FileNotUniqException("Такой файл уже существует");
        }

        FileInfo fileInfo = new FileInfo().builder()
                .name(file.getOriginalFilename())
                .size(file.getSize())
                .uploadDate(LocalDate.now())
                .checksum(checksumMD5)
                .user(user)
                .build();
        fileRepository.save(fileInfo);
    }

    @Override
    public List<FileInfo> listAllFiles() {
        return new ArrayList<>(fileRepository.findAll());
    }

    @Override
    public void update(String fileName, String newFileName) {
        Long fileID = fileRepository.findByName(fileName).getId();
        FileInfo fileInfo = fileRepository.findById(fileID)
                .orElseThrow(() -> new EntityNotFoundException("Файл не найден"));
        File file = new File(uploadPath + "/" + fileInfo.getName());
        file.renameTo(new File(uploadPath + "/" + newFileName));
        fileInfo.setName(newFileName);
        fileRepository.save(fileInfo);
    }

    @Override
    public void delete(String fileName) {
        Long fileID = fileRepository.findByName(fileName).getId();
        FileInfo fileInfo = fileRepository.findById(fileID)
                .orElseThrow(() -> new EntityNotFoundException("Файл не найден"));
        fileRepository.delete(fileInfo);
        File file = new File(uploadPath + "/" + fileInfo.getName());
        file.delete();
        log.info("IN FileService - file: {} successfully delete", fileName);
    }

    @Override
    public void download(String fileName, HttpServletResponse response) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            FileInputStream fis = new FileInputStream(uploadPath + "/" + fileName);
            int len;
            byte[] buf = new byte[1024];
            while ((len = fis.read(buf)) > 0) {
                bos.write(buf,0,len);
            }
            bos.close();
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}