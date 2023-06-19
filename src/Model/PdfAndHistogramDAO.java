package Model;

import Data.Payer;
import Interface.PdfAndHistogramInterface;


public class PdfAndHistogramDAO implements PdfAndHistogramInterface {
    private Model model;
    
    public PdfAndHistogramDAO(Model model) {
        this.model = model;
    }

    @Override
    public void createPDF(Payer payment, String path) {
        model.createPDF(payment, path);
    }

    @Override
    public void showHistogram() {
        model.showHistogram();
    }

    @Override
    public void createHistogram(int[] data) {
        model.createHistogram(data);
    }
}

