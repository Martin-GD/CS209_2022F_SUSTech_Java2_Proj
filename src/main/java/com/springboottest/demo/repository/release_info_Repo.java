package com.springboottest.demo.repository;

import com.springboottest.demo.entity.release;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

public interface release_info_Repo extends JpaRepository<release,String> {

    @Query(value = "select * from release where owner_repo = ?1 order by published_at asc ",nativeQuery = true)
    List<release> findByOwner_repo(String owner_repo);

    @Query(value = "select count(*) from release where owner_repo = ?1",nativeQuery = true)
    int findNumByOwner_repo(String owner_repo);

    @Query(value = "select count(*) from commit where owner_repo = ?1 and date between ?2 and ?3",nativeQuery = true)
    int findCommitNumInReleases(String owner_repo, Timestamp pre_time, Timestamp now_time);
}
