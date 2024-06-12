import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    private static final int[] movimentos_linha = {-1, 1, 0, 0};
    private static final int[] movimentos_coluna = {0, 0, -1, 1};
    private static boolean debug = false;
    private static int currentMazeIndex = 0;
    private static char[][][] labirintos;
    private static MazePanel mazePanel;

    public static void main(String[] args) {
        labirintos = new char[][][]{
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
                {
                        {'S', 'X', '0', '0', '0'},
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

        Ponto inicio = new Ponto(0, 0, null);
        Scanner sc = new Scanner(System.in);
        System.out.println("Deseja ver o debug? (s/n)");
        String resposta = sc.nextLine();
        debug = resposta.equals("s");

        // Inicializar a interface gráfica
        SwingUtilities.invokeLater(() -> createAndShowGUI(inicio));
    }

    private static void createAndShowGUI(Ponto inicio) {
        JFrame frame = new JFrame("Labirinto");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());

        mazePanel = new MazePanel(labirintos[currentMazeIndex]);
        frame.add(mazePanel, BorderLayout.CENTER);

        JButton nextButton = new JButton("Próximo Labirinto");
        JButton prevButton = new JButton("Labirinto Anterior");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentMazeIndex < labirintos.length - 1) {
                    currentMazeIndex++;
                    mazePanel.setLabirinto(labirintos[currentMazeIndex]);
                    if (encontrarCaminhoMaisCurto(labirintos[currentMazeIndex], inicio, debug)) {
                        mazePanel.repaint();
                    } else {
                        System.out.println("Caminho não encontrado.");
                    }
                } else {
                    System.out.println("Todos os labirintos foram exibidos.");
                }
            }
        });
        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentMazeIndex > 0) {
                    currentMazeIndex--;
                    mazePanel.setLabirinto(labirintos[currentMazeIndex]);
                    if (encontrarCaminhoMaisCurto(labirintos[currentMazeIndex], inicio, debug)) {
                        mazePanel.repaint();
                    } else {
                        System.out.println("Caminho não encontrado.");
                    }
                } else {
                    System.out.println("Você está no primeiro labirinto.");
                }
            }
        });
        frame.add(nextButton, BorderLayout.SOUTH);
        frame.add(prevButton, BorderLayout.NORTH);

        frame.setVisible(true);

        if (encontrarCaminhoMaisCurto(labirintos[currentMazeIndex], inicio, debug)) {
            mazePanel.repaint();
        } else {
            System.out.println("Caminho não encontrado.");
        }
    }

    private static boolean encontrarCaminhoMaisCurto(char[][] labirinto, Ponto inicio, boolean debug) {
        boolean[][] visitado = new boolean[labirinto.length][labirinto[0].length];
        Queue<Ponto> fila = new LinkedList<>();
        fila.add(inicio);
        visitado[inicio.x][inicio.y] = true;

        while (!fila.isEmpty()) {
            Ponto atual = fila.poll();
            if (debug) System.out.println("Visitando o ponto: (" + atual.x + "," + atual.y + ")");

            if (labirinto[atual.x][atual.y] == 'E') {
                marcarCaminho(labirinto, atual);
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
}

