package com.Unicash.issuehandling.controller;

import com.Unicash.issuehandling.dto.request.SupportIn;
import com.Unicash.issuehandling.dto.response.SupportOut;
import com.Unicash.issuehandling.security.Util;
import com.Unicash.issuehandling.security.meta.IsAdmin;
import com.Unicash.issuehandling.security.other.UserInfo;
import com.Unicash.issuehandling.security.other.UserInfoUserDetailsService;
import com.Unicash.issuehandling.service.SupportService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/support")
@SecurityRequirement(name = "Bearer Authentication")
@PreAuthorize("hasAnyAuthority('SUPPORT', 'ADMIN')")//using @IsAdmin, adminAlreadymentioned
@RequiredArgsConstructor
//@IsAdmin
public class SupportController {

    private final UserInfoUserDetailsService userInfoService;
    private final SupportService supportService;

//    public SupportController(SupportService supportService) {
//        this.supportService = supportService;
//    }

    @PostMapping("/create")
    public SupportOut create(SupportIn sup) {
        userInfoService.saveUser(new UserInfo(null,sup.getUserName(),sup.getPhone(),sup.getEmail(),sup.getPassword(), Util.getRole(sup.getRole())));
        return supportService.create(sup);
    }

    @PutMapping("/update")
    public SupportOut update(SupportIn supportIn) {
        return supportService.update(supportIn);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam("supportId") Long supportId)  {
        supportService.delete(supportId);//hope its always TRUE;
    }

//    @GetMapping("/list")
//    public List<SupportOut> list (@Nullable @RequestParam("start") int start,
//                                  @Nullable @RequestParam("max") int max,
//                                  @Nullable @RequestParam("limit") int limit,
//                                  @Nullable @RequestParam("page") int page) {
//        return supportService.list(start, max,limit,page);
//    }

    @GetMapping("/list")
    public List<SupportOut> list(@RequestParam("page") Integer page,
                                 @RequestParam("pageSize") Integer pageSize) {
        return supportService.list(page, pageSize);
    }
    @GetMapping("/findById")
    public SupportOut findById(@RequestParam("supportId") Long supportId) {
        return supportService.findById(supportId);
    }
    ////////++++++++++//////////////
    @GetMapping("/listByEnterpriseId")
    public List<SupportOut> listByEnterpriseId(@Nullable @RequestParam("enterpriseId") Long enterpriseId,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "3") int pageSize) {
        return supportService.listByEnterpriseId(enterpriseId, page, pageSize);
    }
////////************//////////

    @PutMapping("/assignEnterprises")
    public void assignEnterprises(@RequestParam("supportId") Long supportId,
                                                     @RequestParam("enterpriseIds") List<Long> enterpriseIds) {
        supportService.assignEnterprises(supportId, enterpriseIds);
    }
    @PutMapping("/cancelEnterprises")
    public void cancelEnterprises(@RequestParam("supportId") Long supportId,
                                                     @RequestParam("enterpriseIds") Set<Long> enterpriseIds) {
        supportService.cancelEnterprises(supportId, enterpriseIds);
    }

//    @GetMapping("/tasks")
//    public List<SupportOut> getAllTask(int page, int pageSize, Sort sort) {
//        return supportService.getTasks(page, pageSize, sort);
//    }
}
