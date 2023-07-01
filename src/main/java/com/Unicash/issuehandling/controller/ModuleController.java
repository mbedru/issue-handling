package com.Unicash.issuehandling.controller;

import com.Unicash.issuehandling.dto.request.ModuleIn;
import com.Unicash.issuehandling.dto.response.ModuleOut;
import com.Unicash.issuehandling.model.Module;
import com.Unicash.issuehandling.service.ModuleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/module")
@SecurityRequirement(name = "Bearer Authentication")
//@PreAuthorize("hasAuthority('ADMIN')")
public class ModuleController {

    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @PostMapping("/create")
    public ModuleOut create(ModuleIn moduleIn) {
        return moduleService.create(moduleIn);
    }

    @PutMapping("/update")
    public ModuleOut update(ModuleIn moduleIn) {
        return moduleService.update(moduleIn);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam("moduleId") Long moduleId)  {
        moduleService.delete(moduleId);
    }

    @GetMapping("/list")
    public List<ModuleOut> list(@RequestParam("page") int page,
                                @RequestParam("pageSize") int pageSize) {
        return moduleService.list(page, pageSize);
    }

    @GetMapping("/findById")
    public ModuleOut findById(@RequestParam("moduleId") Long moduleId) {
        return moduleService.findById(moduleId);
    }

}
