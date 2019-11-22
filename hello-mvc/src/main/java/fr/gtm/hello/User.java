package fr.gtm.hello;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Column(name="user")
	private String nom;
//	private String password;
	@Column(name="role")
	private String roles;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
//	public String getPassword() {
//		return password;
//	}
//	public void setPassword(String password) {
//		this.password = password;
//	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
//	public User(long id, String nom, String password, String roles) {
//		super();
//		this.id = id;
//		this.nom = nom;
//		this.password = password;
//		this.roles = roles;
//	}
	public User() {}
public User(long id, String nom, String roles) {
	super();
	this.id = id;
	this.nom = nom;
	this.roles = roles;
}
public User(String nom) {
	super();
	this.nom = nom;
}

}
