package it.polito.tdp.formulaone.model;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.formulaone.db.FormulaOneDAO;

public class Model {
	
	private FormulaOneDAO dao;
	private DriverIdMap map;
	private SimpleDirectedWeightedGraph<Driver, DefaultWeightedEdge> graph;
	private int bestScore;
	private List<Driver> soluzione;

	public Model() {
		dao = new FormulaOneDAO();
		map = new DriverIdMap();
		graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		bestScore = 10000000;
		soluzione = new ArrayList<Driver>();
	}
	
	public void creaGrafo(Year year) {
		
		Graphs.addAllVertices(graph, dao.getDriversOfTheYear(year, map));
		for(Driver d: graph.vertexSet()) {
			System.out.println(d.getSurname());
		}
		
		for(Driver d: graph.vertexSet()) {
			for(Driver dd: graph.vertexSet()) {
				if(d!=dd) {
					int peso = dao.getNumeroVittorie(d.getDriverId(), dd.getDriverId(), year);
					if(peso!=0) {
						Graphs.addEdge(graph, d, dd, peso);
						System.out.println(d.getSurname()+" "+dd.getSurname()+" "+peso);
					}
					
				}
			}
		}
		
	}
	
	public Driver getVincente() {
		int max = 0;
		Driver migliore = null;
		for(Driver d: graph.vertexSet()) {
			if((graph.outDegreeOf(d) - graph.inDegreeOf(d)) > max) {
				max = graph.outDegreeOf(d) - graph.inDegreeOf(d);
				migliore = d;
			}
		}
		
		return migliore;
	}
	
	public List<Season> getSeasons(){
		return dao.getAllSeasons();
	}
	
	public List<Driver> calcolaDreamTeam(int dimensione) {
		List<Driver> list = new ArrayList<Driver>();
		recursive(list, dimensione);
		return soluzione;
	}
	
	private void recursive(List<Driver> parziale, int dimensione) {
		
		// condizione terminale e controllo risultato
		if(parziale.size() == dimensione) {
			int score = calcolaPunteggio(parziale);
			if(score < bestScore) {
				bestScore = score;
				soluzione = new ArrayList<>(parziale);
				
			}
			return;
		}
		
		for(Driver d: graph.vertexSet()) {
			if(!parziale.contains(d)) {
				parziale.add(d);
				recursive(parziale, dimensione);
				parziale.remove(d);
			}
		}
	}

	public int calcolaPunteggio(List<Driver> parziale) {
		int result = 0;
		for(Driver d: graph.vertexSet()) {
			if(!parziale.contains(d))
				result+=graph.outDegreeOf(d);
		}
		return result;
	}
	

}
