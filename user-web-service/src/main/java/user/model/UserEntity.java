package user.model;

import java.io.IOException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "user_entity")
public class UserEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(unique = true)
	private String name;
	
	@Column
	private String surname;
	
	@Column
	private String password;
	
	@Column
	private Integer money;
	
	
	public UserEntity() {
		this.money = 5000;

		
	}
	
	public UserEntity(String Name, String Surname, String Password) {
		this.name = Name;
		this.surname  = Surname;
		this.password = Password;
		this.money = 5000;
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public String toString() {
		return "Name : " + this.name + "\n" + " Surname : " + this.surname + "\n \n"; 
	}
	
	
	
}
