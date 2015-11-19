package br.edu.ifba.embarcados.javaapp;

import br.edu.ifba.embarcados.javaapp.asincexec.IListenerBussola;
import java.math.*;

public class ListenerConsole implements IListenerBussola {

	@Override
	public void notificarMovimento(int x, int y, int z) {
		double heading = Math.atan2(y, x);
		heading += 0.4072;
		if(heading < 0)
			heading += 2*Math.PI;

		// Check for wrap due to addition of declination.
		if(heading > 2*Math.PI)
			heading -= 2*Math.PI;
		
		// Convert radianos para graus
		double headingDegrees = heading * 180/Math.PI;
		
		imprimirPosicoes((int) headingDegrees);
		
	}
	
	public void imprimirPosicoes(int graus) {
		if (graus < 45 && graus > 0)
		  {
		    System.out.println("Nordeste");
		  }

		  else if (graus < 90 && graus > 45)
		  {
			  System.out.println("Leste");
		  }
		  
		  else if (graus < 135 && graus > 90)
		  {
			  System.out.println("Sudeste");
		  }
		  else if (graus < 180 && graus > 135)
		  {
			  System.out.println("Sul");
		  }
		  else if (graus < 225 && graus > 180)
		  {
			  System.out.println("Sudoeste");
		  }
		  else if (graus < 270 && graus > 225)
		  {
			  System.out.println("Oeste");
		  }
		  else if (graus < 315 && graus > 270)
		  {
			  System.out.println("Noroeste");
		  }
		  else if (graus < 360 && graus > 315)
		  {
			  System.out.println("Norte");
		  }
	}

}
