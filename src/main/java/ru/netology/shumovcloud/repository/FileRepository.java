package ru.netology.shumovcloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.netology.shumovcloud.entity.FileInfo;

@Repository
public interface FileRepository extends JpaRepository<FileInfo, Long> {
    boolean existsByNameAndChecksum(String name, String checksum);
}
