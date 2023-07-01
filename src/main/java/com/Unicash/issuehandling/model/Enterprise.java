package com.Unicash.issuehandling.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Enterprise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private Module module;

    @JsonIgnoreProperties("enterprises")
    @ManyToMany
    @JoinTable(name = "support_enterprise",
            joinColumns = @JoinColumn(name = "enterprise_id"),
            inverseJoinColumns = @JoinColumn(name = "support_id")
    )
    private List<Support> supports = new ArrayList<>();

    @JsonIgnore
    @CreationTimestamp
    private Date createdOn;
    @JsonIgnore
    @UpdateTimestamp
    private Date updatedOn;

}

/*
    @JsonIgnoreProperties("enterprises")
    @ManyToMany(mappedBy = "enterprises")
    private List<Support> supports = new ArrayList<>();
*/