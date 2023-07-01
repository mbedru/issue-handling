package com.Unicash.issuehandling.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @ManyToOne
    @JsonIgnoreProperties(value = "supports")//added now
    private Enterprise enterprise;

    @ManyToOne
    @JsonIgnoreProperties(value = "enterprises")//added now
    private Support support;

    @ManyToOne
    private IssueType issueType;

    @ManyToOne
    private Status status;

    @JsonIgnore
    @CreationTimestamp
    private Date createdOn;
    @JsonIgnore
    @UpdateTimestamp
    private Date updatedOn;
    
}
