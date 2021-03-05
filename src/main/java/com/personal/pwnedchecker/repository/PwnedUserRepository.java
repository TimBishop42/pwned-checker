package com.personal.pwnedchecker.repository;

import com.personal.pwnedchecker.model.PwnedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PwnedUserRepository extends JpaRepository<PwnedUser, Long> {
}
