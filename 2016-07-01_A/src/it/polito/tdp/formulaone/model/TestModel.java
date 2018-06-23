package it.polito.tdp.formulaone.model;

import java.time.Year;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		
		model.creaGrafo(Year.of(2009));
		
		model.calcolaDreamTeam(3);

	}

}
