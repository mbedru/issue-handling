package com.Unicash.issuehandling.service;

import com.Unicash.issuehandling.convertors.EnterpriseConvertor;
import com.Unicash.issuehandling.dto.request.EnterpriseIn;
import com.Unicash.issuehandling.dto.response.EnterpriseOut;
import com.Unicash.issuehandling.exception.NotFondException;
import com.Unicash.issuehandling.exception.OtherException;
import com.Unicash.issuehandling.model.Developer;
import com.Unicash.issuehandling.model.Enterprise;
import com.Unicash.issuehandling.model.Module;
import com.Unicash.issuehandling.model.Support;
import com.Unicash.issuehandling.repo.EnterpriseRepository;
import com.Unicash.issuehandling.repo.ModuleRepository;
import com.Unicash.issuehandling.repo.SupportRepository;
import com.Unicash.issuehandling.staticHelpers.staticHelper;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class EnterpriseService {

    private final EnterpriseRepository enterpriseRepository;
    private final ModuleRepository moduleRepository;
    private final SupportRepository supportRepository;

    public EnterpriseService(EnterpriseRepository enterpriseRepository, ModuleRepository moduleRepository, SupportRepository supportRepository) {
        this.enterpriseRepository = enterpriseRepository;
        this.moduleRepository = moduleRepository;
        this.supportRepository = supportRepository;
    }

    public EnterpriseOut create(EnterpriseIn enterpriseIn)  {
        Module moduleExisting = moduleRepository.findById(enterpriseIn.getModuleId()).orElseThrow(()-> new NotFondException(Module.class, enterpriseIn.getId()));
        List<Support> enterpriseSupports = new ArrayList<>();
        if (enterpriseIn.getSupportIds().size() > 0)
        enterpriseSupports = supportRepository.findAllById(enterpriseIn.getSupportIds());
            Enterprise enterprise1 = new Enterprise();
//            enterprise1.setId(null);
            enterprise1.setName(enterpriseIn.getName());
            enterprise1.setModule(moduleExisting);
            enterprise1.setSupports(enterpriseSupports.size() > 0 ?  enterpriseSupports : null);
            return EnterpriseConvertor.toEnterpriseOut(enterpriseRepository.save(enterprise1));

    }
    public EnterpriseOut update(EnterpriseIn enterpriseIn) {
        Enterprise enterpriseExisting = enterpriseRepository.findById(enterpriseIn.getId()).orElseThrow(()-> new NotFondException(Enterprise.class, enterpriseIn.getId()));
        Module moduleExisting = moduleRepository.findById(enterpriseIn.getModuleId()).orElseThrow(()-> new NotFondException(Module.class, enterpriseIn.getModuleId()));
        List<Support> supportsExisting = new ArrayList<>();
        if (enterpriseIn.getSupportIds().size()>0)
            supportsExisting = supportRepository.findAllById(enterpriseIn.getSupportIds());

//        if (enterpriseExisting.isPresent() && moduleExisting.isPresent() ) {
            Enterprise enterprise1 = new Enterprise();
            enterprise1.setId(enterpriseIn.getId());
            enterprise1.setName(enterpriseIn.getName());
            enterprise1.setModule(moduleExisting);
            enterprise1.setSupports(supportsExisting);
            return EnterpriseConvertor.toEnterpriseOut(enterpriseRepository.save(enterprise1));

    }

    public void delete(Long enterpriseId) {
        Enterprise  enterprise1 = enterpriseRepository.findById(enterpriseId).orElseThrow(()-> new NotFondException(Enterprise.class, enterpriseId));
        enterpriseRepository.deleteById(enterpriseId);
    }

    public List<EnterpriseOut> list(Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return EnterpriseConvertor.toEnterpriseOutList(enterpriseRepository.findAll(pageable).getContent());
    }

    public int listCount() {
        return enterpriseRepository.findAll().size();
    }

    public EnterpriseOut findById(Long enterpriseId) {
        return EnterpriseConvertor.toEnterpriseOut(
                enterpriseRepository.findById(enterpriseId)
                        .orElseThrow(()-> new NotFondException(Enterprise.class, enterpriseId)));
    }

    public List<EnterpriseOut> listByModuleId(Long moduleId, Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return EnterpriseConvertor.toEnterpriseOutList(enterpriseRepository.findByModuleId(moduleId, pageable).getContent());
    }

    public int listByModuleIdCount(Long moduleId) {
        return enterpriseRepository.findByModuleId(moduleId).size();
    }

    public List<EnterpriseOut> listBySupportId(Long supportId, Integer page, Integer pageSize) {
            Stream<Enterprise> enterpriseStream = enterpriseRepository.findAll()
                    .stream().filter(enterprise1 ->
                            enterprise1.getSupports().stream().anyMatch(support ->
                                    support.getId().equals(supportId)));
        List<Enterprise> enterpriseList = enterpriseStream.collect(Collectors.toList());
        PagedListHolder<Enterprise> pagedListHolder = new PagedListHolder<Enterprise>(enterpriseList);
        pagedListHolder.setPageSize(pageSize);
        pagedListHolder.setPage(page);

        if (pagedListHolder.getPageCount() <= page )
            return Collections.emptyList();

        List<Enterprise> pageAsList = pagedListHolder.getPageList();//returnsthe sublist of selected page of enterpriseList
        return EnterpriseConvertor.toEnterpriseOutList(pageAsList);
///////////////
/*
        Pageable pageable = PageRequest.of(page, pageSize);
        List<Support> collect = supportRepository.findAll(pageable).stream().
                filter(support -> support.getEnterprises().stream().anyMatch
                        (enterprise -> enterprise.getId().equals(enterpriseId))).collect(Collectors.toList());

        return collect.stream().map(Support::support).collect(Collectors.toList());
*/

    }

    public int listBySupportIdCount(Long supportId) {
        Stream<Enterprise> enterpriseStream = enterpriseRepository.findAll()
                .stream().filter(enterprise1 ->
                        enterprise1.getSupports().stream().anyMatch(support ->
                                support.getId().equals(supportId)));

        return enterpriseStream.collect(Collectors.toList()).size();
    }


    public void assignSupports(Long enterpriseId, List<Long> supportIds) {
        Enterprise enterpriseExisting = enterpriseRepository.findById(enterpriseId).orElseThrow(()-> new NotFondException(Enterprise.class, enterpriseId));
            List<Long> suppExistingIds = enterpriseExisting.getSupports().stream()
                                            .map(Support::getId)
                                            .collect(Collectors.toList());
            suppExistingIds.addAll(supportIds);//existingIds lay newYemetutenId add mareg
            List<Support> supportsExistingAndNew = supportRepository.findAllById(suppExistingIds);
            enterpriseExisting.setSupports(supportsExistingAndNew);
            Enterprise save = enterpriseRepository.save(enterpriseExisting);
//            return true;
    }

    public void cancelSupports(Long enterpriseId, Set<Long> supportIdsForCancel) {
        System.out.println("-----"+supportIdsForCancel+"----"+supportIdsForCancel.size()+"-");
        Enterprise enterpriseExisting = enterpriseRepository.findById(enterpriseId).orElseThrow(()-> new NotFondException(Enterprise.class, enterpriseId));
        List<Long> supportsInEnterprise = enterpriseExisting.getSupports().stream()
                                            .map(Support::getId)
                                            .collect(Collectors.toList());
//        boolean bool = supportIdsForCancel.retainAll(supportsInEnterprise);//removes supportIdsForCancel elements that aren't in supportsInEnterprise
//        System.out.println("-----"+supportIdsForCancel+"--"+supportIdsForCancel.size()+"--"+bool+"-");
//
//            if (supportIdsForCancel.size() <= 0) {
//                throw new NotFondException("the supportIds you mentioned arent Existing");//there is no support in our enterprise that matches the supportIds tobeCanceled
//            }
            ArrayList<Long> afterCancelingSupportIds = new ArrayList<>();
            for (Long num : supportsInEnterprise) {
                if (!supportIdsForCancel.contains(num)) {
                    afterCancelingSupportIds.add(num);
                }
            }
            System.out.println("-----"+supportIdsForCancel+"--"+supportIdsForCancel.size()+"--"+afterCancelingSupportIds+"--");
            List<Support> afterCancelingSupports = supportRepository.findAllById(afterCancelingSupportIds);
            enterpriseExisting.setSupports(afterCancelingSupports);
            enterpriseRepository.save(enterpriseExisting);

    }
//    public List<Enterprise> listBySupportId(Long supportId, Integer start, Integer max) {
//        Optional<Support> supportOptional = supportRepository.findById(supportId);
//        List<Support> singleSupportInAList = (List<Support>) new HashSet<Support>();
//        supportOptional.ifPresent(singleSupportInAList::add);
//
//        List<Enterprise> supportList = enterpriseRepository.findBySupport(singleSupportInAList)
//                .subList(start, max);
//        return supportList;
//    }
//    public int listBySupportId(Long supportId) {
//        Optional<Support> supportOptional = supportRepository.findById(supportId);
//        List<Support> singleSupportInAList = (List<Support>) new HashSet<Support>();
//        singleSupportInAList.add(supportOptional.get());
//
//        List<Enterprise> supportList = enterpriseRepository.findBySupport(singleSupportInAList);
//        return supportList.size();
//    }

}
