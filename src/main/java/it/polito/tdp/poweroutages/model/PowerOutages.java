package it.polito.tdp.poweroutages.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class PowerOutages {
	
	private int id;
	private int nerc_id;
	private int customers_affected;
	private LocalDateTime dataInizio;
	private LocalDateTime dataFine;
	
	public PowerOutages(int id, int nerc_id, int customers_affected, LocalDateTime dataInizio, LocalDateTime dataFine) {
		this.id = id;
		this.nerc_id = nerc_id;
		this.customers_affected = customers_affected;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
	}
	
	public int getYear() {
		return dataInizio.getYear();
	}
	
	public long getDuration() {
		return Duration.between(dataInizio, dataFine).toHours();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNerc_id() {
		return nerc_id;
	}

	public void setNerc_id(int nerc_id) {
		this.nerc_id = nerc_id;
	}

	public int getCustomers_affected() {
		return customers_affected;
	}

	public void setCustomers_affected(int customers_affected) {
		this.customers_affected = customers_affected;
	}

	public LocalDateTime getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(LocalDateTime dataInizio) {
		this.dataInizio = dataInizio;
	}

	public LocalDateTime getDataFine() {
		return dataFine;
	}

	public void setDataFine(LocalDateTime dataFine) {
		this.dataFine = dataFine;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		PowerOutages other = (PowerOutages) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return this.getYear()+" "+dataInizio+" "+dataFine+" "+this.getDuration()+" "+customers_affected+"\n";
	}

}
