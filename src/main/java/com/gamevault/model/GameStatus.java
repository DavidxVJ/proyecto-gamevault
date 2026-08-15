package com.gamevault.model;

//Si usaramos string y hay un error, este lo veriamos hasta la etapa de produccion debido a que nada nos impide guardar "Jugando", "playin", etc.
//Usaremos enum ya que nos obliga a usar uno de los valores definidos. Se pasa de error de runtime a un error de compilacion.
//Cuanto antes detectas un error, más barato es corregirlo
public enum GameStatus {
    BACKLOG,
    PLAYING,
    COMPLETED,
    DROPPED
}