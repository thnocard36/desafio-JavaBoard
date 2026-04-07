package br.com.thnocard.board.persistence.config.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import static br.com.thnocard.board.persistence.config.entity.BoardColumnKindEnum.INITIAL;

@Data
public class BoardEntity {

    private Long id;
    private String name;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<BoardColumnEntity> boardColumns = new ArrayList<>();

    public BoardColumnEntity getBoardColumn() {
        return boardColumns.stream()
                .filter(bc -> bc.getKind().equals(INITIAL))
                .findFirst().orElseThrow();
    }

}