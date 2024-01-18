import java.util.ArrayList;
import java.util.List;

public interface Observer {
    void showPopup(String message);
    void updateFullDataSize(long size);
    void updateTrainingDataSize(long size);
    void updateTestDataSize(long size);
    void updateList(List<Classifier> classifiers);
    void updateGraph(ArrayList<Point> points,String xAxis,String yAxis, String graphName);
    void updateInformationArea(String information);
    void updateTestResult(String result);
}
