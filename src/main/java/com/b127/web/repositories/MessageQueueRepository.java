package com.b127.web.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.b127.web.entity.MessageQueue;

public interface MessageQueueRepository extends JpaRepository<MessageQueue, Long>{
	
	@Query(value = "SELECT m FROM MessageQueue m WHERE m.currentMsgCount = ( SELECT min(mq.currentMsgCount) FROM MessageQueue mq )")
	List<MessageQueue> getMinimumMsgCountQueue();
	
	Optional<MessageQueue> findByAdminId(long adminId);
	

}
