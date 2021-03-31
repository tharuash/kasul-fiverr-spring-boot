package com.b127.web.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.b127.web.entity.Profile;
import com.b127.web.entity.User;

public interface ProfileRepository extends JpaRepository<Profile, Long>{
	
	@Modifying(clearAutomatically = true)
	@Query("Update Profile p SET p.isDefaultBilling = false WHERE p.user =:user AND p.isDefaultBilling = true")
	void updateProfileDefaultBillingStateToFalse(@Param("user") User user);
	
	@Modifying(clearAutomatically = true)
	@Query("Update Profile p SET p.isDefaultShipping = false WHERE p.user =:user AND p.isDefaultShipping = true")
	void updateProfileDefaultShippingStateToFalse(@Param("user") User user);
	
	@Query(value = "SELECT p FROM Profile p WHERE p.user = :user AND p.isDefaultShipping = true")
	Optional<Profile> getUserDefaultShippingProfile(@Param("user") User user);
	
	@Query(value = "SELECT p FROM Profile p WHERE p.user = :user AND p.isDefaultBilling = true")
	Optional<Profile> getUserDefaultBillingProfile(@Param("user") User user);
	
	@Query(value = "SELECT max(p.id) FROM Profile p")
	Long getLatestId();
	
	Optional<Profile> findByUserAndIsDefaultBilling(User user, boolean isDefaultBilling);
	
	Optional<Profile> findByUserAndIsDefaultShipping(User user, boolean isDefaultBilling);

}
