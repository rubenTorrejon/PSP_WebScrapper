package principal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scrapper {

	private int nombre = 0;
	private int ultimo = 0;
	private int fecha = 0;
	private int hora = 0;

	private StringBuilder rowBuilder = new StringBuilder();

	/**
	 * Constructor por defecto
	 */
	public Scrapper() {
		//Constructor sin par�metros
	}
	
	
	/**
	 * M�todo que visita la p�gina y lee la informaci�n
	 */
	public void espiar() {
		
		//Guardamos en un documento la direcci�n que queremos reconocer 
	    Document documento = null;
	    
	    //Realizamos un intento de conexi�n
	    try {
	        documento = Jsoup.connect("https://www.bolsamadrid.es/esp/aspx/Mercados/Precios.aspx?indice=ESI100000000&punto=indice").get();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    //Descargado el c�digo HTML, buscamos el id(#) de la tabla que queremos leer y nos posicionamos en la cabecera
	    Element tabla = documento.select("#ctl00_Contenido_tbl�ndice").first();
	    
	    //Accedemos al cuerpo de la tabla 
	    Element tbody = tabla.getElementsByTag("tbody").first();
	    
	    //Recorremos todos los tr que hay en nuestra tabla 
	    for (Element tr : tbody.getElementsByTag("tr")) { 
	        
	    	//Por cada tr le preguntamos si tiene th (Cabezera) y recorremos todo lo que contenga 
	    	for (Element th : tr.getElementsByTag("th")) {
	            
	    		//Establecemos las diferentes opciones para la cabecera
	    		switch(th.text()) {	
	    			case "Nombre":
	            		nombre = th.elementSiblingIndex();
	            		break;
	            	case "Fecha":
	            		fecha = th.elementSiblingIndex();
	            		break;
	            	case "�ltimo":
	            		ultimo = th.elementSiblingIndex();
	            		break;
	            	case "Hora":
	            		hora = th.elementSiblingIndex();
	            		break;
	            }
	    	}
	    	
	    	//A�adimos los td en una lista para poder seleccionar los que nos interesen
	    	Elements tdLista = tr.getElementsByTag("td"); 
	    	    
	    	//Incluimos en el rowBuiler los elementos que henmos obtenido antes
    	    if (tdLista.size() > 0){
    	    	rowBuilder.append(tdLista.get(nombre).text()+";");
    	    	rowBuilder.append(tdLista.get(ultimo).text()+";");
    	    	rowBuilder.append(tdLista.get(fecha).text()+";");
    	    	rowBuilder.append(tdLista.get(hora).text());
    	    }
    	    //Reiniciamos la lista para liberar memoria
    	    tdLista = null;
	    } 
    }
	
	
	/**
	 * M�todo para escribir la informacion de la lista en un fichero de texto
	 * @throws IOException 
	 */
	public void escribirFichero(String fichero) throws IOException {
	    
		//Iniciamos el escritor
	    BufferedWriter writer = null;
	    
	    //Escribimos la informaci�n de la lista en el fichero
	    try {
	        writer = new BufferedWriter(new FileWriter(new File(fichero),true));
	        writer.append(rowBuilder);
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        writer.close();
	    }
	    
	    //Iniciamos el rowBuilder para evitar la redundancia de informaci�n en la escritura 
	    rowBuilder = null;
	}
}
