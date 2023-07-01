package com.Unicash.issuehandling.controller;

import com.Unicash.issuehandling.dto.request.IssueTypeIn;
import com.Unicash.issuehandling.dto.response.IssueTypeOut;
import com.Unicash.issuehandling.model.IssueType;
import com.Unicash.issuehandling.service.IssueTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/issueType")
//@PreAuthorize("hasAuthority('ADMIN')")
public class IssueTypeController {

    private final IssueTypeService issueTypeService;

    public IssueTypeController(IssueTypeService issueTypeService) {
        this.issueTypeService = issueTypeService;
    }


    @PostMapping("/create")
    public IssueTypeOut create(IssueTypeIn issueTypeIn) {
        return issueTypeService.create(issueTypeIn);
    }

    @PutMapping("/update")
    public IssueTypeOut update(IssueTypeIn issueTypeIn) {
        return issueTypeService.update(issueTypeIn);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam("issueTypeId") Long issueTypeId)  {
        issueTypeService.delete(issueTypeId);
    }

    @GetMapping("/list")
    public List<IssueTypeOut> list(@RequestParam("page") int page,
                                   @RequestParam("pageSize") int pageSize) {
        return issueTypeService.list(page, pageSize);
    }

    @GetMapping("/findById")
    public IssueTypeOut findById(@RequestParam("issueTypeId") Long issueTypeId) {
        return issueTypeService.findById(issueTypeId);
    }

}
