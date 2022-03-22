package ru.netology.shumovcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.shumovcloud.dto.Login;
import ru.netology.shumovcloud.dto.NewFileName;
import ru.netology.shumovcloud.dto.Token;
import ru.netology.shumovcloud.entity.FileInfo;
import ru.netology.shumovcloud.exceptions.FileNotUniqException;
import ru.netology.shumovcloud.service.AuthService;
import ru.netology.shumovcloud.service.UserService;
import ru.netology.shumovcloud.service.impl.FileServiceImpl;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http//localhost:8080")
public class FileController {

    private final UserService userService;

    private final AuthService authService;

    private final FileServiceImpl fileService;

    @Autowired
    public FileController(FileServiceImpl fileService, AuthService authService, UserService userService) {
        this.fileService = fileService;
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody Login login) throws AuthException {
        String res = authService.getToken(login);
        System.out.println(res);
        return new ResponseEntity<>( new Token(res), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("auth-token") String authToken){
        authService.removeToken(authToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<FileInfo>> list(@RequestHeader("auth-token") String authToken,
                                               @RequestParam("limit") int limit) {
        String token = authToken.substring(7);
        List<FileInfo> list = fileService.getFiles(limit, token);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/file")
    public ResponseEntity<String> upload(@RequestHeader("auth-token") String authToken,
                           @RequestParam("filename") String filename,
                           MultipartFile file) throws IOException, FileNotUniqException {
        String result = fileService.upload(file, filename, authToken.substring(7));
        if (result.equals("OK")) {
            return new ResponseEntity<>("Upload success", HttpStatus.OK);
            } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/file")
    public ResponseEntity<String> update(@RequestHeader("auth-token") String authToken,
                       @RequestParam("filename") String filename,
                       @RequestBody NewFileName newFileName) {
        String result = fileService.update(filename, newFileName.getFilename(), authToken.substring(7));
        if (result.equals("OK")) {
            return new ResponseEntity<>("Rename success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/file")
    public ResponseEntity<String> deleteFile(@RequestHeader("auth-token") String authToken,
                                             @RequestParam("filename") String fileName) {
        String result = fileService.deleteFile(fileName, authToken.substring(7));
        if (result.equals("OK")) {
            return new ResponseEntity<>("Delete success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/file")
    @ResponseBody
    public ResponseEntity<HttpServletResponse> downloadFile(@RequestParam("filename") String fileName,
                                                            HttpServletResponse response) {
        fileService.downloadFile(fileName, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
