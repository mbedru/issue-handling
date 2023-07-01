package com.Unicash.issuehandling.convertors;

import com.Unicash.issuehandling.dto.response.EnterpriseOut;
import com.Unicash.issuehandling.model.Enterprise;

import java.util.ArrayList;
import java.util.List;

public class EnterpriseConvertor {

    public static EnterpriseOut toEnterpriseOut(Enterprise enterprise) {
        EnterpriseOut enterpriseOut = new EnterpriseOut();
        enterpriseOut.setId(enterprise.getId());
        enterpriseOut.setName(enterprise.getName());
        enterpriseOut.setModule(enterprise.getModule().getName());
        return enterpriseOut;
    }

    public static List<EnterpriseOut> toEnterpriseOutList(List<Enterprise> enterprises) {
        List<EnterpriseOut> enterpriseOutList = new ArrayList<>();
        enterprises.stream().forEach(enterprise -> {
            enterpriseOutList.add(toEnterpriseOut(enterprise));
        });
        return enterpriseOutList;
    }
}
