package Interface;

import java.time.LocalDate;
import java.util.List;

import Data.Payer;

public interface PayerInterface {

    //Add/Create a Payment in DB 
    void addPayer(Payer payment);

    //Display/Read all Payer 
    List<Payer> getAllPayers();

    //Modify/Update Payer 
    void updatePayer(Payer payer);

    //Delete a Payment 
    void deletePayer(String IM);

    //Overloaded deletePayer for all 
    void deletePayer(String IM, String num_tarif, LocalDate date);

    //another deletePayer for num_tarif 
    void deletePayerTarif(String num_tarif);

    //Get a Payment in the DB based on IM, numtarif, date
    Payer getPayer(String IM, String num_tarif, LocalDate date);

    //List of paid pensions between two dates 
    List<Payer> listAllPayersBetweenDates(LocalDate date1, LocalDate date2);

}