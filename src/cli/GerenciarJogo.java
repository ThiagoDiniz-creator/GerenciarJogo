package cli;

import model.*;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class GerenciarJogo {
    private static void truco(JogadorBaralho jogadorBaralho1, JogadorBaralho jogadorBaralho2,
                              RodadaTruco rodadaTruco, JogoTruco jogoTruco) {
        if (11 > rodadaTruco.getValor() && 11 > jogoTruco.getTentosTimeA() && 11 > jogoTruco.getTentosTimeB()) {
            if (jogadorBaralho2.isCPU()) {
                boolean aceitarDecisao = jogadorBaralho2.tomarDecisao(18);
                if (aceitarDecisao) {
                    boolean trucar = jogadorBaralho2.tomarDecisao(21);
                    rodadaTruco.incrementValorDaRodada();
                    if (trucar) {
                        System.out.println(jogadorBaralho2.getNome() + " RETRUCOU");
                        truco(jogadorBaralho2, jogadorBaralho1, rodadaTruco, jogoTruco);
                    } else {
                        System.out.println(jogadorBaralho2.getNome() + " CHAMOU");
                    }
                } else {
                    System.out.println(jogadorBaralho2.getNome() + " FUGIU");
                    rodadaTruco.encerrarComTruco(jogoTruco.obterTimeDoJogador(jogadorBaralho1));
                }
            } else {
                System.out.println("1 - Para chamar. 2 - para trucar novamente. 3 - para fugir.");
                int escolha = Integer.parseInt(new Scanner(System.in).nextLine());

                if (escolha == 2) {
                    System.out.println("Você retrucou");
                    rodadaTruco.incrementValorDaRodada();
                    truco(jogadorBaralho2, jogadorBaralho1, rodadaTruco, jogoTruco);
                } else if (escolha == 3) {
                    System.out.println("Você fugiu");
                    rodadaTruco.encerrarComTruco(jogoTruco.obterTimeDoJogador(jogadorBaralho1));
                } else {
                    System.out.println("Você chamou!");
                }
            }
        } else {
            System.out.println("O truco já está no valor máximo (12 tentos)");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        JogadorBaralho jogadorBaralho1 = new JogadorBaralho(0, "Jogador");
        JogadorBaralho jogadorBaralho2 = new JogadorBaralho(1, "Oponente", true);

        RodadaTruco rodadaTruco = new RodadaTruco();
        JogoTruco jogoTruco = new JogoTruco();
        jogoTruco.registrarTimes(List.of(jogadorBaralho1), List.of(jogadorBaralho2));
        jogoTruco.setBaralho(new Baralho());
        jogoTruco.setRodadaTruco(rodadaTruco);

        int numeroGlobalDeTurnos = 1;

        System.out.println("Início do truco!");

        while (jogoTruco.isAberto()) {
            System.out.println("---- RODADA: " + (jogoTruco.getRodada()) + " ----");

            System.out.println("A vira é " + jogoTruco.iniciarRodada().getValor());
            waitMilli(200);

            while (rodadaTruco.isEmDisputa()) {
                // Condições 11
                if (jogoTruco.getTentosTimeA() == 11 && jogoTruco.getTentosTimeB() == 1) {
                    System.out.println("Rodada às cegas.");
                    while (rodadaTruco.isEmDisputa()) {
                        Carta carta1, carta2 = null;
                        if (numeroGlobalDeTurnos % 2 == 0) {
                            carta2 = jogadorBaralho2.jogarCartaAleatoria();
                            System.out.println(jogadorBaralho2.getNome() + " jogou " + carta2);
                        }
                        carta1 = jogadorBaralho1.jogarCartaAleatoria();
                        System.out.println(jogadorBaralho1.getNome() + " jogou " + carta1);

                        if (numeroGlobalDeTurnos % 2 != 0) {
                            carta2 = jogadorBaralho2.jogarCartaAleatoria();
                            System.out.println(jogadorBaralho2.getNome() + " jogou " + carta2);
                        }
                        Carta cartaVencedora = jogoTruco.compararCartas(carta1, carta2);
                        pontuarTurno(rodadaTruco, carta1, carta2, cartaVencedora);

                    }
                } else if (jogoTruco.getTentosTimeA() == 11) {
                    System.out.println("Você pode jogar ou não. (1 = sim, 2 = não)");
                    int decisao = Integer.parseInt(sc.nextLine());
                    if (decisao == 2) {
                        jogoTruco.incrementTentosTime2(1);
                        break;
                    }
                } else if (jogoTruco.getTentosTimeB() == 11) {
                    if (!jogadorBaralho2.tomarDecisao(18)) {
                        jogoTruco.incrementTentosTime1(1);
                        break;
                    }
                }

                // Rodada comum
                System.out.println("---- TURNO: " + (rodadaTruco.getTurnoAtual() + 1) + " ----");

                Carta carta1, carta2 = null;
                if (numeroGlobalDeTurnos % 2 == 1) {
                    System.out.println(jogadorBaralho1.getNome() + " distribuiu as cartas.");
                    carta2 = jogadorBaralho2.jogarCartaAleatoria();
                    System.out.printf("%s jogou %s\n", jogadorBaralho2.getNome(), carta2);
                } else {
                    System.out.println(jogadorBaralho2.getNome() + " distribuiu as cartas.");
                }

                // Obter jogada
                int jogada = GerenciarJogo.obterJogada(sc, jogadorBaralho1, rodadaTruco, jogoTruco);

                if (jogada == 2)
                    if (12 > rodadaTruco.getValor())
                        GerenciarJogo.truco(jogadorBaralho1, jogadorBaralho2, rodadaTruco, jogoTruco);
                    else
                        System.out.println("O valor da aposta já está no máximo (12)");

                // Se a rodada ainda está em disputa (não houve fuga de um possível truco)
                if (rodadaTruco.isEmDisputa()) {
                    jogadorBaralho1.exibirCartas(1);
                    carta1 = jogadorBaralho1.jogarCarta(Integer.parseInt(sc.nextLine()) - 1);

                    if (numeroGlobalDeTurnos % 2 == 0) {
                        carta2 = jogadorBaralho2.jogarCartaAleatoria();
                        System.out.printf("%s jogou %s\n", jogadorBaralho2.getNome(), carta2);
                    }
                    Carta cartaVencedora = jogoTruco.compararCartas(carta1, carta2);
                    pontuarTurno(rodadaTruco, carta1, carta2, cartaVencedora);
                }
                numeroGlobalDeTurnos += 1;
            }

            GerenciarJogo.pontuarRodada(jogoTruco, rodadaTruco);
            rodadaTruco.reciclar();
            jogoTruco.incrementRodada();
            jogoTruco.updateGameState();
            System.out.println("Tentos do jogador: " + jogoTruco.getTentosTimeA() +
                    " | Tentos do oponente: " + jogoTruco.getTentosTimeB());
        }


        if (jogoTruco.getTentosTimeA() > 11) {
            System.out.println("Parabéns, você venceu a partida :)");
        } else if (jogoTruco.getTentosTimeB() > 11) {
            System.out.println("Que pena, você foi derrotado :(");
        }
    }


    private static void pontuarRodada(JogoTruco jogoTruco, RodadaTruco rodadaTruco) {
        if (rodadaTruco.getVencedor() == 1)
            jogoTruco.incrementTentosTime1(rodadaTruco.getValor());
        else
            jogoTruco.incrementTentosTime2(rodadaTruco.getValor());
    }

    private static void pontuarTurno(RodadaTruco rodadaTruco, Carta carta1, Carta carta2, Carta cartaVencedora) {
        if (cartaVencedora == carta1) {
            System.out.println("O jogador venceu com " + carta1);
            rodadaTruco.finishTurno(1);
        } else if (cartaVencedora == carta2) {
            System.out.println("O oponente venceu com " + carta2);
            rodadaTruco.finishTurno(2);
        } else {
            System.out.println("O turno terminou empatado");
            rodadaTruco.finishTurno(0);
        }
    }

    private static int obterJogada(Scanner sc, JogadorBaralho jogadorBaralho, RodadaTruco rodadaTruco, JogoTruco jogoTruco) {
        System.out.println("Cartas disponíveis: ");
        jogadorBaralho.exibirCartasLinha();
        System.out.println("Possíveis jogadas:");
        System.out.println("1- Jogar uma carta");
        if (11 > rodadaTruco.getValor() && 11 > jogoTruco.getTentosTimeA() && 11 > jogoTruco.getTentosTimeB()) {
            System.out.println("2- Trucar");
        }
        return Integer.parseInt(sc.nextLine());
    }

    public static void waitMilli(int milli) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(milli);
    }
}