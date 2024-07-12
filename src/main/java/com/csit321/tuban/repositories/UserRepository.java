package com.csit321.tuban.repositories;

import com.csit321.tuban.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public List<User> findAllByIsDeletedFalse();
    public User findByIdAndIsDeletedFalse(Integer id);
    public User findByEmailAndIsDeletedFalse(String email);
    public User findByEmailAndIsDeletedTrue(String email);
    public List<User> findAllByIsDeletedTrue();
    public List<User> findAllByFirstNameAndIsDeletedFalse(String firstName);
    public List<User> findAllByLastNameAndIsDeletedFalse(String lastName);
}
