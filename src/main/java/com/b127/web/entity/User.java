package com.b127.web.entity;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "users")
public class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstname;
	private String lastname;
	private String username;
	private String email;
	private String password;
	private String role;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dob;
	private String address;
	private String city;
	private String country;
	private LocalDate lastLogged;
	private LocalDate lastProfileUpdate;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Profile> profiles;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Credit> creditProfiles;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Ticket> userTickets;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "receivedAdmin", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Ticket> adminReplies;
	

	public User() {

	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public void setProfiles(Set<Profile> profiles) {
		this.profiles = profiles;
	}
	
	public Set<Profile> getProfiles() {
		return profiles;
	}
	
	public void setCreditProfiles(Set<Credit> creditProfiles) {
		this.creditProfiles = creditProfiles;
	}
	
	public Set<Credit> getCreditProfiles() {
		return creditProfiles;
	}
	
	public void setUserTickets(Set<Ticket> userTickets) {
		this.userTickets = userTickets;
	}
	
	public Set<Ticket> getUserTickets() {
		return userTickets;
	}
	
	public void setAdminReplies(Set<Ticket> adminReplies) {
		this.adminReplies = adminReplies;
	}
	
	public Set<Ticket> getAdminReplies() {
		return adminReplies;
	}
	
	public void setLastLogged(LocalDate lastLogged) {
		this.lastLogged = lastLogged;
	}
	
	public LocalDate getLastLogged() {
		return lastLogged;
	}
	
	public void setLastProfileUpdate(LocalDate lastProfileUpdate) {
		this.lastProfileUpdate = lastProfileUpdate;
	}
	
	public LocalDate getLastProfileUpdate() {
		return lastProfileUpdate;
	}
}
