package Data;
import java.time.LocalDate; // import the LocalDateTime class

/**
 * Class of the table payer
 */
public class Payer {
    private String IM;
    private int num_tarif;
    private LocalDate date;

    public String getIM() {
        return IM;
    }
    public int getNum_tarif() {
        return num_tarif;
    }
    public LocalDate getDate() {
        return date;
    }

}
