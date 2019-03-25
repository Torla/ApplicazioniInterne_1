package com.test.demo.db;

import com.test.demo.ViewModels.UserDataVM;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserDataRepository extends CrudRepository<UserDataVM,Long>{

}
