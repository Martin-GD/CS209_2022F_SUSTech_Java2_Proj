package com.springboottest.demo.repository;

import com.springboottest.demo.entity.issue;
import com.springboottest.demo.entity.release;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface issue_info_Repo extends JpaRepository<issue, String> {
  @Query(value = "select * from issue where owner_repo = ?1", nativeQuery = true)
  List<issue> findByOwner_repo(String owner_repo);

  @Query(value = "select * from issue where owner_repo = ?1 and state = ?2", nativeQuery = true)
  List<issue> findByOwner_repoAndState(String owner_repo, String state);

  @Query(value = "select count(*) from issue where owner_repo = ?1 and state=?2", nativeQuery = true)
  int findNumByState(String owner_repo, String state);

  @Query(value = "select * from issue where (issue.body like  CONCAT('%',?2,'%') or issue.title like CONCAT('%',?2,'%')) and owner_repo = ?1", nativeQuery = true)
  List<issue> searchByTitleAndBody(String owner_repo, String keyword);


}
