package ru.netology.shumovcloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.netology.shumovcloud.entity.FileInfo;
import ru.netology.shumovcloud.entity.User;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileInfo, Long> {
    boolean existsByNameAndChecksum(String name, String checksum);
    FileInfo findByName(String fileName);

    @Query(value = "Select f.name, f.upload_date, f.file_size from usr_file_infos ufi inner join file_info f on ufi.file_infos_file_id where ufi.user_usr_id = ?", nativeQuery = true)


    //@Query(value = "Select fileinfo1_.name as name3_0_1_, fileinfo1_.upload_date as upload_d5_0_1_, fileinfo1_.file_size as file_siz4_0_1_ from usr_file_infos fileinfos0_ inner join file_info fileinfo1_ on fileinfos0_.file_infos_file_id=fileinfo1_.file_id where fileinfos0_.user_usr_id=1", nativeQuery = true)
    List<FileInfo> custom (Long user_usr_id);



}
