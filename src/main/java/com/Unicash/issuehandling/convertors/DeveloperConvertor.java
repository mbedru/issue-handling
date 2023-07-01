package com.Unicash.issuehandling.convertors;

import com.Unicash.issuehandling.dto.response.DeveloperOut;
import com.Unicash.issuehandling.model.Developer;

import java.util.ArrayList;
import java.util.List;

public class DeveloperConvertor {

    public static DeveloperOut toDeveloperOut(Developer developer) {
        DeveloperOut developerOut = new DeveloperOut();
        developerOut.setId(developer.getId());
        developerOut.setUsername(developer.getUsername());
        developerOut.setFirstName(developer.getFirstName());
        developerOut.setLastName(developer.getLastName());
        developerOut.setModule(developer.getModule().getName());
        return developerOut;
    }

    public static List<DeveloperOut> toDeveloperOutList(List<Developer> developers) {
        List<DeveloperOut> developerOutList = new ArrayList<>();
        developers.stream().forEach(developer -> {
            developerOutList.add(toDeveloperOut(developer));
        });
        return developerOutList;
    }
}
