package it.polito.tdp.formulaone;

import java.net.URL;
import java.time.Year;
import java.util.ResourceBundle;

import it.polito.tdp.formulaone.model.Driver;
import it.polito.tdp.formulaone.model.Model;
import it.polito.tdp.formulaone.model.Season;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FormulaOneController {
	
	Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Season> boxAnno;

    @FXML
    private TextField textInputK;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    		Year year = boxAnno.getValue().getYear();
    		model.creaGrafo(year);
    		txtResult.appendText(model.getVincente().getForename()+ " "+model.getVincente().getSurname());

    }

    @FXML
    void doTrovaDreamTeam(ActionEvent event) {
    		txtResult.clear();
    		Year year = boxAnno.getValue().getYear();
    		String result = "";
    		int dimensione = Integer.parseInt(textInputK.getText());
    		for(Driver d: model.calcolaDreamTeam(dimensione)) {
    			result += d.toString();
    		}
    		result = result.trim();
    		txtResult.appendText(result);

    }

    @FXML
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert textInputK != null : "fx:id=\"textInputK\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'FormulaOne.fxml'.";

    }
    
    public void setModel(Model model){
    	this.model = model;
    	boxAnno.getItems().addAll(model.getSeasons());
    }
}
