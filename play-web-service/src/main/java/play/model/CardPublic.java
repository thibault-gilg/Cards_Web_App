package play.model;

public class CardPublic {

	public int id;

	public String userId;
	
	public String name;

	public String imgUrl;

	public String description;

	public int attack;
	
	public int defence;
	
	public int price;
	
	public CardPublic() {
		
	}

	public CardPublic(int id, String userId, String Name, String imgUrl, String Description, int Attack, int Defence, int price) {
		this.id = id;
		this.userId =userId;
		this.name = Name;
		this.imgUrl = imgUrl;
		this.description = Description;
		this.attack = Attack;
		this.defence = Defence;
		this.price = price;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefence() {
		return defence;
	}

	public void setDefence(int defence) {
		this.defence = this.defence + defence;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	public String toString() {
		return "Card Name : " + this.name + "\n " + 
				"Card Attack : " + this.attack + "\n" +
				"Card Defence : " + this.defence + "\n";
	}

}