package com.Unicash.issuehandling.model;

import com.Unicash.issuehandling.dto.response.SupportOut;
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
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Support {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String firstName;
    private  String lastName;
    private String username;
    @JsonIgnoreProperties("supports")
    @ManyToMany
    @JoinTable(name = "support_enterprise",
            joinColumns = @JoinColumn(name = "support_id"),
            inverseJoinColumns = @JoinColumn(name = "enterprise_id") )
    private List<Enterprise> enterprises = new ArrayList<>();

    @JsonIgnore
    @CreationTimestamp
    private Date createdOn;
    @JsonIgnore
    @UpdateTimestamp
    private Date updatedOn;

    public SupportOut supportOut() {
        return SupportOut.builder()
                .id(id)
//                .name(name)
//                .phone(phone)
                .build();

    }

    public SupportOut support() {
        SupportOut supportOut = new SupportOut();
        supportOut.setId(id);
//        supportOut.setName(name);
//        supportOut.setPhone(phone);
        //supportOut.setEnterprises(enterprises.stream().map(Enterprise::getSupports).collect(Collectors.toList()));
        return supportOut;
    }

}
