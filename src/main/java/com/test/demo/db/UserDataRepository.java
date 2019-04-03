package com.test.demo.db;

import com.test.demo.ViewModels.UserDataVM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserDataRepository extends JpaRepository<UserDataVM, Long> {

  UserDataVM findByEmail(String email);

  int countByEmail(String email);
}
