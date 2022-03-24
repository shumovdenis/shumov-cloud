package ru.netology.shumovcloud.service;

import org.springframework.web.multipart.MultipartFile;
import ru.netology.shumovcloud.entity.FileInfo;
import ru.netology.shumovcloud.exceptions.FileNotUniqException;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface FileService {
    String upload(MultipartFile file, String filename, String authToken) throws FileNotUniqException, IOException;

    List<FileInfo> getFiles(int limit, String token);

    String update(String fileName, String newFileName, String token);

    String deleteFile(String fileName, String token);

    void downloadFile(String fileName, HttpServletResponse response, String token);

}
