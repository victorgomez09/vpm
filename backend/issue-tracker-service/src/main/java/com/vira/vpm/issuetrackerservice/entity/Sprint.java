package com.vira.vpm.issuetrackerservice.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;

@Entity
@Table(name = "sprints")
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Sprint {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "objective")
    private String objective;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Issue> issues;

    @OneToOne(mappedBy = "sprint", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project")
    private Project project;

    @CreationTimestamp
    @Column(name = "creation_date")
    private Date creationDate;

    @UpdateTimestamp
    @Column(name = "update_date")
    private Date updateDate;
}
