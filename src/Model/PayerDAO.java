package Model;
import Data.*;
import Interface.PayerInterface;

import java.time.LocalDate;
import java.util.List;


public class PayerDAO implements PayerInterface {
    private Model model;

    public PayerDAO(Model model) {
        this.model = model;
    }

    @Override
    public void addPayer(Payer payment) {
        model.addPayer(payment);
    }

    @Override
    public List<Payer> getAllPayers() {
        return model.getAllPayers();
    }

    @Override
    public void updatePayer(Payer payer) {
        model.updatePayer(payer);
    }

    @Override
    public void deletePayer(String IM) {
        model.deletePayer(IM);
    }

    @Override
    public void deletePayer(String IM, String num_tarif, LocalDate date) {
        model.deletePayer(IM, num_tarif, date);
    }

    @Override
    public void deletePayerTarif(String num_tarif) {
        model.deletePayerTarif(num_tarif);
    }

    @Override
    public Payer getPayer(String IM, String num_tarif, LocalDate date) {
        return model.getPayer(IM, num_tarif, date);
    }

    @Override
    public List<Payer> listAllPayersBetweenDates(LocalDate date1, LocalDate date2) {
        return model.listAllPayersBetweenDates(date1, date2);
    }
}
