package br.edu.ifba.embarcados.javaapp.asincexec;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifba.embarcados.javaapp.conector.FabricaConectores;
import br.edu.ifba.embarcados.javaapp.conector.IComunicacaoSensores;

public class AsincExec implements Runnable {
	
	private String porta;
	private boolean continuar;
	private List<IListenerBussola> Listeners;
	
	public AsincExec(String porta) {
		this.porta = porta;
		Listeners = new ArrayList<IListenerBussola>();
	}
	
	public void setContinuar(boolean continuar){
		this.continuar = continuar;
	}
	
	public void addListener(IListenerBussola listener){
		Listeners.add(listener);
	}

	@Override
	public void run() {
		
		IComunicacaoSensores conector = FabricaConectores.getConector();
		
		if (conector.iniciar(porta) == 0){
			
			continuar = true; 
			
			while(continuar){
				conector.ler();
				
				notificar(conector.getAcelX(), 
						  conector.getAcelY(), 
						  conector.getAcelZ());
				/*try { //no linux porque no windows deu um atraso
					Thread.sleep(100);
				} catch (InterruptedException e) {
					 TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			}
			conector.finalizar();
		}
	}	
	
	private void notificar(int x, int y, int z){
		
		for (IListenerBussola listener : Listeners){
			listener.notificarMovimento(x, y, z);
		}
	}
}
	

