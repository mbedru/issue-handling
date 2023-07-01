package com.Unicash.issuehandling.convertors;

import com.Unicash.issuehandling.dto.response.IssueOut;
import com.Unicash.issuehandling.dto.response.IssueTypeOut;
import com.Unicash.issuehandling.model.Issue;
import com.Unicash.issuehandling.model.IssueType;

import java.util.ArrayList;
import java.util.List;

public class IssueTypeConvertor {

    public static IssueTypeOut toIssueTypeOut(IssueType issueType) {
        IssueTypeOut issueTypeOut = new IssueTypeOut();
        issueTypeOut.setId(issueType.getId());
        issueTypeOut.setName(issueType.getName());
        return issueTypeOut;
    }

    public static List<IssueTypeOut> toIssueTypeOutList(List<IssueType> issueTypes) {
        List<IssueTypeOut> issueTypeOutList = new ArrayList<>();
        issueTypes.stream().forEach(issueType -> {
            issueTypeOutList.add(toIssueTypeOut(issueType));
        });
        return issueTypeOutList;
    }
}
