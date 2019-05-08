package application;


import xadrez.Partida_de_Xadrez;

public class Program {

	public static void main(String[] args) {
		
		Partida_de_Xadrez partidaXadrez = new Partida_de_Xadrez();
        UI.placaImpressao(partidaXadrez.getPecas()); 
	}

}
