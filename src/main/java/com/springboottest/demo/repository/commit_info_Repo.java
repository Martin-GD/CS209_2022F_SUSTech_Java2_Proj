package com.springboottest.demo.repository;

import com.springboottest.demo.entity.commit;
import com.springboottest.demo.entity.release;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface commit_info_Repo extends JpaRepository<commit,String> {
    @Query(value = "select * from commit where owner_repo = ?1",nativeQuery = true)
    List<commit> findByOwner_repo(String owner_repo);
}
