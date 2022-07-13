package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {

	ImdbDAO dao ;
	Graph<Director,DefaultWeightedEdge> grafo;
	Map<Integer, Director> idMap;
	
	
	
	public Model() {
		dao = new ImdbDAO();
		idMap =new HashMap<>();
	}



	public void creaGrafo(int anno)
	{
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, this.dao.getVertici(anno, idMap));
		
		List<Adiacenze> conn1 = this.dao.getConnessione1(anno, idMap);
		List<Adiacenze> conn2 = this.dao.getConnessione2(anno, idMap);
//		List<Adiacenze> result  =  new ArrayList<Adiacenze>();
		
		for(Adiacenze a1: conn1)
		{
			for(Adiacenze a2: conn2)
			{
				if(a1.getD1()==a2.getD1() && a1.getD2()==a2.getD2())
				{
					int peso = a1.getPeso()+a2.getPeso();
					
					Graphs.addEdgeWithVertices(this.grafo, a1.getD1(), a1.getD2(), peso);
//					Adiacenze tot = new Adiacenze(a1.getD1(),a1.getD2(), peso);
//					result.add(tot);
				}
				
				Graphs.addEdgeWithVertices(this.grafo, a2.getD1(), a2.getD2(), a2.getPeso());
			}
			Graphs.addEdgeWithVertices(this.grafo, a1.getD1(), a1.getD2(), a1.getPeso());
		}
		
		
	}



	public int getNVertici() {
		
		return this.grafo.vertexSet().size();
	}



	public int getNArchi() {
		
		return this.grafo.edgeSet().size();
	}
	
	public List<Director> getDirettori(int anno)
	{
		return this.dao.getVertici(anno, idMap);
	}
	
	public List<DirectorAdiacenti> getAdiacenti(Director d)
	{
		List<Director> registi = Graphs.neighborListOf(this.grafo, d);
		List<DirectorAdiacenti> registiAdiacenti = new ArrayList<>();
		for(Director dd: registi)
		{
			DefaultWeightedEdge dwe =this.grafo.getEdge(d, dd);
			int peso =(int)this.grafo.getEdgeWeight(dwe);
			DirectorAdiacenti da = new DirectorAdiacenti(dd, peso);
			registiAdiacenti.add(da);
			
		}
		
		Collections.sort(registiAdiacenti);
		return registiAdiacenti;
	}
}
