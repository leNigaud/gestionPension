package Data;
import java.time.LocalDate; // import the LocalDateTime class

/**
 * Class of the table payer
 */
public class Payer {
    private String IM;
    private String num_tarif;
    private LocalDate date;

    //Constructor
    public Payer(String iM, String num_tarif, LocalDate date) {
        IM = iM;
        this.num_tarif = num_tarif;
        this.date = date;
    }

    //Getters
    public String getIM() {
        return IM;
    }
    public String getNum_tarif() {
        return num_tarif;
    }
    public LocalDate getDate() {
        return date;
    }

}
