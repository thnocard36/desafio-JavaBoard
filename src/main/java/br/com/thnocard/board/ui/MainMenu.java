package br.com.thnocard.board.ui;

import br.com.thnocard.board.persistence.config.entity.BoardColumnEntity;
import br.com.thnocard.board.persistence.config.entity.BoardColumnKindEnum;
import br.com.thnocard.board.persistence.config.entity.BoardEntity;
import br.com.thnocard.board.services.BoardQueryService;
import br.com.thnocard.board.services.BoardServices;

import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static br.com.thnocard.board.persistence.config.config.ConnectionConfig.getConnection;

public class MainMenu {

    private final Scanner scanner = new Scanner(System.in);

    public void execute() throws SQLException {
        System.out.println();
        System.out.println("*************** Bem Vindo ao Gerenciador de Boards ***************");
        System.out.println();
        System.out.println("Escolha a opção desejada");

        var optionMenu = 0;
        while (true) {
            System.out.println("1- Criar um novo Board");
            System.out.println("2- Selecionar um novo Board existente");
            System.out.println("3- Excluir um Board");
            System.out.println("4- Atualizar um Board");
            System.out.println("5- Sair");
            optionMenu = scanner.nextInt();

            switch (optionMenu) {
                case 1 -> createBoard();
                case 2 -> selectBoard();
                case 3 -> deleteBoard();
                case 4 -> editBoard();
                case 5 -> {
                    System.out.println();
                    System.out.println("Obrigado por utilizar este Gerenciador.");
                    System.out.println("Volte logo!");
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

    private void createBoard() throws SQLException {
        var entity = new BoardEntity();
        System.out.println("Informe o nome do seu board");
        entity.setName(scanner.next());

        System.out.println("Seu board terá colunas além das 3 padrões? Se sim informe quantas, se não informe '0'");
        var additionalColumns = scanner.nextInt();

        List<BoardColumnEntity> columns = new ArrayList<>();

        System.out.println("Informe o nome da coluna inicial do board");
        var initialColumnName = scanner.next();
        var initialColumn = createBoardColumn(initialColumnName, BoardColumnKindEnum.INITIAL, 0);
        columns.add(initialColumn);

        for(int i = 0; i < additionalColumns; i++) {
            System.out.println("Informe o nome da coluna de tarefa pendente do board");
            var pendingColumnName = scanner.next();
            var pendingColumn = createBoardColumn(pendingColumnName, BoardColumnKindEnum.PENDING, i + 1);
            columns.add(pendingColumn);
        }

        System.out.println("Informe o nome da coluna final do board");
        var finalColumnName = scanner.next();
        var finalColumn = createBoardColumn(finalColumnName, BoardColumnKindEnum.FINAL, additionalColumns + 1);
        columns.add(finalColumn);

        System.out.println("Informe o nome da coluna de cancelamento do board");
        var cancelColumnName = scanner.next();
        var cancelColumn = createBoardColumn(cancelColumnName, BoardColumnKindEnum.CANCEL, additionalColumns + 2);
        columns.add(cancelColumn);

        entity.setBoardColumns(columns);
        try(var connection = getConnection()) {
            var service = new BoardServices(connection);
            service.insert(entity);
        }
    }

    private void selectBoard() throws SQLException {
        System.out.println("Digite um 'id' do board que deseja selecionar: ");
        var id = scanner.nextLong();
        try(var connection = getConnection()) {
            var queryService = new BoardQueryService(connection);
            var optional = queryService.findById(id);
                optional.ifPresentOrElse(p -> new BoardMenu(optional.get()).execute(),
                        () -> System.out.printf("Board com o id: [%s], não foi encontrado!\n\n", id));
        }
    }

    private void deleteBoard() throws SQLException {
        System.out.println("Digite um id do board que deseja excluir");
        var id = scanner.nextInt();
        try(var connection = getConnection()) {
            var service = new BoardServices(connection);
            if(service.delete(id)) {
                System.out.printf("Board [%s] foi excluido com sucesso!\n\n", id);
            } else {
                System.out.printf("Board com o id: [%s], não foi encontrado!\n\n", id);
            }
        }
    }

    private BoardColumnEntity createBoardColumn(final String name, final BoardColumnKindEnum kind, final int order){
        var boardColumn = new BoardColumnEntity();
        boardColumn.setName(name);
        boardColumn.setKind(kind);
        boardColumn.setOrder(order);
        return boardColumn;
    }

    private void editBoard() {}

}