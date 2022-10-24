package com.vira.vpm.issuetrackerservice.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import com.vira.vpm.issuetrackerservice.enums.ProjectTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class Project {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "color")
    private String color;

    @Column(name = "users")
    @ElementCollection
    private List<String> users;

    @Column(name = "responsible")
    private String responsible;

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Issue> issues;

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Sprint> sprints;

    @Column(name = "type")
    private ProjectTypeEnum type;

    @CreationTimestamp
    @Column(name = "creation_date")
    private Date creationDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    private Date updateDate;
}
