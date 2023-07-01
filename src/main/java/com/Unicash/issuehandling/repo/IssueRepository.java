package com.Unicash.issuehandling.repo;
import com.Unicash.issuehandling.model.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue,Long> {

    List<Issue> findAll();
    Page<Issue> findAll(Pageable pageable);

    List<Issue> findByEnterpriseId(Long enterpriseId);
    Page<Issue> findByEnterpriseId(Long enterpriseId, Pageable pageable);

    List<Issue> findBySupportId(Long supportId);
    Page<Issue> findBySupportId(Long supportId, Pageable pageable);

    List<Issue> findByIssueTypeId(Long issueTypeId);
    Page<Issue> findByIssueTypeId(Long issueTypeId, Pageable pageable);

    List<Issue> findByStatusId(Long statusId);
    Page<Issue> findByStatusId(Long statusId, Pageable pageable);

}
