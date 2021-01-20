package principal;

import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		
		//Mientras no cortemos la ejecución
		while(true) {
			
			//Iniciamos un objeto scrapper
			Scrapper myScraper = new Scrapper();
			
			//Iniciamos el comportamiento espiar
			myScraper.espiar();
			
			//Iniciamos el comportamiento para escribir el fichero
			try {
				myScraper.escribirFichero("fichero.txt");
				//Dormimos al programa espía 5 minutos para evitar que "salten las alarmas"
				Thread.sleep(300000);
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}  
	}
}
