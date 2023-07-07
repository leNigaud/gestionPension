package Interface;

import Data.Payer;

public interface PdfAndHistogramInterface {

    //Create the pdf reçu, specify the path where the pdf will appear like(.../MyFolder/)
    void createPDF(Payer payment, String path);

    //show the histogram of all ages of person
    void showHistogram();

    //helper for showHistogram()
    void createHistogram(int[] data);

}