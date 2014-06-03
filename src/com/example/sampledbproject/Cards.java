package com.example.sampledbproject;

public class Cards {
	int card_id;
	String card_account;
	String card_name;

	// constructors
	public Cards(){
		
	}
	
	public Cards(String name, String account){
		this.card_name = name;
		this.card_account = account;
	}

	public Cards(int id, String name, String account){
		this.card_id = id;
		this.card_name = name;
		this.card_account = account;
	}
	
	//setters
	public void setCardId(int id){
		this.card_id = id;
	}
	
	public void setCardName(String name){
		this.card_name = name;
	}
	
	public void setCardAccount(String account){
		this.card_account = account;
	}
	
	//getters
	public int getCardId(){
		return this.card_id;
	}
	
	public String getCardName(){
		return this.card_name;
	}
	
	public String getCardAccount(){
		return this.card_account;
	}
}
