/*
 * Extern.h
 *
 *  Created on: 15/11/2015
 *      Author: Mateus
 */

#ifndef EXTERN_H_
#define EXTERN_H_

#ifdef __cplusplus
extern "C" {
#endif

int iniciar(char* porta);
int ler();
int getBussX();
int getBussY();
int getBussZ();
int finalizar();


#ifdef __cplusplus
}
#endif



#endif /* EXTERN_H_ */
