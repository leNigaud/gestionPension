package Model;

import java.util.List;
import Data.*;
import Interface.TarifInterface;

public class TarifDAO implements TarifInterface {
    private Model model;

    public TarifDAO(Model model) {
        this.model = model;
    }

    @Override
    public void addTarif(Tarif tarif) {
        model.addTarif(tarif);
    }

    @Override
    public List<Tarif> getAllTarifs() {
        return model.getAllTarifs();
    }

    @Override
    public void updateTarif(Tarif tarif) {
        model.updateTarif(tarif);
    }

    @Override
    public void deleteTarif(String numTarif) {
        model.deleteTarif(numTarif);
    }

    @Override
    public Tarif getTarif(String numTarif) {
        return model.getTarif(numTarif);
    }

    @Override
    public Tarif getTarifDiplome(String diplome) {
        return model.getTarifDiplome(diplome);
    }
}
