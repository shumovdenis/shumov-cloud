package ru.netology.shumovcloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.netology.shumovcloud.entity.FileInfo;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface JpaFileRepository extends JpaRepository<FileInfo, Long> {
    boolean existsByFilenameAndChecksum(String name, String checksum);
    FileInfo findByFilename(String fileName);
    List<FileInfo> findByUserId(Long id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO cloud_storage.file_info (checksum, filename, file_size, upload_date, user_usr_id) VALUES (:checksum, :filename, :file_size, current_timestamp, :user_usr_id)" , nativeQuery = true)
    void addNemFile(@Param("user_usr_id") long user_id, @Param("filename") String filename,@Param ("checksum") String checksum
            , @Param("file_size") long filesize);

    @Modifying
    @Transactional
    @Query(value = "update file_info fi set fi.filename = :newFileName where file_id = :fileID", nativeQuery = true)
    void renameFile(@Param("newFileName") String newFileName, @Param("fileID") Long fileID);


}
