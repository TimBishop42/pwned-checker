package com.personal.pwnedchecker.repository;

import com.personal.pwnedchecker.model.Pwned;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PwnedRepository extends JpaRepository<Pwned, Long> {
}
