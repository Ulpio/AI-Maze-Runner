import java.util.Arrays;
import java.util.LinkedList; // Importa a classe LinkedList
import java.util.Queue; // Importa a classe Queue
import java.util.Scanner;

public class Main{
    private static final int[] movimentos_linha = {-1,1,0,0}; // Vetor de movimentos possíveis para a linha
    private static final int[] movimentos_coluna ={0,0,-1,1}; // Vetor de movimentos possíveis para a coluna
    private static boolean debug = false; // Variável para debug

    public static void main(String[] args) {
        // Crie uma lista de labirintos com soluções diferentes e possíveis
        char[][][] labirintos ={
                {
                        {'S', '0', 'X', '0', 'X'},
                        {'X', '0', 'X', '0', 'X'},
                        {'0', '0', '0', '0', '0'},
                        {'0', 'X', 'X', 'X', '0'},
                        {'0', '0', '0', 'X', 'E'}
                },
                {
                        {'S', '0', '0', 'X', '0'},
                        {'X', 'X', '0', 'X', '0'},
                        {'0', '0', '0', '0', '0'},
                        {'0', 'X', 'X', 'X', '0'},
                        {'0', '0', '0', 'X', '0'},
                        {'0', '0', '0', '0', 'E'}
                },
                {
                        {'S', '0', '0', '0', '0'},
                        {'X', 'X', '0', 'X', '0'},
                        {'0', '0', '0', 'X', '0'},
                        {'0', 'X', 'X', 'E', 'X'},
                        {'0', '0', '0', '0', '0'}
                },
                {       {'S', 'X', '0', '0', '0'},
                        {'0', 'X', 'X', 'X', '0'},
                        {'0', '0', '0', '0', '0'},
                        {'0', 'X', '0', 'X', '0'},
                        {'X', '0', '0', 'X', 'E'}
                },
                {
                        {'S', '0', 'X', '0', '0'},
                        {'0', 'X', 'X', 'X', '0'},
                        {'0', '0', '0', '0', '0'},
                        {'0', 'X', 'X', 'X', '0'},
                        {'0', '0', 'X', '0', 'E'}
                },
                {
                        {'S', '0', '0', '0', '0'},
                        {'0', 'X', 'X', 'X', '0'},
                        {'0', '0', '0', '0', '0'},
                        {'0', 'X', 'X', 'X', '0'},
                        {'0', '0', '0', '0', 'E'}
                },
                {
                    // labirinto de 8x8
                    {'S', '0', '0', '0', '0', '0', '0', '0'},
                    {'0', 'X', 'X', 'X', 'X', 'X', 'X', '0'},
                    {'0', 'X', '0', '0', '0', '0', '0', '0'},
                    {'0', 'X', '0', 'X', 'X', 'X', 'X', '0'},
                    {'0', 'X', '0', 'X', '0', '0', '0', '0'},
                    {'0', 'X', '0', 'X', '0', 'X', 'X', '0'},
                    {'0', 'X', '0', 'X', '0', '0', 'X', '0'},
                    {'0', 'X', '0', 'X', 'X', '0', 'E', '0'}
                },
                {
                    //Labirinto de 10x10
                    {'S', '0', '0', '0', '0', '0', '0', '0', '0', 'X'},
                    {'0', 'X', 'X', 'X', 'X', 'X', 'X', '0', 'X', '0'},
                    {'0', 'X', '0', '0', '0', '0', '0', '0', 'X', '0'},
                    {'0', 'X', '0', 'X', 'X', 'X', 'X', 'X', 'X', '0'},
                    {'0', 'X', '0', 'X', '0', '0', 'X', '0', '0', '0'},
                    {'0', 'X', '0', 'X', 'X', 'X', 'X', '0', 'X', '0'},
                    {'0', 'X', '0', '0', '0', '0', '0', '0', 'X', '0'},
                    {'0', 'X', '0', 'X', 'X', 'X', 'X', 'X', 'X', '0'},
                    {'0', 'X', '0', '0', '0', '0', 'X', '0', '0', '0'},
                    {'0', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'E'}
                },
        };
        Ponto inicio = new Ponto(0,0,null); // Criação do ponto de início
        Scanner sc = new Scanner(System.in); // Criação de um objeto Scanner
        System.out.println("Deseja ver o debug? (s/n)"); // Pergunta se deseja ver o debug
        String resposta = sc.nextLine(); // Lê a resposta
        // Se a resposta for "n", o debug é desativado
        debug = resposta.equals("s"); // Se a resposta for "s", o debug é ativado
        // Para cada labirinto, encontre o caminho mais curto
        for (int i = 0; i < labirintos.length; i++) {
            // Numerar os labirintos ao imprimir
            System.out.println("Labirinto:"+ (i+1));
            imprimirLabirinto(labirintos[i]);
            if (encontrarCaminhoMaisCurto(labirintos[i], inicio, debug)) {
                System.out.println("Caminho encontrado:");
                imprimirLabirinto(labirintos[i]);
            } else {
                System.out.println("Caminho não encontrado.");
            }
        }
    }

    private static boolean encontrarCaminhoMaisCurto(char[][] labirinto,Ponto inicio,boolean debug){
        boolean [][] visitado = new boolean[labirinto.length][labirinto[0].length]; // Criação de uma matriz de booleanos para marcar os pontos visitados
        Queue<Ponto> fila = new LinkedList<>(); // Criação de uma fila para armazenar os pontos a serem visitados
        fila.add(inicio); // Adiciona o ponto inicial à fila
        visitado[inicio.x][inicio.y] = true; // Marca o ponto inicial como visitado
        while(!fila.isEmpty()){
            Ponto atual = fila.poll();
            if (debug) System.out.println("Visitando o ponto: ("+atual.x+","+atual.y+")");

            if (labirinto[atual.x][atual.y] == 'E'){
                marcarCaminho(labirinto,atual);
                return true;
            }

            for (int i = 0; i < 4; i++) {
                int novaLinha = atual.x + movimentos_linha[i];
                int novaColuna = atual.y + movimentos_coluna[i];

                if (movimentoValido(labirinto, novaLinha, novaColuna, visitado)) {
                    visitado[novaLinha][novaColuna] = true;
                    fila.add(new Ponto(novaLinha, novaColuna, atual));
                    if (debug) System.out.println("Adicionando ponto à fila: (" + novaLinha + ", " + novaColuna + ")");
                } else {
                    if (debug) System.out.println("Movimento inválido: (" + novaLinha + ", " + novaColuna + ")");
                }
            }
        }
        return false;
    }

    private static boolean movimentoValido(char[][] labirinto, int linha, int coluna, boolean[][] visitado) {
        return linha >= 0 && linha < labirinto.length && coluna >= 0 && coluna < labirinto[0].length && labirinto[linha][coluna] != 'X' && !visitado[linha][coluna];
    }

    private static void marcarCaminho(char[][] labirinto, Ponto fim) {
        Ponto atual = fim;
        while (atual != null) {
            if (labirinto[atual.x][atual.y] != 'S' && labirinto[atual.x][atual.y] != 'E') {
                labirinto[atual.x][atual.y] = '*';
            }
            atual = atual.pai;
        }
    }

    private static void imprimirLabirinto(char[][] labirinto) {
        for (char[] chars : labirinto) {
            for (int j = 0; j < labirinto[0].length; j++) {
                System.out.print(chars[j] + " ");
            }
            System.out.println();
        }
    }
}
