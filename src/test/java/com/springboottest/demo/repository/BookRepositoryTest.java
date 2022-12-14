//package com.springboottest.demo.repository;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class BookRepositoryTest {
//    @Autowired
//    private itemRepository bookRepository;
//
//
//    @Test
//    void findAll() {
//        System.out.println(bookRepository.findAll());
//    }
//
//    @Test
//    void save(){
//        item i = new item();
//        i.setId(100);
//        i.setName("z");
//        i.setAuthor("zz");
//        item i2 = bookRepository.save(i);
//        System.out.println(i2);
//    }
//}