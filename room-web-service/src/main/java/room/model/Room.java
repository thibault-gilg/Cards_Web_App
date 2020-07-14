package room.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Room {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column
	private String name;
	
	@Column
	private int mise;
	
	@Column
	private int id_joueur1;
	
	@Column
	private int id_joueur2;
	
	public Room() {
		
	}
	
	public Room(int id_joueur1, String name, String mise) {
		this.id_joueur1 = id_joueur1;
		this.setName(name);
		this.mise = Integer.parseInt(mise);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String nom) {
		this.name = nom;
	}

	public int getMise() {
		return mise;
	}

	public void setMise(int mise) {
		this.mise = mise;
	}
	
	public int getId_joueur1() {
		return id_joueur1;
	}

	public void setId_joueur1(int id_joueur1) {
		this.id_joueur1 = id_joueur1;
	}

	public int getId_joueur2() {
		return id_joueur2;
	}

	public void setId_joueur2(int id_joueur2) {
		this.id_joueur2 = id_joueur2;
	}

	public String toString() {
		return "Room " +  this.getName() + " mise de " + this.getMise();
	}
}
