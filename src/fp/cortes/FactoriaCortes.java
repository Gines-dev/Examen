package fp.cortes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fp.utiles.Checkers;

public class FactoriaCortes {
	
	public static InformeCortes leerCortes(String nombreFichero, LocalDate fecha, String nombre) {
		InformeCortes res = null;
		try {
			Stream<CorteElectrico> stCortes = Files.lines(Paths.get(nombreFichero))
				.skip(1)
				.map(FactoriaCortes::parsearCortes);
			res = new InformeCortes(nombre, fecha, stCortes);
		} catch(IOException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	private static CorteElectrico parsearCortes(String lineaCSV) {
		String[] trozos = lineaCSV.split(";");
		Checkers.check("No executa", trozos.length==7);
		String descripcion = trozos[0].trim();
		LocalDateTime fechaInicio = LocalDateTime.parse(trozos[1].trim(), DateTimeFormatter.ofPattern("d/M/yy-H:m"));
		LocalDateTime fechaRestablecimiento = LocalDateTime.parse(trozos[2].trim(), DateTimeFormatter.ofPattern("d/M/yy-H:m"));
		String compania = trozos[3].trim();
		String region = trozos[4].trim();
		Double perdida = Double.valueOf(trozos[5].trim());
		Integer consumidores = Integer.valueOf(trozos[6].trim());
		List<String> etiquetas = parseaEtiq(trozos[7].trim());
		return new CorteElectrico(descripcion, fechaInicio, fechaRestablecimiento, compania, region, perdida, consumidores, etiquetas);
		
	}
	
	private static List<String> parseaEtiq(String cad) {
		String[] trozos = cad.split(cad);
		return Arrays.stream(trozos).
				map(String::trim).
				collect(Collectors.toList());
	}
	
			
	
}
