/*
 * Extern.cpp
 *
 *  Created on: 15/11/2015
 *      Author: Mateus
 */


#include "Extern.h"
#include "Comunicacao.h"

struct Eixos { // necessario inserir todos os eixos dos sensores
	short acelx, acely, acelz; // acelerômetro
	//int girox, giroy, giroz; // giroscópio
};
Eixos eixos;

Comunicacao com = NULL;

int iniciar(char* porta) {
	com = Comunicacao(porta);
	return com.iniciar();
}

int ler() {
	com.ler((char*) &eixos, sizeof(eixos));
	char ci, cf;
	Eixos eixos;
	int resultado = com.ler((char*) &ci, sizeof(ci));
	if (resultado == EXIT_SUCCESS && (ci == 'I')) {
		//Se ocorrer tudo bem
		//Ler os eixos
		resultado = com.ler((char*) &eixos, sizeof(eixos));
		if (resultado == EXIT_SUCCESS) {
			//Se ler os eixos e carateres ocorrer bem
			//Ler o caractere F Final
			resultado = com.ler((char*) &cf, sizeof(cf));
			if ((resultado == EXIT_SUCCESS) && (cf == 'F')) {
				//se tudo for atendido exibe os eixos co Acelerometro
				resultado = EXIT_SUCCESS;
			}
		}
	}
	return resultado;
}
int getAcelX() {
	return eixos.acelx;
}
int getAcelY() {
	return eixos.acely;
}
int getAcelZ() {
	return eixos.acelz;
}
int finalizar() {
	return com.finalizar();
}

