package br.com.thnocard.board.persistence.config.converter;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

import static java.time.ZoneOffset.UTC;
import static java.util.Objects.nonNull;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OffSetDateTimeConverter {

    public static OffsetDateTime toOffsetDateTime(final Timestamp value) {
        return nonNull(value) ? OffsetDateTime.ofInstant(value.toInstant(), UTC) : null;
    }

}