package com.Unicash.issuehandling.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EnterpriseIn {

    private Long id;
    private String name;
    private Long moduleId;
    private List<Long> supportIds = new ArrayList<>();
}
