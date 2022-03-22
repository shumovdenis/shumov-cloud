package ru.netology.shumovcloud.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class FileInfo implements Serializable {

    @Id
    @Column(name = "file_id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String filename;

    @Column(name = "file_size",nullable = false)
    private int size;

    @Column(nullable = false)
    private Date uploadDate;

    @Column(nullable = false)
    private String checksum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    @Override
    public String toString() {
        return filename + " " + uploadDate + " " + size;
    }
}
