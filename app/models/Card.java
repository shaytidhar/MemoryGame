package models;

public class Card{
	Integer  	value = null;
	CardStatus 	status = null;
	
	public Integer getValue() {
		return value;
	}
	
	public void setValue(Integer value) {
		this.value = value;
	}
	
	public CardStatus getStatus() {
		return status;
	}
	
	public void setStatus(CardStatus status) {
		this.status = status;
	}
		
	/***
	 * Ctor
	 * Initialize the card by the given value 
	 * Sets it upside down
	 * 
	 * @param nCardValue
	 */
	public Card(Integer nCardValue) {
		this.setValue(nCardValue);
		this.setStatus(CardStatus.UPSIDE_DOWN);
	}	
	
	/***
	 * Copy Ctor
	 *  
	 * @param nCardValue
	 */
	public Card(Card crdCardToCopy) {
		this.setValue(crdCardToCopy.getValue());
		this.setStatus(crdCardToCopy.getStatus());
	}	
}
