package it.polito.tdp.imdb.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Adiacenze;
import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Movie;

public class ImdbDAO {
	
	public List<Actor> listAllActors(){
		String sql = "SELECT * FROM actors";
		List<Actor> result = new ArrayList<Actor>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Actor actor = new Actor(res.getInt("id"), res.getString("first_name"), res.getString("last_name"),
						res.getString("gender"));
				
				result.add(actor);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Movie> listAllMovies(){
		String sql = "SELECT * FROM movies";
		List<Movie> result = new ArrayList<Movie>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Movie movie = new Movie(res.getInt("id"), res.getString("name"), 
						res.getInt("year"), res.getDouble("rank"));
				
				result.add(movie);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Director> listAllDirectors(){
		String sql = "SELECT * FROM directors";
		List<Director> result = new ArrayList<Director>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Director director = new Director(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
				
				result.add(director);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Director> getVertici(int anno, Map<Integer, Director> idMap){
		String sql = "SELECT Distinct  d.id, d.first_name, d.last_name "
				+ "FROM directors d, movies_directors md, movies m "
				+ "WHERE m.year = ? "
				+ "AND m.id = md.movie_id "
				+ "AND d.id = director_id "
				+ "GROUP BY d.id, d.first_name, d.last_name "
				+ "HAVING COUNT(d.id)>=1 "
				+ "ORDER BY d.first_name ";
		List<Director> result = new ArrayList<Director>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Director director = new Director(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
				idMap.put(director.getId(), director);
				
				result.add(director);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Adiacenze> getConnessione1(int anno, Map<Integer, Director> idMap){
		String sql = "SELECT DISTINCT md1.director_id as d1,md2.director_id as d2, COUNT(r1.actor_id) as peso "
				+ "FROM movies_directors md1,movies_directors md2, roles r1, roles r2, movies m1, movies m2 "
				+ "WHERE m1.year = ? "
				+ "AND m2.year = ? "
				+ "AND md1.director_id > md2.director_id "
				+ "AND md1.movie_id <> md2.movie_id "
				+ "AND r2.movie_id = md2.movie_id "
				+ "AND r1.movie_id = md1.movie_id "
				+ "AND r1.movie_id = m1.id "
				+ "ANd r2.movie_id = m2.id "
				+ "ANd r1.actor_id = r2.actor_id "
				+ "GROUP BY d1, d2 ";
		
		List<Adiacenze> result = new ArrayList<Adiacenze>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setInt(2, anno);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Adiacenze a = new Adiacenze(idMap.get(res.getInt("d1")), idMap.get(res.getInt("d2")),res.getInt("peso"));
				
				result.add(a);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Adiacenze> getConnessione2(int anno, Map<Integer, Director> idMap){
		String sql = "SELECT DISTINCT md1.director_id as d1,md2.director_id as d2, COUNT(r1.actor_id) as peso "
				+ "FROM movies_directors md1,movies_directors md2, roles r1, movies m1 "
				+ "WHERE m1.year = ? "
				+ "AND md1.director_id > md2.director_id "
				+ "AND md1.movie_id = md2.movie_id "
				+ "AND md1.movie_id = r1.movie_id "
				+ "ANd md1.movie_id = m1.id "
				+ "GROUP BY d1, d2 ";
		
		List<Adiacenze> result = new ArrayList<Adiacenze>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Adiacenze a = new Adiacenze(idMap.get(res.getInt("d1")), idMap.get(res.getInt("d2")),res.getInt("peso"));
				
				result.add(a);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	
}
