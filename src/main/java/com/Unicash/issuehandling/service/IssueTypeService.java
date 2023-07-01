package com.Unicash.issuehandling.service;


import com.Unicash.issuehandling.convertors.IssueTypeConvertor;
import com.Unicash.issuehandling.dto.request.IssueTypeIn;
import com.Unicash.issuehandling.dto.response.IssueTypeOut;
import com.Unicash.issuehandling.exception.NotFondException;
import com.Unicash.issuehandling.model.IssueType;
import com.Unicash.issuehandling.repo.IssueTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueTypeService {

    private final IssueTypeRepository issueTypeRepository;

    public IssueTypeService(IssueTypeRepository issueTypeRepository) {
        this.issueTypeRepository = issueTypeRepository;
    }


    public IssueTypeOut create(IssueTypeIn issueTypeIn)  {
        IssueType issueType1 = new IssueType();
        issueType1.setId(null);
        issueType1.setName(issueTypeIn.getName());
        issueType1.setDescription(issueTypeIn.getDescription());
        return IssueTypeConvertor.toIssueTypeOut(issueTypeRepository.save(issueType1));
    }
    public IssueTypeOut update(IssueTypeIn issueTypeIn) {
        IssueType issueTypeExisting = issueTypeRepository.findById(issueTypeIn.getId()).orElseThrow(()-> new NotFondException(IssueType.class, issueTypeIn.getId()));
//        if (issueTypeExisting.isPresent()) {
            IssueType issueType1 = new IssueType();
            issueType1.setId(issueTypeIn.getId());
            issueType1.setName(issueTypeIn.getName());
            issueType1.setDescription(issueTypeIn.getDescription());
            return IssueTypeConvertor.toIssueTypeOut(issueTypeRepository.save(issueType1));

    }

    public void delete(Long issueTypeId) {
        IssueType  issueType1 = issueTypeRepository.findById(issueTypeId).orElseThrow(()-> new NotFondException(IssueType.class, issueTypeId));
        issueTypeRepository.deleteById(issueTypeId);
    }

    public List<IssueTypeOut> list(int page, int pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);
        return IssueTypeConvertor.toIssueTypeOutList(issueTypeRepository.findAll(pageable).getContent());
    }
    public int listCount() {
        return issueTypeRepository.findAll().size();
    }


    public IssueTypeOut findById(Long issueTypeId) {
        return IssueTypeConvertor.toIssueTypeOut(
                issueTypeRepository.findById(issueTypeId)
                        .orElseThrow(()-> new NotFondException(IssueType.class, issueTypeId)));
    }

}


