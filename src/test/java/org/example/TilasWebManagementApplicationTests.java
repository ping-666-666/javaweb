package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
class TilasWebManagementApplicationTests {
//    @Autowired
//    private EmpMapper empMapper;
//    @Test
//    void contextLoads() {
//        List<Emp> list = empMapper.findAll();
//        list.forEach(System.out::println);
//    }

    @Test
    void testUuid(){
        for (int i = 0; i < 100; i++) {
            UUID uuid = UUID.randomUUID();
            System.out.println(uuid);
        }
    }

}
