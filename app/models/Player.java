package models;

public class Player {
	private int 	id;
	private String 	name;
	private int		score;
		
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public int getScore() {
		return score;
	}
	
	public void increaseScore() {
		this.score += 10;
	}

	/***
	 * Ctor
	 * @param name
	 */
	public Player(int id, String name) {
		super();
		
		this.id = id;
		this.name = name;
		this.score = 0;
	}	
}
