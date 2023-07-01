package com.Unicash.issuehandling.service;

import com.Unicash.issuehandling.convertors.DeveloperConvertor;
import com.Unicash.issuehandling.dto.request.DeveloperIn;
import com.Unicash.issuehandling.dto.response.DeveloperOut;
import com.Unicash.issuehandling.exception.NotFondException;
import com.Unicash.issuehandling.exception.OtherException;
import com.Unicash.issuehandling.model.Developer;
import com.Unicash.issuehandling.model.Module;
import com.Unicash.issuehandling.repo.DeveloperRepository;
import com.Unicash.issuehandling.repo.ModuleRepository;
import com.Unicash.issuehandling.staticHelpers.staticHelper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeveloperService {

    private final DeveloperRepository developerRepository;
    private final ModuleRepository moduleRepository;

    public DeveloperService(DeveloperRepository developerRepository, ModuleRepository moduleRepository) {
        this.developerRepository = developerRepository;
        this.moduleRepository = moduleRepository;
    }

    public DeveloperOut create(DeveloperIn developerIn)  {
        Module moduleExisting = moduleRepository.findById(developerIn.getModuleId()).orElseThrow(()-> new NotFondException(Module.class, developerIn.getModuleId()));
            Developer developer1 = new Developer();
                developer1.setId(null);
                developer1.setUsername(developerIn.getUserName());
                developer1.setFirstName(developerIn.getFirstName());
                developer1.setLastName(developerIn.getLastName());
                developer1.setModule(moduleExisting);//.get());
            return DeveloperConvertor.toDeveloperOut(developerRepository.save(developer1));
    }
    public DeveloperOut update(DeveloperIn developerIn) {
        Developer developerExisting = developerRepository.findById(developerIn.getDevId()).orElseThrow(()-> new NotFondException(Developer.class, developerIn.getDevId()));
        Module moduleExisting = moduleRepository.findById(developerIn.getModuleId()).orElseThrow(()-> new NotFondException(Module.class, developerIn.getModuleId()));
            Developer developer1 = new Developer();
                developer1.setId(developerExisting.getId());
                developer1.setUsername(developerIn.getUserName());
                developer1.setFirstName(developerIn.getFirstName());
                developer1.setLastName(developerIn.getLastName());
                developer1.setModule(moduleExisting);//.get());
            return DeveloperConvertor.toDeveloperOut(developerRepository.save(developer1));
    }

    public void delete(Long developerId) {
        Developer  developer1 = developerRepository.findById(developerId).orElseThrow(()-> new NotFondException(Developer.class, developerId));
        developerRepository.deleteById(developer1.getId());
    }

    public List<DeveloperOut> list(Integer page, Integer pageSize) {
        System.out.println("in--2");

        Pageable pageable = PageRequest.of(page, pageSize);
        return DeveloperConvertor.toDeveloperOutList(developerRepository.findAll(pageable).getContent());

    }
    public int listCount() {
        return developerRepository.findAll().size();
    }

    public DeveloperOut findById(Long developerId) {
            Developer developer1 = developerRepository.findById(developerId).orElseThrow(() -> new NotFondException(Developer.class, developerId));
            return DeveloperConvertor.toDeveloperOut(developer1);
    }

    public List<DeveloperOut> listByModuleId(Long moduleId, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return DeveloperConvertor.toDeveloperOutList(developerRepository.findByModuleId(moduleId, pageable).getContent());
    }

    public int listByModuleIdCount(Long moduleId) {
        return developerRepository.findByModuleId(moduleId).size();
    }
}
