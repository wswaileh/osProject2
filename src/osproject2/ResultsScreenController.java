package osproject2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ResultsScreenController implements Initializable {

    @FXML
    private Button backBT;

    @FXML
    private LineChart<Number, Number> linechart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private Label avgLB;

    @FXML
    private Label totalLB;

    @FXML
    private Label seqLB;

    private Model model;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
        xAxis.setLabel("Order Of Execution");
        yAxis.setLabel("Location");

    }

    public void setModel(Model model) {
        this.model = model;
        if (model.getAlgo().equals("SCAN")) {
            scan();
            linechart.setTitle("SCAN "+model.getHeadDir()+" Algorithm With "+ model.getNumOfTracks() +" Tracks");
        }
        else{
            lcfs();
            linechart.setTitle("LCFS Algorithm With " + model.getNumOfTracks() + " Tracks");
        }
    }

    public void backBTOnAction() {
        // get a handle to the stage
        Stage stage = (Stage) backBT.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    private void scan() {
        int sum = 0;
        String[] requests = model.getDiskQueue().split(",");
        int[] d = new int[2 * requests.length];
        for (int i = 0; i < requests.length; i++) {
            d[i] = Integer.parseInt(requests[i]);
        }
        int temp, max;
        int dloc = 0;   //loc of disk in array
        int n = requests.length;
        int disk = Integer.parseInt(model.getHeadLoc());
        d[n] = disk;
        n = n + 1;
        for (int i = 0; i < n; i++) // sorting disk locations
        {
            for (int j = i; j < n; j++) {
                if (d[i] > d[j]) {
                    temp = d[i];
                    d[i] = d[j];
                    d[j] = temp;
                }
            }
        }
        max = d[n];
        for (int i = 0; i < n; i++) // to find loc of disc in array
        {
            if (disk == d[i]) {
                dloc = i;
                break;
            }
        }
        int j = 0;
        XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
        series1.setName("Head Locations");
        int totalSeekTime = Integer.parseInt(model.getHeadLoc()) - d[dloc];
        
        //DOWN SCAN
        if (model.getHeadDir() == "DOWN") {
            series1.getData().add(new XYChart.Data<>(j++, Integer.parseInt(model.getHeadLoc())));
            for (int i = dloc; i >= 0; i--) {
                System.out.printf("%d -->", d[i]);
                seqLB.setText(seqLB.getText() + d[i] + "->");
                if (i != 0) {
                    totalSeekTime += (Math.abs(d[i] - d[i - 1]));
                }
                series1.getData().add(new XYChart.Data<>(j++, d[i]));
            }

            totalSeekTime += (Math.abs(d[0]));
            totalSeekTime += Math.abs(d[3]);
            System.out.printf("0 -->");
            seqLB.setText(seqLB.getText() + "0->");

            series1.getData().add(new XYChart.Data<>(j++, 0));

            for (int i = dloc + 1; i < n; i++) {
                series1.getData().add(new XYChart.Data<>(j++, d[i]));
                if (i != n - 1) {
                    totalSeekTime += (Math.abs(d[i] - d[i + 1]));
                }
                if (i==n-1)
                    seqLB.setText(seqLB.getText() + d[i]);
                else
                    seqLB.setText(seqLB.getText() + d[i] + "->");
                
               System.out.printf("%d-->", d[i]);
                

            }
        }

        //UP SCAN
        if (model.getHeadDir() == "UP") {
            for (int i = dloc; i < n; i++) {
                series1.getData().add(new XYChart.Data<>(j++, d[i]));
                if (i != n - 1) {
                    totalSeekTime += (Math.abs(d[i] - d[i + 1]));
                }
                System.out.printf("%d-->", d[i]);
                seqLB.setText(seqLB.getText() + d[i] + "->");
            }
            totalSeekTime += Math.abs(Integer.parseInt(model.getNumOfTracks()) - d[n - 1]);
            totalSeekTime += Math.abs(Integer.parseInt(model.getNumOfTracks()) - d[dloc - 1]);
            System.out.printf(model.getNumOfTracks() + " -->");
            series1.getData().add(new XYChart.Data<>(j++, Integer.parseInt(model.getNumOfTracks())));
            seqLB.setText(seqLB.getText() + model.getNumOfTracks() + "->");
            

            for (int i = dloc - 1; i >= 0; i--) {
                System.out.printf("%d -->", d[i]);
                
                if (i==0)
                    seqLB.setText(seqLB.getText() + d[i]);
                else
                    seqLB.setText(seqLB.getText() + d[i] + "->");
                
                if (i != 0) {
                    totalSeekTime += (Math.abs(d[i] - d[i - 1]));
                }
                series1.getData().add(new XYChart.Data<>(j++, d[i]));
            }

        }

        double avgSeekTime = (double) totalSeekTime / (n-1);
        avgLB.setText(avgLB.getText() + String.format("%.02f", avgSeekTime));
        totalLB.setText(totalLB.getText() + totalSeekTime);
        linechart.getData().add(series1);
    }

    private void lcfs() {
        String[] requests = model.getDiskQueue().split(",");
        int[] d = new int[requests.length];
        for (int i = 0; i < requests.length; i++) {
            d[i] = Integer.parseInt(requests[i]);
        }XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
        series1.setName("Head Locations");
        int j=0;
        int totalSeekTime=0;
        System.out.printf("%d-->",Integer.parseInt(model.getHeadLoc()));
        series1.getData().add(new XYChart.Data<>(j++, Integer.parseInt(model.getHeadLoc())));
        seqLB.setText(seqLB.getText() + model.getHeadLoc() + "->");
        totalSeekTime += Math.abs(d[requests.length-1]-Integer.parseInt(model.getHeadLoc()));
        for (int i=requests.length-1;i>=0;i--){
            series1.getData().add(new XYChart.Data<>(j++, d[i]));
            System.out.printf("%d-->",d[i]);
            if (i!=0)
                seqLB.setText(seqLB.getText() + d[i] + "->");
            else
                seqLB.setText(seqLB.getText() + d[i]);
            if (i!=0)
                totalSeekTime += Math.abs( d[i]-d[i-1]);
        }
        
        System.out.println("Total : "+totalSeekTime);
        double avgSeekTime = (double) totalSeekTime / requests.length;
        avgLB.setText(avgLB.getText() + String.format("%.02f", avgSeekTime));
        totalLB.setText(totalLB.getText() + totalSeekTime);
        linechart.getData().add(series1);
    }
}
