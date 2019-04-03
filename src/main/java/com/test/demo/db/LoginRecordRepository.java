package com.test.demo.db;

import com.test.demo.Entity.LoginRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRecordRepository extends JpaRepository<LoginRecord, Long> {

  LoginRecord findByCookie(String cookie);

}

