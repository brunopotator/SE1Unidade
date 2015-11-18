#include <Wire.h>
#include "HMC5883L.h"

HMC5883L bussola = HMC5883L();
int erro = 0;

struct Eixos { //deverá ter todos os eixos do sensores
  int bussX, bussY, bussZ; //eixos da bussola
};

Eixos eixos;

void setup() {
  Serial.begin(9600);
  Wire.begin();
  erro = bussola.SetScale(1.3); //seta a escala da bussola com o tempo geológico
  if (erro != 0)
    bussola.GetErrorText(erro);
    
  erro = bussola.SetMeasurementMode(Measurement_Continuous); //modo de medição contínuo
  if (erro != 0)
    bussola.GetErrorText(erro);
}

void loop() {
  
  MagnetometerScaled escalado = bussola.ReadScaledAxis(); //usa os valores conforme a escala estabelecida (1.3) e o não escalado
  
  eixos.bussX = escalado.XAxis;
  eixos.bussY = escalado.ZAxis;
  eixos.bussZ = escalado.ZAxis;
  
  enviarEixos();

  delay(104);
}

void enviarEixos() {
  int tam = sizeof(eixos); //retorna tamanho em bytes do argumento
  char buff[tam]; //cria um buffer com o tamanho da estrutura
  memcpy(&buff, &eixos, tam); //copia a estrutura (destino, origem, tamanho)

  Serial.write('I');
  Serial.write((uint8_t*)&buff, tam);
  Serial.write('F');  
}


