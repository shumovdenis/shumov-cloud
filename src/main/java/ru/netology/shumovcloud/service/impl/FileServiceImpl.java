package ru.netology.shumovcloud.service.impl;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.shumovcloud.config.jwt.JwtTokenProvider;
import ru.netology.shumovcloud.entity.FileInfo;
import ru.netology.shumovcloud.exceptions.ErrorGettingFileList;
import ru.netology.shumovcloud.exceptions.ErrorInputData;
import ru.netology.shumovcloud.exceptions.ErrorUnauthorized;
import ru.netology.shumovcloud.exceptions.FileNotUniqException;
import ru.netology.shumovcloud.repository.JpaFileRepository;
import ru.netology.shumovcloud.repository.UserRepository;
import ru.netology.shumovcloud.service.FileService;
import ru.netology.shumovcloud.service.UserService;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;


@Service
@Slf4j
@NoArgsConstructor
public class FileServiceImpl implements FileService {
    private JpaFileRepository jpaFileRepository;
    private UserService userService;
    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;

    final static String SEP = File.separator;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public FileServiceImpl(JpaFileRepository jpaFileRepository, UserService userService, UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.jpaFileRepository = jpaFileRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String upload(MultipartFile file, String filename, String token)
            throws FileNotUniqException, IOException {

        String checksumMD5 = DigestUtils.md5Hex(file.getBytes());
        String userName = getUserName(token);
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(getFullPath(filename,userName))));
                stream.write(bytes);
                stream.close();
                jpaFileRepository.addNemFile(getUserId(userName), filename,checksumMD5, file.getSize());

            } catch (Exception e) {
                log.info("File upload error,", filename, userName);
                throw new ErrorInputData("Service said: Error input data!");
            }
        }
        log.info(String.format("Method 'uploadFile' completed successfully. File: %s  User: %s", filename, userName));
        return "OK";
    }

    @Override
    public List<FileInfo> getFiles(int limit, String token) {

        String login = jwtTokenProvider.getUserName(token);
        Long id = userRepository.findByLogin(login).getId();

        try {
            List<FileInfo> result = jpaFileRepository.findByUserId(id);
            System.out.println(result);
            log.info("IN FileService - getFiles was successfully executed");
            return result.subList(0, Math.min(limit, result.size()));
        } catch (Exception e) {
            throw new ErrorGettingFileList("Error getting file list");
        }
    }

    @Override
    public String update(String fileName, String newFileName, String token) {
        try {
            FileInfo fileInfo = jpaFileRepository.findByFilename(fileName);
            jpaFileRepository.renameFile(newFileName, fileInfo.getId());
            File file = new File(getFullPath(fileName, getUserName(token))).getAbsoluteFile();
            file.renameTo(new File(getFullPath(newFileName, getUserName(token))).getAbsoluteFile());
        } catch (Exception e) {
            log.info("Service: the update method has an error");
            throw new EntityNotFoundException("Entity not found");
        }
        return "OK";
    }

    @Override
    public String deleteFile(String fileName, String token) {
        String username = getUserName(token);
        Long fileID = jpaFileRepository.findByFilename(fileName).getId();
        FileInfo fileInfo = jpaFileRepository.findById(fileID)
                .orElseThrow(() -> new EntityNotFoundException("File not found"));
        try {
            jpaFileRepository.delete(fileInfo);
            File file = new File(getFullPath(fileName, username)).getAbsoluteFile();
            file.delete();
        } catch (Exception e) {
            log.info("Method 'deleteFile' has exception ErrorInputData");
            throw new ErrorInputData("FileService: Error delete file");
        }

        log.info("Service: file - {} successfully delete", fileName);
        return "OK";
    }

    @Override
    public void downloadFile(String fileName, HttpServletResponse response) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            FileInputStream fis = new FileInputStream(getFullPath(fileName, "123") );
            int len;
            byte[] buf = new byte[1024];
            while ((len = fis.read(buf)) > 0) {
                bos.write(buf,0,len);
            }
            bos.close();
            response.flushBuffer();
        } catch (IOException e) {
            throw new ErrorInputData("Error input data file: " + fileName);
        }
        log.info("Service: file - {} successfully download", fileName);
    }

    private String getUserName(String token) {
        String username;
        try {
            username = jwtTokenProvider.getUserName(token);
        } catch (Exception e) {
            log.info("Method 'getUsername' thrown exception ", e.toString());
            throw new ErrorUnauthorized("Service: Unauthorized error!");
        }
        return username;
    }

    private Long getUserId(String username) {
        Long id;
        try {
            id = userRepository.findByLogin(username).getId();
        } catch (Exception e) {
            throw new ErrorUnauthorized("Service: Unauthorized error!");
        }
        return id;
    }

    private String getFullPath(String fileName, String userName) {
        StringBuilder newFilePath = new StringBuilder("cloud" + SEP + "users" + SEP)
                .append(userName)
                .append(SEP)
                .append(fileName);
        return newFilePath.toString();
    }

}
