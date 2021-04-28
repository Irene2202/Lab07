/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutages;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private List<Nerc> nerc;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="cmbNerc"
    private ComboBox<Nerc> cmbNerc; // Value injected by FXMLLoader

    @FXML // fx:id="txtYears"
    private TextField txtYears; // Value injected by FXMLLoader

    @FXML // fx:id="txtHours"
    private TextField txtHours; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    private Model model;
    
    @FXML
    void doRun(ActionEvent event) {
    	txtResult.clear();
    	Nerc n=cmbNerc.getValue();
    	
    	int anniMax=this.controllaAnno();
    	int oreMax=this.controllaOra();
    	
    	if(n==null) {
    		txtResult.setText("Selezionare un NERC.");
    		return;
    	}
    	else if(anniMax!=-1 && oreMax!=-1) {
    		List<PowerOutages> result=this.model.getWorstCase(n, anniMax, oreMax);
    		
    		txtResult.appendText("Tot persone coinvolte: "+this.model.calcolaNumeroPersone(result)+"\n");
    		txtResult.appendText("Tot ore di disservizio: "+this.model.calcolaDurataOre(result)+"\n");
    		StringBuilder sb=new StringBuilder();
    		for(PowerOutages p:result) {
    			sb.append(String.format("%-5d", p.getYear()));
    			sb.append(String.format("%-17s", p.getDataInizio()));
    			sb.append(String.format("%-17s", p.getDataFine()));
    			sb.append(String.format("%-4d", p.getDuration()));
    			sb.append(String.format("%-8d\n", p.getCustomers_affected()));   			
    		}
    		
    		txtResult.appendText(sb.toString());
       	}
    	
    }

   

	@FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert cmbNerc != null : "fx:id=\"cmbNerc\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtYears != null : "fx:id=\"txtYears\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtHours != null : "fx:id=\"txtHours\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        
        // Utilizzare questo font per incolonnare correttamente i dati;
        txtResult.setStyle("-fx-font-family: monospace");
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	nerc=this.model.getNercList();
    	cmbNerc.getItems().addAll(nerc);
    }
    
    private int controllaOra() {
		String oreString=txtHours.getText();
		int ore=-1;
		
		if(oreString.length()==0)
			txtResult.appendText("Max Hours non inserito.\n");
		else {
			try {
				ore=Integer.parseInt(oreString);
			} catch (NumberFormatException e) {
				txtResult.appendText("Il formato inserito in 'Max Hours' non è valido\n");
			}
		}
		
		return ore;
	}

	private int controllaAnno() {
		String anniString=txtYears.getText();
		int anni=-1;
		
		if(anniString.length()==0)
			txtResult.appendText("Max Years non inserito.\n");
		else {
			try {
				anni=Integer.parseInt(anniString);
			} catch (NumberFormatException e) {
				txtResult.appendText("Il formato inserito in 'Max Years' non è valido\n");
			}
		}
	
		return anni;
	}
}
