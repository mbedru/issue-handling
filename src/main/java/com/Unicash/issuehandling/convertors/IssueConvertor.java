package com.Unicash.issuehandling.convertors;

import com.Unicash.issuehandling.dto.response.IssueOut;
import com.Unicash.issuehandling.model.Issue;

import java.util.ArrayList;
import java.util.List;

public class IssueConvertor {

    public static IssueOut toIssueOut(Issue issue) {
        IssueOut issueOut = new IssueOut();
        issueOut.setId(issue.getId());
        issueOut.setDescription(issue.getDescription());
        issueOut.setEnterprise(issue.getEnterprise().getName());
        issueOut.setModule(issue.getEnterprise().getModule().getName());
        issueOut.setSupportUserName(issue.getSupport().getUsername());
        issueOut.setIssueType(issue.getIssueType().getName());
        issueOut.setStatus(issue.getStatus().getName());
        return issueOut;
    }

    public static List<IssueOut> toIssueOutList(List<Issue> issues) {
        List<IssueOut> issueOutList = new ArrayList<>();
        issues.stream().forEach(issue -> {
            issueOutList.add(toIssueOut(issue));
        });
        return issueOutList;
    }
}
