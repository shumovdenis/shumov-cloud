package ru.netology.shumovcloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.netology.shumovcloud.entity.FileInfo;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileInfo, Long> {
    boolean existsByNameAndChecksum(String name, String checksum);
    FileInfo findByName(String fileName);
    FileInfo findByUserId

    //@Query(value = "SELECT 'name', 'upload_date', 'file_size' FROM file_info WHERE usr_id = 1", nativeQuery = true)
    //@Query(value = "SELECT * FROM file_info WHERE 'usr_id' = 1", nativeQuery = true)
    //@Query("SELECT f.name, f.uploadDate, f.size FROM FileInfo f WHERE User.id=1")

    //@Query(value = "SELECT f.name, f.upload_date, f.file_size FROM file_info f WHERE f.usr_id = 1 ", nativeQuery = true)

    //@Query(value = "SELECT f.name FROM file_info f WHERE usr_id = 1", nativeQuery = true)
    @Query(value = "select name from file_info where 'usr_id' = 1 ", nativeQuery = true)

    //@Query(value = "Select f.name, f.upload_date, f.file_size from file_info f join usr u on u.usr_id = f.usr_id", nativeQuery = true)
    List<String> custom (Long usr_id);

}
