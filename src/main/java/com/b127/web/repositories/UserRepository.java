package com.b127.web.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.b127.web.entity.Profile;
import com.b127.web.entity.Ticket;
import com.b127.web.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findByUsername(String username);
	
	boolean existsByUsername(String username);
	
	@Query(value = "SELECT count(u) FROM User u JOIN u.userTickets AS t WHERE u.id = :id AND t.reply != '' AND t.isReplySeenByUser=true")
	int countReadMessagesByUser(@Param("id") long userId);
	
	@Query(value = "SELECT count(u) FROM User u JOIN u.userTickets AS t WHERE u.id = :id AND t.reply != '' AND t.isReplySeenByUser = false")
	int countUnreadMessagesByUser(@Param("id") long userId);
	
	@Query(value = "SELECT p FROM User u JOIN u.profiles AS p WHERE u.id = :id AND p.isDefaultShipping = true")
	Optional<Profile> getUserDefaultShippingProfile(@Param("id") long userId);
	
	@Query(value = "SELECT p FROM User u JOIN u.profiles AS p WHERE u.id = :id AND p.isDefaultBilling = true")
	Optional<Profile> getUserDefaultBillingProfile(@Param("id") long userId);
	
	@Query(value = "SELECT count(u) FROM User u JOIN u.adminReplies AS t WHERE u.id =:id AND t.isMessageSeenByAdmin=true")
	int countReadMessagesByAdmin(@Param("id") long userId);
	
	@Query(value = "SELECT count(u) FROM User u JOIN u.adminReplies AS t WHERE u.id =:id AND t.isMessageSeenByAdmin=false")
	int countUnreadMessagesByAdmin(@Param("id") long userId);
	
	List<User> findByRole(String role);
	
	@Query(value = "SELECT count(u) FROM User u  WHERE u.role ='user'")
	int countRegisteredUsersByAdmin();
	
	@Query(value = "SELECT max(u.id) FROM User u")
	Long getLatestId();
	
	@Query(value = "SELECT u FROM User u WHERE u.role = 'admin' AND u.id != :id")
	List<User> getAdminUsers(@Param("id") long id);
	
	@Query(value = "SELECT u FROM User u WHERE u.id != :id")
	List<User> getAllUsers(@Param("id") long id);
}
