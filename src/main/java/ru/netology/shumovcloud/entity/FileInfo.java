package ru.netology.shumovcloud.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FileInfo implements Serializable {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "file_size",nullable = false)
    private long size;

    @Column(nullable = false)
    private LocalDate uploadDate;

    @Column(nullable = false)
    private String checksum;

    @ManyToOne (optional=false, fetch = FetchType.EAGER)
    @JoinColumn (name="usr_id")
    private User user;
}
