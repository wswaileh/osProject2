package osproject2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainScreenController implements Initializable {

    @FXML
    private ComboBox algoCB;

    @FXML
    private ComboBox tracksCB;

    @FXML
    private ComboBox dirCB;

    @FXML
    private Label dirLB;

    @FXML
    private Button aboutUs;

    @FXML
    private Button exitBT;

    @FXML
    private Button genBT;

    @FXML
    private TextField queueTF;

    @FXML
    private TextField headTF;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setAlgoCbData();
        setTracksCbData();
        setDirCbData();
        dirVisiblity(false);

    }

    private void setDirCbData() {
        dirCB.getItems().addAll("UP", "DOWN");
        dirCB.getSelectionModel().selectFirst();

    }

    private void setAlgoCbData() {
        algoCB.getItems().addAll("LCFS", "SCAN");
        algoCB.getSelectionModel().selectFirst();

    }

    private void setTracksCbData() {
        tracksCB.getItems().addAll(300, 500);
        tracksCB.getSelectionModel().selectFirst();

    }

    private void dirVisiblity(boolean choice) {
        dirLB.setVisible(choice);
        dirCB.setVisible(choice);
    }
    
    public void exitBtOnAction(ActionEvent e){
                System.exit(0);
    }

    public void genBtOnAction(ActionEvent e){
       String result = "";
       for (int i=0;i<300;i++){
        double randomDouble = Math.random();
	randomDouble = randomDouble * Integer.parseInt(tracksCB.getValue().toString()) + 1;
	int randomInt = (int) randomDouble;
        if (i!=299)
            result += randomInt+",";
        else
            result += randomInt;
       }
       queueTF.setText(result);
    }
    
    public void algoCbOnAction(ActionEvent e) {
        if (algoCB.getValue().toString().equals("SCAN")) {
            dirVisiblity(true);
        } else {
            dirVisiblity(false);
        }
    }

    public void aboutUsOnAction(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("aboutUsScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Disk Scheduling Simulator");
        stage.setScene(scene);
        stage.show();
    }

    public void runOnAction(ActionEvent e) {
        try {
            String algo = algoCB.getValue().toString();
            String numOfTracks = tracksCB.getValue().toString();
            String headLoc = headTF.getText();
            String diskQueue = queueTF.getText();   
            String headDir;
            if (algo.equals("SCAN"))
                headDir = dirCB.getValue().toString();
            else
                headDir = "NONE";
            FXMLLoader loader = new FXMLLoader(getClass().getResource("resultsScreen.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            ResultsScreenController controller = loader.getController();
            Model model = new Model(algo, numOfTracks, headLoc, diskQueue, headDir);
            controller.setModel(model);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Disk Scheduling Simulator");
            stage.setScene(scene);
            stage.show();
        } catch (NullPointerException ee) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Please Fill All Fields!");
            alert.show();
        }
        catch (NumberFormatException ee) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Please Fill All Fields!");
            alert.show();
        }
        catch (Exception eee){
            System.err.println(eee.toString());
        }
    }

}
