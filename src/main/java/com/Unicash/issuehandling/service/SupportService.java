package com.Unicash.issuehandling.service;


import com.Unicash.issuehandling.convertors.SupportConvertor;
import com.Unicash.issuehandling.dto.request.SupportIn;
import com.Unicash.issuehandling.dto.response.SupportOut;
import com.Unicash.issuehandling.exception.NotFondException;
import com.Unicash.issuehandling.exception.OtherException;
import com.Unicash.issuehandling.model.Enterprise;
import com.Unicash.issuehandling.model.Support;
import com.Unicash.issuehandling.repo.EnterpriseRepository;
import com.Unicash.issuehandling.repo.SupportRepository;
import com.Unicash.issuehandling.staticHelpers.staticHelper;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SupportService {

    private final SupportRepository supportRepository;
    private final EnterpriseRepository enterpriseRepository;

    public SupportService(SupportRepository supportRepository, EnterpriseRepository enterpriseRepository) {
        this.supportRepository = supportRepository;
        this.enterpriseRepository = enterpriseRepository;
    }


    public SupportOut create(SupportIn supportIn) {
        List<Enterprise> supportEnterprises = new ArrayList<>();
        if (supportIn.getEnterpriseIds().size() > 0)
            supportEnterprises = enterpriseRepository.findAllById(supportIn.getEnterpriseIds());
//            if (supportIn.getEnterpriseIds().size() > 0 && supportEnterprises.size() <= 0)
//              throw new OtherException("The ENTERPRISES you are refering are NOT FOUND");
        Support support1 = new Support();
            support1.setId(null);
            support1.setUsername(supportIn.getUserName());
            support1.setFirstName(supportIn.getFirstName());
            support1.setLastName(supportIn.getLastName());
        support1.setEnterprises(supportEnterprises.size() > 0 ? supportEnterprises : null);
        return SupportConvertor.toSupportOut(supportRepository.save(support1));

    }

    public SupportOut update(SupportIn supportIn) {
        Support supportExisting = supportRepository.findById(supportIn.getId()).orElseThrow(() -> new NotFondException(Support.class, supportIn.getId()));
        List<Enterprise> supportEnterprises = new ArrayList<>();
        if (supportIn.getEnterpriseIds().size() > 0)
            supportEnterprises = enterpriseRepository.findAllById(supportIn.getEnterpriseIds());
//        if (supportIn.getEnterpriseIds().size() > 0 && supportEnterprises.size() <= 0)
//            throw new OtherException("The ENTERPRISES you are refering are NOT FOUND");

        Support support1 = new Support();
        support1.setId(supportIn.getId());
        support1.setUsername(supportIn.getUserName());
        support1.setFirstName(supportIn.getFirstName());
        support1.setLastName(supportIn.getLastName());
        support1.setEnterprises(supportEnterprises);
        return SupportConvertor.toSupportOut(supportRepository.save(support1));

    }

    public void delete(Long supportId) {
        Support support1 = supportRepository.findById(supportId).orElseThrow(() -> new NotFondException(Support.class, supportId));
        supportRepository.deleteById(supportId);
    }

    public List<SupportOut> list(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        //return SupportConvertor.toSupportOutList(supportRepository.findAll(pageable).getContent());
        return SupportConvertor.toSupportOutList(supportRepository.findAll(pageable).getContent());
    }

    public int listCount() {
        return supportRepository.findAll().size();
    }


    public SupportOut findById(Long supportId) {
        return SupportConvertor.toSupportOut(supportRepository.findById(supportId).orElseThrow(() -> new NotFondException(Support.class, supportId)));
    }


        public List<SupportOut> listByEnterpriseId(Long enterpriseId, Integer page, Integer pageSize) {
//            Pageable pageable = PageRequest.of(page, pageSize);
            Stream<Support> supportStream = supportRepository.findAll().stream().
                    filter(support -> support.getEnterprises().stream().anyMatch(enterprise ->
                                    enterprise.getId().equals(enterpriseId)));
            List<Support> supportList = supportStream.collect((Collectors.toList()));
            PagedListHolder<Support> pagedListHolder = new PagedListHolder<Support>(supportList);
            pagedListHolder.setPageSize(pageSize);
            pagedListHolder.setPage(page);

            if (pagedListHolder.getPageCount() <= page)
                return Collections.emptyList();

            List<Support> supportAsList = pagedListHolder.getPageList();//returnsthe sublist of selected page of supportList

            return SupportConvertor.toSupportOutList(supportAsList);
        }

    public int listByEnterpriseIdCount(Long enterpriseId) {
        Stream<Support> supportStream = supportRepository.findAll()
                .stream().filter(support ->
                        support.getEnterprises().stream().anyMatch(enterprise ->
                                enterprise.getId().equals(enterpriseId)));
        return supportStream.collect(Collectors.toList()).size();
    }

    ///////////////*******////////
    public void assignEnterprises(Long supportId, List<Long> enterpriseIds) {
        Support supportExisting = supportRepository.findById(supportId).orElseThrow(() -> new NotFondException(Support.class, supportId));
        List<Long> enterpriseExistingIds = supportExisting.getEnterprises().stream()
                .map(Enterprise::getId)
                .collect(Collectors.toList());
        enterpriseExistingIds.addAll(enterpriseIds);//existingIds lay newComingIds add mareg
        List<Enterprise> enterprisesExistingAndNew = enterpriseRepository.findAllById(enterpriseExistingIds);
        supportExisting.setEnterprises(enterprisesExistingAndNew);
        Support save = supportRepository.save(supportExisting);
    }

    public void cancelEnterprises(Long supportId, Set<Long> enterpriseIds) {
        System.out.println("-----" + enterpriseIds + "----" + enterpriseIds.size() + "-");
        Support supportExisting = supportRepository.findById(supportId).orElseThrow(() -> new NotFondException(Support.class, supportId));
        List<Long> enterprisesInSupport = supportExisting.getEnterprises().stream()
                .map(Enterprise::getId)
                .collect(Collectors.toList());
//            boolean bool = enterpriseIds.retainAll(enterprisesInSupport);//removes enterpriseIds elements that arent in enterprisesInSupport
//            if (!bool) throw new OtherException("Error while canceling Entrprises!!!");
//            System.out.println("-----"+enterpriseIds+"--"+enterpriseIds.size()+"--"+bool+"-");
//
//            if (enterpriseIds.size() <= 0) {
//                throw new OtherException("The enterprise/s you want to be canceled with Id/s: "+enterpriseIds.toString()+", is/are not related to this Support.");//there is no enterprise in our support that matches the enterpriseIds tobeCanceled
//            }
        //ExistingEnterprisesIds - enterpriseIdsTobeCanceled
        ArrayList<Long> afterCancelingEnterpriseIds = new ArrayList<>();
        for (Long num : enterprisesInSupport) {
            if (!enterpriseIds.contains(num)) {
                afterCancelingEnterpriseIds.add(num);
            }
        }
        System.out.println("-----" + enterpriseIds + "--" + enterpriseIds.size() + "--" + afterCancelingEnterpriseIds + "--");
        List<Enterprise> afterCancelingEnterprises = enterpriseRepository.findAllById(afterCancelingEnterpriseIds);
//            if (afterCancelingEnterprises.size() == enterprisesInSupport.size()) {
//                throw new OtherException("NO-cancellation-occured");
//            }
        supportExisting.setEnterprises(afterCancelingEnterprises);
        supportRepository.save(supportExisting);

    }
}
/*    public List<SupportOut> list(int limit, int page) {
        List<SupportOut> supportOuts = SupportConvertor.toSupportOutList(supportRepository.findAll());

        Page<SupportOut> pageResult = getListWithStartAndEndAndLimit(supportOuts, limit, page);
        List<SupportOut> itemsOnPage = pageResult.getContent();
        return itemsOnPage;
    }*/
/*    public List<SupportOut> getTasks(int page, int pageSize, Sort sort) {
        Pageable pageable1 = PageRequest.of(page, pageSize, sort);

        return supportRepository.findAll(pageable1).stream().map(Support::supportOut).collect(Collectors.toList());
    }*/

    /*
    public List<Support> listByEnterpriseId(Long enterpriseId, Integer start, Integer max) {
        Optional<Enterprise> enterpriseOptional = enterpriseRepository.findById(enterpriseId);
        List<Enterprise> singleEnterpriseInAList = (List<Enterprise>) new HashSet<Enterprise>();
        singleEnterpriseInAList.add(enterpriseOptional.get());

        List<Support> supportList = supportRepository.findByEnterprise(singleEnterpriseInAList)
                .subList(start, max);
        return supportList;
    }*/

/*    public int listByEnterpriseIdCount(Long enterpriseId) {
        Optional<Enterprise> enterpriseOptional = enterpriseRepository.findById(enterpriseId);
        List<Enterprise> singleEnterpriseInAList = (List<Enterprise>) new HashSet<Enterprise>();
        singleEnterpriseInAList.add(enterpriseOptional.get());

        List<Support> supportList = supportRepository.findByEnterprise(singleEnterpriseInAList);
        return supportList.size();
    }*/
/*    public Page<SupportOut> getListWithStartAndEndAndLimit(List<SupportOut> supportOuts,int limit, int page) {

        int totalItems = supportOuts.size();
        int fromIndex = page * limit;
        int toIndex = Math.min(fromIndex + limit, totalItems);
        Pageable pageable = PageRequest.of(page, limit);
        return new PageImpl<>(supportOuts, pageable, totalItems);
    }*/

//    public List<SupportOut> list(Integer start, Integer max) {
//        int listCount = listCount();
//        int start1 = start == null || start <= 0 ? 0 : start;
//        int max1 = max == null || max <= 0 ? 0 : max;//max can be the number of entities u want at a single page.
//        if (listCount < max1) //throw new OtherException("listCount is less than pageMax");
//        {
//            int listCountModulo = listCount %(max1-start1);//remaining at last. 3?
//            if(start1 + listCountModulo <= max1)
//            max1 = start1 + listCountModulo;
//        }
//        if (staticHelper.checkPaginationPossible(start1,max1,listCount))
//            return SupportConvertor.toSupportOutList(supportRepository.findAll().subList(start1, max1));
//        else
//            throw new OtherException(String.format("Pagination of %s from: %d to: %d not possible.", Support.class, start1, max1));
//
//    }
//    public List<SupportOut> list(Integer start, Integer max) {
//        int listCount = listCount();
//        System.out.println("-s1--"+start+"-s2--"+max+"-s1--"+listCount);
//        int start1 = start == null || start <= 1 ? 0 : start-1;
//        int max1 = max == null || max <= 1 ? 0 : max;//max can be the number of entities u want at a single page.
//        System.out.println("11111-s1--"+start1+"-s2--"+max1+"-s1--"+listCount);
//        if (listCount < max1) //throw new OtherException("listCount is less than pageMax");
//        {
//            int listCountModulo = listCount %(max1-start1+1);//remaining at last. 3?
//            if(start1 + listCountModulo < max1)
//            max1 = start1 + listCountModulo;
//            System.out.println("22222-s1--"+start1+"-s2--"+max1+"-s1--"+listCount);
//        }
//        if (staticHelper.checkPaginationPossible(start1,max1,listCount))
//            return SupportConvertor.toSupportOutList(supportRepository.findAll().subList(start1, max1));
//        else
//            throw new OtherException(String.format("Pagination of %s from: %d to: %d not possible.", Support.class, start1, max1));
//
//    }
//public List<SupportOut> list(Integer start, Integer max) {
//    int listCount = listCount();
//    int start1 = start == null || start < 1 ? 1 : start;
//    int max1 = max == null || max < 1 ? 1 : max; //max can be the number of entities u want at a single page.
//    if (listCount < max1 - start1 + 1) //throw new OtherException("listCount is less than pageMax");
//    {
//        int listCountModulo = listCount % (max1 - start1 + 1);//remaining at last. 3?
//        if (start1 + listCountModulo - 1 <= max1)
//            max1 = start1 + listCountModulo - 1;
//    }
//    if (staticHelper.checkPaginationPossible(start1, max1, listCount))
//        return SupportConvertor.toSupportOutList(supportRepository.findAll().subList(start1 - 1, max1));
//    else
//        throw new OtherException(String.format("Pagination of %s from: %d to: %d not possible.", Support.class, start1, max1));
//}



