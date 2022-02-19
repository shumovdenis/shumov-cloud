package ru.netology.shumovcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.shumovcloud.entity.FileInfo;
import ru.netology.shumovcloud.entity.User;
import ru.netology.shumovcloud.exceptions.FileNotUniqException;
import ru.netology.shumovcloud.service.AuthService;
import ru.netology.shumovcloud.service.UserService;
import ru.netology.shumovcloud.service.impl.FileServiceImpl;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class FileController {

    @Value("${upload.path}")
    private String uploadPath;

    private final UserService userService;

    private final AuthService authService;

    private final FileServiceImpl fileService;

    @Autowired
    public FileController(FileServiceImpl fileService, AuthService authService, UserService userService) {
        this.fileService = fileService;
        this.authService = authService;
        this.userService = userService;
    }

    @GetMapping("/file")
    public List<FileInfo> getFiles() {
        return fileService.getFiles();
    }

    @PostMapping("/file")
    public void uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("filename") String filename
    ) throws IOException, FileNotUniqException {
        //User user = userService.findByEmail(authService.getAuthInfo().getEmail());
        fileService.uploadFile(file, filename, new User());
    }

    @PutMapping("/file")
    public void update(@RequestParam("fileName") String filename,
                       @RequestParam("fileData") String newFileName) {
        fileService.update(filename, newFileName);
    }

    @DeleteMapping("/file")
    public void deleteFile(@RequestParam("fileName") String fileName) {
        fileService.deleteFile(fileName);
    }

    @GetMapping("/downloadFile")
    @ResponseBody
    public void downloadFile(@RequestParam("fileName") String fileName, HttpServletResponse response) {
        fileService.downloadFile(fileName, response);
    }

}
