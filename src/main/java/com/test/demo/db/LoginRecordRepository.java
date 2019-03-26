package com.test.demo.db;

import com.test.demo.Entity.LoginRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRecordRepository extends CrudRepository<LoginRecord,Long> {

	LoginRecord findByCookie(String cookie);
}

