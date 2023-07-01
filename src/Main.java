import java.time.LocalDate;

import Controller.Controller;
import Model.*;
import Data.*;
import View.*;

public class Main {

public static void main(String[] args) {

        View myView = new View();
        Model myModel = new Model();
        Controller myController = new Controller(myModel, myView);


    }



}
