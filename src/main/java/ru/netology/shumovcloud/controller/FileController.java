package ru.netology.shumovcloud.controller;

import lombok.NoArgsConstructor;
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

@NoArgsConstructor
@RestController
public class FileController {

    @Value("${upload.path}")
    private String uploadPath;

    private UserService userService;

    private AuthService authService;

    private FileServiceImpl fileServiceImpl;

    @Autowired
    public FileController(FileServiceImpl fileServiceImpl, AuthService authService, UserService userService) {
        this.fileServiceImpl = fileServiceImpl;
        this.authService = authService;
        this.userService = userService;
    }

    @GetMapping("/file")
    public List<FileInfo> getFiles() {
        return fileServiceImpl.getFiles();
    }

    @PostMapping("/file")
    public void uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("filename") String filename
    ) throws IOException, FileNotUniqException {
        User user = userService.findByEmail(authService.getAuthInfo().getEmail());
        fileServiceImpl.uploadFile(file, filename, user);
    }

    @PutMapping("/file")
    public void update(@RequestParam("fileName") String filename,
                       @RequestParam("fileData") String newFileName) {
        fileServiceImpl.update(filename, newFileName);
    }

    @DeleteMapping("/file")
    public void deleteFile(@RequestParam("fileName") String fileName) {
        fileServiceImpl.deleteFile(fileName);
    }

    @GetMapping("/downloadFile")
    @ResponseBody
    public void downloadFile(@RequestParam("fileName") String fileName, HttpServletResponse response) {
        fileServiceImpl.downloadFile(fileName, response);
    }

}
