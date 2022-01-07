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
import ru.netology.shumovcloud.service.impl.FileServiceImpl;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@NoArgsConstructor
@RestController
public class FileController {

    @Value("${upload.path}")
    private String uploadPath;

    private FileServiceImpl fileServiceImpl;

    @Autowired
    public FileController(FileServiceImpl fileServiceImpl) {
        this.fileServiceImpl = fileServiceImpl;
    }

    @GetMapping("/list")
    public List<FileInfo> listAllFiles() {
        return fileServiceImpl.listAllFiles();
    }

    @PostMapping("/file")
    public void uploadFile(@AuthenticationPrincipal User user,
            @RequestParam("file")MultipartFile file) throws IOException, FileNotUniqException {
        fileServiceImpl.uploadFile(file, user);
    }

    @PutMapping("/file")
    public void update(@RequestParam("fileName") String filename, @RequestParam String newFileName) {
        fileServiceImpl.update(filename, newFileName);
    }

    @DeleteMapping("/file")
    public void delete(@RequestParam("fileName") String fileName) {
        fileServiceImpl.delete(fileName);
    }

    @GetMapping("/dwnl")
    @ResponseBody
    public void download(@RequestParam("fileName") String fileName, HttpServletResponse response) {
        fileServiceImpl.download(fileName, response);
    }

}
