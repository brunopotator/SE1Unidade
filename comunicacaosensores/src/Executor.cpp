/*
 * Executor.cpp
 *
 *  Created on: 15/11/2015
 *      Author: Mateus
 */

#include "Comunicacao.h"

#include <iostream>

using namespace std;

struct Eixos { // necessario inserir todos os eixos dos sensores
	short bussX, bussY, bussZ; // bussola
};


int main(int argc, char **argv) {
	//Criar instancia da classe de comunicação
	//Comunicacao com = Comunicacao("/dev/ttyACM0");
	Comunicacao com = Comunicacao("COM4");
	//enquanto estiver executando...
	//realizar a leitura do caractere I Inicio
	if (com.iniciar() == EXIT_SUCCESS) {
		char ci, cf;
		Eixos eixos;
		while (true) {
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
						//se tudo for atendido exibe os eixos do Bussola
						cout << "X = " << eixos.bussX << endl;
						cout << "Y = " << eixos.bussY << endl;
						cout << "Z = " << eixos.bussZ << endl;
					}
				}
			}
			Sleep(100);
		}
	}
	return EXIT_SUCCESS;
}


