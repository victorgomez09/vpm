package com.vira.vpm.kanbanservice.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "columns")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class Column {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @javax.persistence.Column(name = "id")
    private String id;

    @javax.persistence.Column(name = "name")
    private String name;

    @javax.persistence.Column(name = "order")
    private int order;

    @javax.persistence.Column(name = "order")
    private List<String> users;

    @ManyToOne
    @JoinColumn(name = "board")
    private Board board;

    @OneToMany(mappedBy = "cards", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> cards;

    @CreationTimestamp
    @javax.persistence.Column(name = "creation_date")
    private Date creationDate;

    @UpdateTimestamp
    @javax.persistence.Column(name = "update_date")
    private Date updateDate;
}
