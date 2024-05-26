package com.sheandstud.repository;

import com.sheandstud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}