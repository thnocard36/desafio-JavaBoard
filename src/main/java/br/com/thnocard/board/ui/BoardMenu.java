package br.com.thnocard.board.ui;

import br.com.thnocard.board.persistence.config.entity.BoardColumnEntity;
import br.com.thnocard.board.persistence.config.entity.BoardEntity;
import br.com.thnocard.board.persistence.config.entity.CardEntity;
import br.com.thnocard.board.services.BoardColumnQueryService;
import br.com.thnocard.board.services.BoardQueryService;
import br.com.thnocard.board.services.CardQueryService;
import br.com.thnocard.board.services.CardService;
import lombok.AllArgsConstructor;

import java.sql.SQLException;
import java.util.Scanner;

import static br.com.thnocard.board.persistence.config.config.ConnectionConfig.getConnection;
import static br.com.thnocard.board.persistence.config.entity.BoardColumnKindEnum.INITIAL;

@AllArgsConstructor
public class BoardMenu {

    private final Scanner scanner = new Scanner(System.in).useDelimiter("\n");
    private final BoardEntity entity;

    public void execute() {
        try {
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
                System.out.println("6- Vizualizar board");
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
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }

    // Alguns Métodos do Menú.
    private void createCard() throws SQLException {
        var card = new CardEntity();
        System.out.println("Informe o Titulo do Card");
        card.setTitle(scanner.next());

        System.out.println("Informe a Descrição Card");
        card.setDescription(scanner.next());
        card.setBoardColumn(entity.getBoardColumn());

        try(var connection = getConnection()) {
            new CardService(connection).insert(card);
        }
    }

    private void moveCardToNextColumn() {
    }

    private void blockCard() {
    }

    private void unblockCard() {
    }

    private void cancelCard() {
    }

    private void showBoard() throws SQLException {
        try(var connection = getConnection()) {
            var optional = new BoardQueryService(connection).showBoardDetails(entity.getId());
            optional.ifPresent(b -> {
                System.out.printf("Board [%s, %s]. \n", b.id(), b.name());
                b.columns().forEach(c -> {
                    System.out.printf("Coluna [%s], Tipo: [%s] tem %s cards. \n", c.name(),
                            c.kind().name(), c.cardsAmoutn());
                });
            });
        }
        System.out.println();
    }

    private void showColumn() throws SQLException{
        var columnsIds = entity.getBoardColumns().stream().map(BoardColumnEntity::getId).toList();
        var selectedColumn = -1L;
        while(!columnsIds.contains(selectedColumn)) {
            System.out.printf("Escolha uma coluna do board '%s' \n", entity.getName());
            entity.getBoardColumns().forEach(c -> System.out.printf("%s - %s [%s] \n",
                    c.getId(), c.getName(), c.getKind()));
            selectedColumn = scanner.nextLong();
        }

        try (var connection = getConnection()){
            var column = new BoardColumnQueryService(connection).findById(selectedColumn);
            column.ifPresent(col -> {
                System.out.printf("Coluna %s - Tipo %s \n", col.getName(), col.getKind());
                col.getCards().forEach(cae -> System.out.printf("Card %s - %s \nDescrição: %s",
                        cae.getId(), cae.getTitle(), cae.getDescription()));
            });
        }
    }

    private void showCard() throws SQLException {

        System.out.println("Informe o id do card que deseja visualizar:");
        var selectedCardId = scanner.nextLong();

        try(var connection = getConnection()) {
            new CardQueryService(connection).findById(selectedCardId)
                    .ifPresentOrElse(
                            c ->{
                                System.out.printf("Card %s - %s \n", c.id(), c.title());
                                System.out.printf("Descrição: %s \n", c.description());
                                System.out.println(c.blocked() ?
                                        "Está bloqueado. Por motivo: %s." + c.blockReason() :
                                        "Não está bloqueado!");
                                System.out.printf("Já foi bloqueado %s vezes.", c.blockAmount());
                                System.out.printf("Está no momento na coluna %s - %s. \n", c.columnId(), c.columnName());
                            },
                            () -> System.out.printf("Não existe um card com o id %s. \n", selectedCardId));
        }
        System.out.println();
    }
}