package fp.cortes;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import fp.utiles.Checkers;

public record CorteElectrico(String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaRestablecimiento, String compania, String region, Double perdida, Integer consumidores, List<String> etiquetas) implements Comparable<CorteElectrico>{

	@Override
	public int compareTo(CorteElectrico o) {
		// TODO Auto-generated method stub
		return 0;
	}
	public CorteElectrico(String descripcion, LocalDateTime fechaInicio, LocalDateTime fechaRestablecimiento, String compania, String region, Double perdida, Integer consumidores, List<String> etiquetas) {
		Checkers.check("La fecha de restablecimiento debe ser igual o posterior a la fecha de inicio del corte", fechaRestablecimiento.isAfter(fechaInicio) || fechaRestablecimiento.equals(fechaInicio));
		Checkers.check("el número de consumidores afectados debe ser mayor o igual que 0, o null.", consumidores>=0);
		Checkers.checkNoNull(consumidores);
		Checkers.check("la lista de etiquetas debe contener al menos una etiqueta.", etiquetas.isEmpty());
		
		this.descripcion = descripcion;
		this.fechaInicio = fechaInicio;
		this.fechaRestablecimiento = fechaRestablecimiento;
		this.compania = compania;
		this.region = region;
		this.perdida = perdida;
		this.consumidores = consumidores;
		this.etiquetas = etiquetas;
		}
	/**
	 * @return the descripcion
	 */
	public String descripcion() {
		return descripcion;
	}
	/**
	 * @return the fechaInicio
	 */
	public LocalDateTime fechaInicio() {
		return fechaInicio;
	}
	/**
	 * @return the fechaRestablecimiento
	 */
	public LocalDateTime fechaRestablecimiento() {
		return fechaRestablecimiento;
	}
	/**
	 * @return the compania
	 */
	public String compania() {
		return compania;
	}
	/**
	 * @return the region
	 */
	public String region() {
		return region;
	}
	/**
	 * @return the perdida
	 */
	public Double perdida() {
		return perdida;
	}
	/**
	 * @return the consumidores
	 */
	public Integer consumidores() {
		return consumidores;
	}
	/**
	 * @return the etiquetas
	 */
	public List<String> etiquetas() {
		return etiquetas;
	}
	
	public Boolean esCritico() {
		Integer duracion = (int) fechaInicio.until(fechaRestablecimiento, ChronoUnit.HOURS);
		Boolean res = false;
		if (perdida >200 || duracion > 10) {
			res = true;
		}
		return res;
	}
	public Nivel severidad() {
		Nivel res = null;
		if (consumidores<1000 || consumidores == null) {
			res = Nivel.BAJO;
		}
		else {
			if (consumidores>1000 && consumidores<100000) {
				res = Nivel.MEDIO;
			}
			else {
				if (consumidores>100000) {
					res = Nivel.ALTO;
				
			}
		}
	}
		return res;
	}
	@Override
	public int hashCode() {
		return Objects.hash(fechaInicio, region);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CorteElectrico other = (CorteElectrico) obj;
		return Objects.equals(fechaInicio, other.fechaInicio) && Objects.equals(region, other.region);
	}
	@Override
	public String toString() {
		return "CorteElectrico [descripcion=" + descripcion + ", fechaInicio=" + fechaInicio
				+ ", fechaRestablecimiento=" + fechaRestablecimiento + ", compania=" + compania + ", region=" + region
				+ ", perdida=" + perdida + ", consumidores=" + consumidores + ", etiquetas=" + etiquetas
				+ ", es critico=" + esCritico() + ", severidad=" + severidad() + "]";
	}
	
	
	
	

	
	}
