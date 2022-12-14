package com.springboottest.demo.repository;

import com.springboottest.demo.entity.developer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface developer_info_Repo extends JpaRepository<developer,String>  {
}
