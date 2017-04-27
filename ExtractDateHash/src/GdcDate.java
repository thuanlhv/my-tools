import java.time.LocalDate;

class GdcDate {
    private LocalDate date;
    private int hash;

    GdcDate(LocalDate date, int hash) {
        this.date = date;
        this.hash = hash;
    }

    LocalDate getDate() {
        return date;
    }

    int getHash() {
        return hash;
    }
}
