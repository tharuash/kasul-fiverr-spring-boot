package com.b127.web.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.b127.web.entity.Ticket;
import com.b127.web.entity.User;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>{
	
	List<Ticket> findBySenderAndIsReplySeenByUser(User sender, boolean isReplySeenByUser);
	
	@Query(value = "SELECT t FROM Ticket t WHERE t.sender = :sender AND t.reply != '' AND t.isReplySeenByUser = false")
	List<Ticket> getUserUnreadTickets(@Param("sender") User sender);
	
	@Query(value = "SELECT t FROM Ticket t WHERE t.receivedAdmin = :receivedAdmin AND t.reply IS NULL AND t.isMessageSeenByAdmin = false")
	List<Ticket> getAdminUnreadTickets(@Param("receivedAdmin") User receivedAdmin);
	
	@Modifying(clearAutomatically = true)
	@Query("Update Ticket t SET t.isReplySeenByUser = true WHERE t.id =:id")
	void updateTicketAsSeenByUser(@Param("id") long id);
	
	List<Ticket> findByReceivedAdmin(User receivedAdmin);

	@Modifying(clearAutomatically = true)
	@Query("Update Ticket t SET t.isMessageSeenByAdmin = true WHERE t.id =:id")
	void updateTicketAsSeenByAdmin(@Param("id") long id);
	
	@Modifying(clearAutomatically = true)
	@Query("Update Ticket t SET t.reply =:reply , t.dateReplied =:date WHERE t.id =:id")
	void updateTicketReplyByAdmin(@Param("id") long id, @Param("reply") String reply,@Param("date")LocalDate date );
}
