package com.dingqi.pojo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "note")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
@Data
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String abs;
    private String name;
    @Column(name = "content_html")
    private String contentHtml;
    @Column(name = "content_md")
    private String contentMd;

    @ManyToOne
    @JoinColumn(name = "author")
    private User author;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @Column(name = "created_time")
    private Timestamp createdTime;

    @Column(name = "last_modified_time")
    private Timestamp lastModifiedTime;
}
