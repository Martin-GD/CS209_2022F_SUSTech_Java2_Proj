package com.springboottest.demo.repository;

import com.springboottest.demo.entity.commit;
import com.springboottest.demo.entity.release;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface commit_info_Repo extends JpaRepository<commit, String> {
  @Query(value = "select * from commit where owner_repo = ?1", nativeQuery = true)
  List<commit> findByOwner_repo(String owner_repo);


  @Query(value = "select count(*) from commit where owner_repo = ?1", nativeQuery = true)
  int findNumByOwner_repo(String owner_repo);

  @Query(value = "select count(*) from commit where extract(minute from date)*60+extract(hour from date)*3600+extract(second from date) between 0 and 28800 and owner_repo = ?1", nativeQuery = true)
  int findMorningNum(String owner_repo);

  @Query(value = "select count(*) from commit where extract(minute from date)*60+extract(hour from date)*3600+extract(second from date) between 28801 and 57600 and owner_repo = ?1", nativeQuery = true)
  int findAfternoonNum(String owner_repo);

  @Query(value = "select count(*) from commit where extract(minute from date)*60+extract(hour from date)*3600+extract(second from date) > 57601 and owner_repo = ?1", nativeQuery = true)
  int findNightNum(String owner_repo);
}
