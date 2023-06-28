import java.time.LocalDate;

import Controller.*;
import Model.*;
import View.View;
import Data.*;

public class Main {

public static void main(String[] args) {

       // Instanciation du modèle
        Model model = new Model();
        
        // Instanciation de la vue
      View view = new View();
        
        // Instanciation du contrôleur et liaison avec le modèle et la vue
        Controller controller = new Controller(view, model);
    }

}
