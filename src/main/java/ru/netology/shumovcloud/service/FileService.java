package ru.netology.shumovcloud.service;

import org.springframework.web.multipart.MultipartFile;
import ru.netology.shumovcloud.entity.FileInfo;
import ru.netology.shumovcloud.entity.User;
import ru.netology.shumovcloud.exceptions.FileNotUniqException;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface FileService {
    void uploadFile(MultipartFile file, String filename, User user) throws FileNotUniqException, IOException;

    List<String> getFiles(int limit, String token);

    void update(String fileName, String newFileName);

    void deleteFile(String fileName);

    void downloadFile(String fileName, HttpServletResponse response);

}
