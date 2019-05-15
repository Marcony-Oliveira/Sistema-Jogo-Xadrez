package application;


import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.Partida_de_Xadrez;
import xadrez.Peca_de_Xadrez;
import xadrez.XadrezExceptions;
import xadrez.XadrezPosicao;

public class Program {

	public static void main(String[] args) {
		
		Partida_de_Xadrez partidaXadrez = new Partida_de_Xadrez();
        Scanner read = new Scanner (System.in);
        
        List <Peca_de_Xadrez> capturadas = new ArrayList<>();		
		
        
        while(true) {
			try {
			    UI.limpaTela();
		        UI.impressaoJogo(partidaXadrez,capturadas); 
		        System.out.println("");
		        System.out.println("ORIGEM: ");
		        XadrezPosicao origem = UI.lerPosicaoXadrez(read);
		
		        boolean [][] possiveisMovimentos = partidaXadrez.possiveisMovimentos(origem);
		        UI.limpaTela();
		        UI.placaImpressao(partidaXadrez.getPecas(),possiveisMovimentos);
		        
		        System.out.println("");
		        System.out.println("DESTINO: ");
		        XadrezPosicao destino = UI.lerPosicaoXadrez(read);
		
		        Peca_de_Xadrez pecaCapturada = partidaXadrez.realizarMovimentoDeXadrez(origem,destino);
		        
		        if (pecaCapturada != null) {
		        	capturadas.add(pecaCapturada);
		        }
			}
			catch(XadrezExceptions e){
				System.out.println(e.getMessage());
				read.nextLine();
			}
			catch(InputMismatchException e){
				System.out.println(e.getMessage());
				read.nextLine();
			}
			
		}
	}

}
