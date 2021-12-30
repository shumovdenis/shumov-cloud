package ru.netology.shumovcloud.controller;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.shumovcloud.entity.FileInfo;
import ru.netology.shumovcloud.entity.User;
import ru.netology.shumovcloud.exceptions.FileNotUniqException;
import ru.netology.shumovcloud.repository.UserRepository;
import ru.netology.shumovcloud.service.FileService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@RestController
public class FileController {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private FileService fileService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/list")
    public List<FileInfo> listAllFiles() {
        return fileService.listAllFiles();
    }

    @PostMapping("/file")
    public void uploadFile(@AuthenticationPrincipal User user,
            @RequestParam("file")MultipartFile file) throws IOException, FileNotUniqException {
        fileService.uploadFile(file);
    }

    @PutMapping("/file")
    public void update(@RequestParam("id") long id, @RequestParam String newFileName) {
        fileService.update(id, newFileName);
    }

    @DeleteMapping("/file")
    public void delete(@RequestParam("id") long id) {
        fileService.delete(id);
    }

    @GetMapping("/dwnl")
    @ResponseBody
    public void download(@RequestParam("fileName") String fileName, HttpServletResponse response) {
        fileService.download(fileName, response);
    }

}
