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

    @Query(value = "Select f.name, f.upload_date, f.file_size from usr_file_infos ufi inner join file_info f on ufi.file_infos_id where ufi.user_usr_id = ?", nativeQuery = true)
    List<FileInfo> custom (Long user_usr_id);

}
