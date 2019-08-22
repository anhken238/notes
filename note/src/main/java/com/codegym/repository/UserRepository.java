package com.codegym.repository;

import com.codegym.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findByName(String name);
//    @Query(value = "select * from notes.note where user_id=:id", )
}
