package ru.netology.shumovcloud.service;

import org.springframework.web.multipart.MultipartFile;
import ru.netology.shumovcloud.entity.FileInfo;
import ru.netology.shumovcloud.entity.User;
import ru.netology.shumovcloud.exceptions.FileNotUniqException;
import ru.netology.shumovcloud.security.jwt.JwtUser;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface FileService {
    void uploadFile(MultipartFile file, JwtUser
            user) throws FileNotUniqException, IOException;

    List<FileInfo> listAllFiles();

    void update(String fileName, String newFileName);

    void delete(String fileName);

    void download(String fileName, HttpServletResponse response);

}
