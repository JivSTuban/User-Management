package com.csit321.tuban.repositories;

import com.csit321.tuban.entities.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Integer> {
    public List<MyUser> findAllByIsDeletedFalse();
    public MyUser findByIdAndIsDeletedFalse(Integer id);
    public MyUser findByEmailAndIsDeletedFalse(String email);
    public MyUser findByEmailAndIsDeletedTrue(String email);
    public List<MyUser> findAllByIsDeletedTrue();
    public List<MyUser> findAllByFirstNameAndIsDeletedFalse(String firstName);
    public List<MyUser> findAllByLastNameAndIsDeletedFalse(String lastName);
}
