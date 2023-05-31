package fp.cortes;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InformeCortes {
	private  String nombre;
	private  LocalDate fecha;
	private  List<CorteElectrico> cortes;
	
	public String getNombre() {
		return nombre;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public List<CorteElectrico> getCortes() {
		return cortes;
	}

	public Integer numeroCortes() {
		return cortes.size();
	}

	public InformeCortes(String nombre, LocalDate fecha, List<CorteElectrico> cortes) {
		this.nombre = nombre;
		this.fecha = fecha;
		this.cortes = null;
	}
	
	public InformeCortes(String nombre, LocalDate fecha, Stream<CorteElectrico> cortes) {
		this.nombre = nombre;
		this.fecha = fecha;
		this.cortes = cortes.collect(Collectors.toList());
	}
	@Override
	public String toString() {
		return "InformeCortes [nombre=" + nombre + ", fecha=" + fecha + ", cortes=" + cortes + ", numero de cortes()="
				+ numeroCortes() + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InformeCortes other = (InformeCortes) obj;
		return Objects.equals(cortes, other.cortes) && Objects.equals(fecha, other.fecha)
				&& Objects.equals(nombre, other.nombre);
	}
	
	//otras operaciones
	public void incorporaCorte(CorteElectrico c) {
		cortes.add(c);
		fecha = LocalDate.now();
	}
	
	public void incorporaCortes(List<CorteElectrico> cortes) {
		cortes.addAll(cortes);
		fecha = LocalDate.now();
	}
	
	public void eliminaCorte(CorteElectrico c) {
		cortes.remove(c);
	}
	
	public Double mediaAfectadosEnRegiones(Nivel s, Set<String> regiones) {
		Integer cont = 0;
		Integer acu = 0;
		for(CorteElectrico c:cortes) {
			if (c.severidad().equals(s) && regiones.contains(c.region())) {
				acu =+ c.consumidores();
				cont++;
			}
		}
		Double res = (double) (acu/cont);
		return res;
	}
	
	List<String> compañiasCortesMasRecientes(String etiqueta, Integer n) {
		return cortes.stream().
			filter(x->x.etiquetas().contains(etiqueta)).
			map(CorteElectrico::compania).
			limit(n).
			collect(Collectors.toList());
			
	}
	
	public SortedMap<String, SortedSet<String>> compañiasConCortesCriticosPorRegion() {
		return cortes.stream().
				filter(x->x.esCritico()).
				collect(Collectors.groupingBy(CorteElectrico::region, TreeMap::new, Collectors.mapping(CorteElectrico::compania, Collectors.toCollection(TreeSet::new))));
		
	}
	public Map<Nivel, Double> porcentajeCortesPorSeveridadEnRegion(String region) {
		Map<Nivel, Long> aux = cortes.stream().
				filter(x->x.region().equals(region)).
				collect(Collectors.groupingBy(CorteElectrico::severidad,Collectors.counting()));
		Long total = cortes.stream().filter(x->x.region().equals(region)).count();
		return aux.keySet().stream().collect(Collectors.toMap(x->x, x->aux.get(x)*100.0/total));
	}
}
