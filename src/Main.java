import java.awt.Frame;
import java.time.LocalDate;

// import javax.swing.text.View;

import Controller.Controller;
import Model.*;
import Data.*;
import View.*;

public class Main {

    public static void main(String[] args) {

        View myView = new View();
        Model myModel = new Model();
        Controller myController = new Controller(myModel, myView);
        // for (String a : myModel.getDiplome())
        //      System.out.println(a);
         
            // secWin_newPay pay2 = new secWin_newPay(new Frame(),"none2","none2");
            // modifpay pay = new modifpay(new Frame(),"none","none");
            //  pay.setVisible(true);
            //  pay2.setVisible(true);


    }



}
