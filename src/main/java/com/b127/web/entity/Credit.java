package com.b127.web.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "credit_profiles")
public class Credit {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String cardType;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate expirationDate;
	private String cardHolderName;
	private String cardNumber;
	private boolean isDefault;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", insertable = true, updatable = true)
	private User user;

	public Credit() {

	}

	public Credit(long id, String cardType, LocalDate expirationDate, String cardHolderName, String cardNumber,
			boolean isDefault, User user) {
		super();
		this.id = id;
		this.cardType = cardType;
		this.expirationDate = expirationDate;
		this.cardHolderName = cardHolderName;
		this.cardNumber = cardNumber;
		this.isDefault = isDefault;
		this.user = user;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
