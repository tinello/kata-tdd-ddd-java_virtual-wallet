package ar.com.tinello.katas.virtualwallet.checkingAccount.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class OpeningDate {

    private final LocalDateTime localDateTime;

    public OpeningDate() {
        localDateTime = LocalDateTime.now();
    }
    
    public OpeningDate(final Timestamp timestamp) {
        localDateTime = timestamp.toLocalDateTime();
    }

    public final String getDatePrintFormat() {
        return localDateTime.format(datePrintFormat());
    }

    public static DateTimeFormatter datePrintFormat() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    public final Long getTimestamp() {
        return Timestamp.valueOf(localDateTime).getTime();
    }
    
}
