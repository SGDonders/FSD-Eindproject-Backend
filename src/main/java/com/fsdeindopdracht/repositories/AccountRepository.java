package com.fsdeindopdracht.repositories;

import com.fsdeindopdracht.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}

