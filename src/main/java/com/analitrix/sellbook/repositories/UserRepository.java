package com.analitrix.sellbook.repositories;

import com.analitrix.sellbook.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
	User findByMail (String mail);
	Page<User> findAll(Pageable pageable);

}
