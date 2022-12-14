package com.springboottest.demo.repository;

import com.springboottest.demo.entity.release;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface release_info_Repo extends JpaRepository<release,String> {

    @Query(value = "select * from release where owner_repo = ?1",nativeQuery = true)
    List<release> findByOwner_repo(String owner_repo);
}
