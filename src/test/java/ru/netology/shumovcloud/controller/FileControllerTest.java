package ru.netology.shumovcloud.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.shumovcloud.dto.Login;
import ru.netology.shumovcloud.dto.NewFileName;
import ru.netology.shumovcloud.dto.Token;
import ru.netology.shumovcloud.entity.FileInfo;
import ru.netology.shumovcloud.exceptions.FileNotUniqException;
import ru.netology.shumovcloud.service.AuthService;
import ru.netology.shumovcloud.service.FileService;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileControllerTest {
    AuthService mockAuthService = Mockito.mock(AuthService.class);
    FileService mockFileService = Mockito.mock(FileService.class);
    Login testlogin = new Login("username", "password");

    @Test
    public void testlogin() throws AuthException {
        Mockito.when(mockAuthService.getToken(testlogin)).thenReturn("Bearer testToken");
        ResponseEntity<Token> actual = new FileController(mockFileService, mockAuthService).login(testlogin);
        ResponseEntity<Token> expected = new ResponseEntity<Token>(new Token("Bearer testToken"), HttpStatus.OK);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testLogout() {
        Mockito.when(mockAuthService.removeToken("test")).thenReturn(true);
        ResponseEntity<String> actual = new FileController(mockFileService, mockAuthService).logout("test");
        ResponseEntity<Token> expected = new ResponseEntity<Token>(HttpStatus.OK);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testList() {
        List<FileInfo> testList = new ArrayList<>();
        testList.add(new FileInfo());
        testList.add(new FileInfo());
        testList.add(new FileInfo());
        Mockito.when(mockFileService.getFiles(3, "test")).thenReturn(testList);
        ResponseEntity<List<FileInfo>> actual = new FileController(mockFileService, mockAuthService).list("Bearer test", 3);
        ResponseEntity<List<FileInfo>> expected = new ResponseEntity<>(testList, HttpStatus.OK);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testDelete() {
        Mockito.when(mockFileService.deleteFile("test.png", "test")).thenReturn("OK");
        ResponseEntity<String> actual = new FileController(mockFileService, mockAuthService)
                .deleteFile("Bearer test", "test.png");
        ResponseEntity<String> expected = new ResponseEntity<>("Delete success", HttpStatus.OK);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testUpdate() {
        NewFileName newFileName = new NewFileName("newTest.png");
        Mockito.when(mockFileService.update("test.png", newFileName.getFilename(), "test")).thenReturn("OK");
        ResponseEntity<String> actual = new FileController(mockFileService, mockAuthService)
                .update("Bearer test", "test.png", newFileName);
        ResponseEntity<String> expected = new ResponseEntity<>("Update success", HttpStatus.OK);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void testUpload() throws FileNotUniqException, IOException {
        MultipartFile multipartFile = new MockMultipartFile("test.png", "test".getBytes(StandardCharsets.UTF_8));
        Mockito.when(mockFileService.upload(multipartFile, "test.png", "test")).thenReturn("OK");
        ResponseEntity<String> actual = new FileController(mockFileService, mockAuthService)
                .upload("Bearer test", "test.png", multipartFile);
        ResponseEntity<String> expected = new ResponseEntity<>("Upload success",HttpStatus.OK);
        Assertions.assertEquals(actual, expected);
    }


}
