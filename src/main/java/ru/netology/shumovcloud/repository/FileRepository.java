package ru.netology.shumovcloud.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.netology.shumovcloud.entity.FileInfo;

import java.util.List;

@Repository
public class FileRepository {
    private final JdbcTemplate jdbcTemplate;

    public FileRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<FileInfo> getFilesList (int limit, String tableName) {
        String queryStr = "SELECT name FROM " + tableName + limit + " ROWS ONLY";
        List result = jdbcTemplate.query(queryStr, new BeanPropertyRowMapper(FileInfo.class));
        return result;
    }
}
