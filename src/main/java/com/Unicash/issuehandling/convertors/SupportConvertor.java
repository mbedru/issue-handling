package com.Unicash.issuehandling.convertors;

import com.Unicash.issuehandling.dto.response.SupportOut;
import com.Unicash.issuehandling.model.Enterprise;
import com.Unicash.issuehandling.model.Support;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SupportConvertor {

    public static SupportOut toSupportOut(Support support) {
        SupportOut supportOut = new SupportOut();
        supportOut.setId(support.getId());
        supportOut.setUsername(support.getUsername());
        supportOut.setFirstName(support.getFirstName());
        supportOut.setLastName(support.getLastName());
        supportOut.setEnterprises(
                support.getEnterprises().stream()
                        .map(Enterprise::getName)
                        .collect(Collectors.toList()));

        return supportOut;
    }

    public static List<SupportOut> toSupportOutList(List<Support> supports) {
        List<SupportOut> supportOutList = new ArrayList<>();
        supports.forEach(support -> {
            supportOutList.add(toSupportOut(support));
        });
        return supportOutList;
    }

}
