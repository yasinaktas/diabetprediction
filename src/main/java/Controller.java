import javax.swing.*;
import java.util.logging.Handler;

public class Controller implements ControllerInterface{


    private final ModelInterface model;
    private final UIView view;

    public Controller(ModelInterface model) {
        this.model = model;
        view = new UIView(this,model);
        view.create();
        view.hideTrainProgressBar();

        model.initialize();

    }

    @Override
    public void createTestData(long size) {
        model.createTestDataSet(size);
    }

    @Override
    public void createTrainingData(long size) {
        model.createTrainingDataSet(size);
    }

    @Override
    public void trainModel(ClassifierType type) {
        view.disableTrainButton();
        view.showTrainProgressBar();
        new Thread(new Runnable() {
            @Override
            public void run() {
                model.trainModel(type);
            }
        }).start();

    }

    @Override
    public void drawGraph(int[] rows, AxisNames xAxis, AxisNames yAxis) {
        if(rows.length == 0){
            view.showPopup("No selected algorithm to draw a graph");
            return;
        }
        for(int row : rows) System.out.print(row + " - ");
        model.createGraph(rows,xAxis,yAxis);
    }

    @Override
    public void selectedClassifier(int selectedRow) {
        model.createInformation(selectedRow);
    }

    @Override
    public void testExample(int row, ExampleData exampleData) {
        model.testExample(row,exampleData);
    }

}
