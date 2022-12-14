package com.springboottest.demo.repository;

import com.springboottest.demo.entity.developer;
import com.springboottest.demo.entity.release;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface developer_info_Repo extends JpaRepository<developer,String>  {
    @Query(value = "select * from developer where owner_repo = ?1",nativeQuery = true)
    List<developer> findByOwner_repo(String owner_repo);

    @Query(value = "select count(*) from developer where owner_repo=?1",nativeQuery = true)
    int findNumberOfDevelopers(String owner_repo);

    @Query(value = "select * from developer where owner_repo = ?1  " +
            "order by contributions desc limit 5",nativeQuery = true)
    List<developer> findTop5(String owner_repo);
}
