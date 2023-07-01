package com.Unicash.issuehandling.service;

import com.Unicash.issuehandling.convertors.IssueConvertor;
import com.Unicash.issuehandling.dto.request.IssueIn;
import com.Unicash.issuehandling.dto.response.IssueOut;
import com.Unicash.issuehandling.exception.NotFondException;
import com.Unicash.issuehandling.exception.OtherException;
import com.Unicash.issuehandling.model.*;
import com.Unicash.issuehandling.repo.*;
import com.Unicash.issuehandling.staticHelpers.staticHelper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueService {

    private final IssueRepository issueRepository;

    private final EnterpriseRepository enterpriseRepository;
    private final SupportRepository supportRepository;
    private final IssueTypeRepository issueTypeRepository;
    private final StatusRepository statusRepository;

    public IssueService(IssueRepository issueRepository, EnterpriseRepository enterpriseRepository,
                        SupportRepository supportRepository, IssueTypeRepository issueTypeRepository, StatusRepository statusRepository) {
        this.issueRepository = issueRepository;
        this.enterpriseRepository = enterpriseRepository;
        this.supportRepository = supportRepository;
        this.issueTypeRepository = issueTypeRepository;
        this.statusRepository = statusRepository;
    }

    public IssueOut create(IssueIn issueIn)  {
            Enterprise enterpriseExisting = enterpriseRepository.findById(issueIn.getEnterpriseId()).orElseThrow(()-> new NotFondException(Enterprise.class, issueIn.getEnterpriseId()));
            Support supportExisting = supportRepository.findById(issueIn.getSupportId()).orElseThrow(()-> new NotFondException(Support.class, issueIn.getSupportId()));
            IssueType issueTypeExisting = issueTypeRepository.findById(issueIn.getIssueTypeId()).orElseThrow(()-> new NotFondException(IssueType.class, issueIn.getIssueTypeId()));
            Status statusExisting = statusRepository.findById(issueIn.getStatusId()).orElseThrow(()-> new NotFondException(Status.class, issueIn.getStatusId()));
//            if (enterpriseExisting.isPresent() && supportExisting.isPresent() && issueTypeExisting.isPresent() && statusExisting.isPresent()) {

                Issue issue1 = new Issue();
//                issue1.setId(null);
                issue1.setDescription(issueIn.getDescription());
                issue1.setEnterprise(enterpriseExisting);
                issue1.setSupport(supportExisting);
                issue1.setIssueType(issueTypeExisting);
                issue1.setStatus(statusExisting);
                return IssueConvertor.toIssueOut(issueRepository.save(issue1));

    }
    public IssueOut update(IssueIn issueIn) {
        Issue issueExisting = issueRepository.findById(issueIn.getId()).orElseThrow(()-> new NotFondException(Issue.class, issueIn.getId()));
//        if (issueExisting.isPresent()) {
            System.out.println("preSent - 1- ");//working
            Enterprise enterpriseExisting = enterpriseRepository.findById(issueIn.getEnterpriseId()).orElseThrow(()-> new NotFondException(Enterprise.class, issueIn.getEnterpriseId()));
            Support supportExisting = supportRepository.findById(issueIn.getSupportId()).orElseThrow(()-> new NotFondException(Support.class, issueIn.getSupportId()));
            IssueType issueTypeExisting = issueTypeRepository.findById(issueIn.getIssueTypeId()).orElseThrow(()-> new NotFondException(IssueType.class, issueIn.getIssueTypeId()));
            Status statusExisting = statusRepository.findById(issueIn.getStatusId()).orElseThrow(()-> new NotFondException(Status.class, issueIn.getStatusId()));
//            if (enterpriseExisting.isPresent() && supportExisting.isPresent() && issueTypeExisting.isPresent() && statusExisting.isPresent()) {
                System.out.println("preSent - 2");
                Issue issue1 = new Issue();
                issue1.setId(issueIn.getId());
                issue1.setDescription(issueIn.getDescription());
                issue1.setEnterprise(enterpriseExisting);
                issue1.setSupport(supportExisting);
                issue1.setIssueType(issueTypeExisting);
                issue1.setStatus(statusExisting);
                return IssueConvertor.toIssueOut(issueRepository.save(issue1));

    }
    public void delete(Long issueId) {
            Issue issue1 = issueRepository.findById(issueId).orElseThrow(()-> new NotFondException(Issue.class, issueId));
            issueRepository.deleteById(issueId);
    }

    public List<IssueOut> list(int page, int pageSize) {
            Pageable pageable = PageRequest.of(page, pageSize);
            return IssueConvertor.toIssueOutList(issueRepository.findAll(pageable).getContent());
    }

    public int listCount() {
        return issueRepository.findAll().size();
    }

    public IssueOut findById(Long issueId) {
        return IssueConvertor.toIssueOut(
                issueRepository.findById(issueId)
                        .orElseThrow(()-> new NotFondException(Issue.class, issueId)));
    }


    public List<IssueOut> listByEnterpriseId(Long entrpriseId, int page, int pageSize) {
         Pageable pageable = PageRequest.of(page, pageSize);
         return IssueConvertor.toIssueOutList(issueRepository.findByEnterpriseId(entrpriseId, pageable).getContent());
    }
    public int listByEnterpriseIdCount(Long entrpriseId) {
        return issueRepository.findByEnterpriseId(entrpriseId).size();
    }

    public List<IssueOut> listBySupportId(Long supportId, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return IssueConvertor.toIssueOutList(issueRepository.findBySupportId(supportId, pageable).getContent());
    }
    public int listBySupportIdCount(Long supportId) {
        return issueRepository.findBySupportId(supportId).size();
    }
    public List<IssueOut> listByIssueTypeId(Long issueTypeId, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return IssueConvertor.toIssueOutList(issueRepository.findByIssueTypeId(issueTypeId, pageable).getContent());
    }
    public int listByIssueTypeIdCount(Long issueTypeId) {
        return issueRepository.findByIssueTypeId(issueTypeId).size();
    }

    public List<IssueOut> listByStatusId(Long statusId, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return IssueConvertor.toIssueOutList(issueRepository.findByStatusId(statusId, pageable).getContent());
    }
    public int listByStatusIdCount(Long statusId) {
        return issueRepository.findByStatusId(statusId).size();
    }

}
