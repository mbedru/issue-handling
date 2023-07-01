package com.Unicash.issuehandling.controller;

import com.Unicash.issuehandling.dto.request.IssueIn;
import com.Unicash.issuehandling.dto.response.IssueOut;
import com.Unicash.issuehandling.model.Issue;
import com.Unicash.issuehandling.security.meta.IsAdmin;
import com.Unicash.issuehandling.service.IssueService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/issue")
@SecurityRequirement(name = "Bearer Authentication")
//@PreAuthorize("hasAnyAuthority('ADMIN')")
//@IsAdmin
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @PostMapping("/create")
    public IssueOut create(IssueIn issueIn) {
        return issueService.create(issueIn);
    }

    @PutMapping("/update")
    public IssueOut update(IssueIn issueIn) {
        return issueService.update(issueIn);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam("issueId") Long issueId)  {
        issueService.delete(issueId);
    }

    @GetMapping("/list")
    public List<IssueOut> list(@Nullable @RequestParam("page") Integer page,
                                @Nullable @RequestParam("pageSize") Integer pageSize) {
        return issueService.list(page,pageSize);
    }

    @GetMapping("/findById")
    public IssueOut findById(@RequestParam("issueId") Long issueId) {
        return issueService.findById(issueId);
    }

    @GetMapping("/listByEnterpriseId")
    public List<IssueOut> listByEnterpriseId(@RequestParam("parameter") Long entrpriseId,
                                            @Nullable @RequestParam("page") Integer page,
                                            @Nullable @RequestParam("pageSize") Integer pageSize) {
        return issueService.listByEnterpriseId(entrpriseId, page, pageSize);
    }
    @GetMapping("/listBySupportId")
    public List<IssueOut> listBySupportId(@RequestParam("parameter") Long supportId,
                                          @Nullable @RequestParam("page") Integer page,
                                          @Nullable @RequestParam("pageSize") Integer pageSize) {
        return issueService.listBySupportId(supportId, page, pageSize);
    }
    @GetMapping("/listByIssueTypeId")
    public List<IssueOut> listByIssueTypeId(@RequestParam("parameter") Long issueTypeId,
                                            @Nullable @RequestParam("page") Integer page,
                                            @Nullable @RequestParam("pageSize") Integer pageSize) {
        return issueService.listByIssueTypeId(issueTypeId, page, pageSize);
    }

    @GetMapping("/listByStatusId")
    public List<IssueOut> listByStatusId(@RequestParam("parameter") Long statusId,
                                         @Nullable @RequestParam("page") Integer page,
                                         @Nullable @RequestParam("pageSize") Integer pageSize) {
        return issueService.listByStatusId(statusId, page, pageSize);
    }

}
