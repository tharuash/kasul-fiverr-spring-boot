package com.b127.web.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.b127.web.entity.Credit;
import com.b127.web.entity.User;

public interface CreditRepository extends JpaRepository<Credit, Long>{
	
	@Modifying(clearAutomatically = true)
	@Query("Update Credit c SET c.isDefault =:isDefault WHERE c.user =:user AND c.isDefault=true")
	void updateCreditDefaultState(@Param("isDefault") boolean isDefault, @Param("user") User user);
	
	List<Credit> findByUser(User user);
	
	Optional<Credit> findByUserAndIsDefault(User user, boolean isDefault);
	
	@Query(value = "SELECT max(c.id) FROM Credit c")
	Long getLatestId();

}
