package tabuleiro;

public class Tabuleiro {
	
	private int linhas;
	private int colunas;
	private Peca[][] pecas;
	
	
	public Tabuleiro(int linhas, int colunas) {
		if(linhas<1 || colunas <1) {
			throw new TabuleiroExceptions("Erro criando o tabuleiro. é necessário ao menos uma linha e uma coluna.");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}


	public int getLinhas() {
		return linhas;
	}


	


	public int getColunas() {
		return colunas;
	}


	
	
	public Peca pecas(int linha,int coluna) {
		if(!posicaoExistente(linha, coluna)) {
			throw new TabuleiroExceptions("Posição não encontrada no tabuleiro.");
		}
		return pecas[linha][coluna];
		
	}
	
	public Peca peca(Posicao posicao) {
		if(!posicao_Existente(posicao)) {
			throw new TabuleiroExceptions("Posição não encontrada no tabuleiro.");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}
	
	public void lugar_da_Peca(Peca peca, Posicao posicao) {
		if(temUmaPecaNaPosicao(posicao)) {
			throw new TabuleiroExceptions("Já existe um peça na posição " + posicao);
		}
		this.pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}
	
	public Peca removePeca(Posicao posicao) {
           if(!posicao_Existente(posicao)) {
        	   throw new TabuleiroExceptions("Posição não encontrada no tabuleiro.");
           }
           if (peca(posicao) == null) {
        	   return null;
           }
           Peca aux = peca(posicao);
           aux.posicao = null;
           pecas[posicao.getLinha()][posicao.getColuna()] =null;
           return aux;
	}
	
	private boolean posicaoExistente(int linha,int coluna) {
		return linha >= 0 && linha < linhas && coluna >=0 && coluna < colunas;
	}
	
	
	public boolean posicao_Existente(Posicao posicao) {
		return posicaoExistente(posicao.getLinha(),posicao.getColuna());
	}
	
	public boolean temUmaPecaNaPosicao(Posicao posicao){
		if(!posicao_Existente(posicao)) {
			throw new TabuleiroExceptions("Posição não encontrada no tabuleiro.");
		}
		return peca(posicao) != null;
		
	}
	
	
	
	

}
