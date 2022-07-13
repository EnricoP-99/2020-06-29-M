package it.polito.tdp.imdb.model;

public class DirectorAdiacenti implements Comparable<DirectorAdiacenti>{

	private Director d1;
	private int peso;
	public DirectorAdiacenti(Director d1, int peso) {
		super();
		this.d1 = d1;
		this.peso = peso;
	}
	public Director getD1() {
		return d1;
	}
	public void setD1(Director d1) {
		this.d1 = d1;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((d1 == null) ? 0 : d1.hashCode());
		result = prime * result + peso;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DirectorAdiacenti other = (DirectorAdiacenti) obj;
		if (d1 == null) {
			if (other.d1 != null)
				return false;
		} else if (!d1.equals(other.d1))
			return false;
		if (peso != other.peso)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return  d1 + " - #attori condivisi: " + peso ;
	}
	@Override
	public int compareTo(DirectorAdiacenti o) {
		// TODO Auto-generated method stub
		return o.peso-this.peso;
	}
	
	
}
