package br.edu.ifba.embarcados.javaapp.asincexec;

public interface IListenerBussola {
	// observador
	// notifica tudo o que a bussola est� fazendo
	
	public void notificarMovimento(int x, int y, int z);
	
	
}
