package br.com.thnocard.board.ui;

import br.com.thnocard.board.persistence.config.entity.BoardEntity;
import lombok.AllArgsConstructor;

import java.util.Scanner;

@AllArgsConstructor
public class BoardMenu {

    private final BoardEntity entity;
    private final Scanner scanner = new Scanner(System.in);

    public void execute() {
        // System.out.printf("Bem vindo ao Board %s, selecione a operação desejada!", entity.getId());
        System.out.println();
        System.out.printf("*************** Bem Vindo(a) ao Board %s ***************", entity.getId());
        System.out.println();
        System.out.println("Escolha a opção desejada");

        var optionMenu = 0;
        while (optionMenu != 9) {
            System.out.println("1- Criar um card");
            System.out.println("2- Mover um card");
            System.out.println("3- Bloquear um card");
            System.out.println("4- Desbloquear um card");
            System.out.println("5- Cancelar um card");
            System.out.println("6- Vizualiar colunas");
            System.out.println("7- Vizualizar coluna com cards");
            System.out.println("8- Vizualizar cards");
            System.out.println("9- Voltar ao menú anterior um card");
            System.out.println("10- Sair");
            optionMenu = scanner.nextInt();

            switch (optionMenu) {
                case 1 -> createCard();
                case 2 -> moveCardToNextColumn();
                case 3 -> blockCard();
                case 4 -> unblockCard();
                case 5 -> cancelCard();
                case 6 -> showBoard();
                case 7 -> showColumn();
                case 8 -> showCard();
                case 9 -> System.out.println("Voltando par ao menu anterior..." + "\n");
                case 10 -> {
                    System.out.println();
                    System.out.println("Obrigado por utilizar este Board.");
                    System.out.println("Volte Sempre!");
                    System.out.println();
                    System.exit(0);
                }
                default -> {
                    System.out.println();
                    System.out.println("Opção inválida, informe uma opção do menú");
                    System.out.println();
                }
            }
        }

    }

    private void createCard() {
    }

    private void moveCardToNextColumn() {
    }

    private void blockCard() {
    }

    private void unblockCard() {
    }

    private void cancelCard() {
    }

    private void showBoard() {
    }

    private void showColumn() {
    }

    private void showCard() {
    }
}
