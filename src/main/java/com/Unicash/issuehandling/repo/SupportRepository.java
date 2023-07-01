package com.Unicash.issuehandling.repo;

import com.Unicash.issuehandling.model.Support;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupportRepository extends JpaRepository<Support, Long> {
    List<Support> findAll();
    @Query(value = "select s from Support s")
    List<Support> findAllSupports(Pageable pageable);

//    List<Support> findByEnterpriseId(Long id);
//    List<Support> findByEnterprise(List<Enterprise> enterprises);//but we'll send 1 enterprise.

//    List<Support> findAllByEnterprise(Long mId);

//findByCustomer_IdAndCustomer_Sibling_IdAndCustomer_Sibling_Toy_Id(Long custId, Long siblingId, Long toyId)

}