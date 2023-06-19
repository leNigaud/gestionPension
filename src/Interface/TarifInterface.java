package Interface;

import java.util.List;
import Data.Tarif;

public interface TarifInterface {

    //Add/Create a Tarif in DB 
    void addTarif(Tarif tarif);

    //Display/Read all Tarifs 
    List<Tarif> getAllTarifs();

    //Modify/Update Tarif 
    void updateTarif(Tarif tarif);

    //Delete a Tarif 
    void deleteTarif(String numTarif);

    //Get a Tarif from DB by its num_tarif 
    Tarif getTarif(String numTarif);

    //Get a Tarif from DB by its diplome 
    Tarif getTarifDiplome(String diplome);

}