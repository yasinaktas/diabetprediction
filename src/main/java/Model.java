import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Model implements ModelInterface{

    List<Observer> observers = new ArrayList<>();
    List<Classifier> classifiers = new ArrayList<>();
    Instances fullData=null,trainingData=null,testData=null;
    Random random;


    @Override
    public void initialize() {
        try{
            CSVLoader csvLoader = new CSVLoader();
            csvLoader.setSource(new File("diabetes_prediction_dataset.csv"));
            fullData = csvLoader.getDataSet();



            updateFullDataSize(fullData.numInstances());
            random = new Random();
        }catch (Exception e){
            System.err.println("Dosya Açılamadı!");
            System.exit(0);
        }
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void createTestDataSet(long size) {
        testData = createDataSet(size);
        updateTestDataSize(testData.numInstances());
    }

    @Override
    public void createTrainingDataSet(long size) {
        trainingData = createDataSet(size);
        updateTrainingDataSize(trainingData.numInstances());
    }

    @Override
    public void trainModel(ClassifierType type) {
        try{
            Classifier classifier = new ConcreteClassifier(type,trainingData,testData);
            classifier.classifierName = String.valueOf(type);
            classifiers.add(classifier);
            updateClassifiers();
        }catch (RuntimeException e){
            updateClassifiers();
            updatePopup(e.getMessage());
        }

    }

    @Override
    public void createGraph(int[] rows, AxisNames xAxis, AxisNames yAxis) {
        ArrayList<Point> points = new ArrayList<>();
        for(int row : rows){
            double x = 0;
            double y = 0;
            String name = String.valueOf((row + 1));
            if(xAxis == AxisNames.Data) x = classifiers.get(row).trainingDataSize;
            if(xAxis == AxisNames.Time) x = classifiers.get(row).trainingTimeMillis;
            if(xAxis == AxisNames.Success) x = classifiers.get(row).successPercent;
            if(yAxis == AxisNames.Data) y = classifiers.get(row).trainingDataSize;
            if(yAxis == AxisNames.Time) y = classifiers.get(row).trainingTimeMillis;
            if(yAxis == AxisNames.Success) y = classifiers.get(row).successPercent;
            points.add(new Point(name,x,y,classifiers.get(row).type));
        }
        updateGraph(points,String.valueOf(xAxis),String.valueOf(yAxis),xAxis + " vs " + yAxis);
    }

    @Override
    public void createInformation(int selectedRow) {
        StringBuilder information = new StringBuilder();
        if(selectedRow >= 0){
            if (classifiers.get(selectedRow).type == ClassifierType.J48) information.append(classifiers.get(selectedRow).j48Classifier.toString());
            if (classifiers.get(selectedRow).type == ClassifierType.NaiveBayes) information.append(classifiers.get(selectedRow).naiveBayesClassifier.toString());
            //if (classifiers.get(selectedRow).type == ClassifierType.RandomForest) information += classifiers.get(selectedRow).randomForestClassifier.toString();
            if(classifiers.get(selectedRow).type == ClassifierType.RandomForest){
                String[] options = classifiers.get(selectedRow).randomForestClassifier.getOptions();
                information.append("Random Forest Model Options:\n");
                information.append("----------------------------\n");
                for (String option : options) {
                    information.append(option).append("\n");
                }
            }
        }
        updateInformationArea(information.toString());
    }

    @Override
    public void testExample(int selectedRow, ExampleData exampleData) {
        int classIndex = classifiers.get(selectedRow).trainingData.numAttributes() - 1;
        fullData.setClassIndex(classIndex);
        Instance newInstance = new DenseInstance(classifiers.get(selectedRow).trainingData.numAttributes());
        newInstance.setDataset(classifiers.get(selectedRow).trainingData);
        newInstance.setValue(0, exampleData.getGender());
        newInstance.setValue(1, exampleData.getAge());
        newInstance.setValue(2, exampleData.getHypertension());
        newInstance.setValue(3, exampleData.getHeartDisease());
        newInstance.setValue(4, exampleData.getSmokingHistory());
        newInstance.setValue(5, exampleData.getBmi());
        newInstance.setValue(6, exampleData.getHba1cLevel());
        newInstance.setValue(7, exampleData.getBloodGlucoseLevel());
        double result = classifiers.get(selectedRow).testData(newInstance);
        if(result < 0) updateTestResult("Error!");
        else{
            updateTestResult(classifiers.get(selectedRow).classifierName + "\nPredicted Class : " + result);
        }
    }

    public Instances createDataSet(long size){
        fullData.randomize(random);
        int dataSize = (int)(size);
        return new Instances(fullData,0,dataSize);
    }

    public void updateFullDataSize(long size){
        for(Observer o : observers){
            o.updateFullDataSize(size);
        }
    }

    public void updateTestDataSize(long size){
        for(Observer o : observers){
            o.updateTestDataSize(size);
        }
    }

    public void updateTrainingDataSize(long size){
        for(Observer o : observers){
            o.updateTrainingDataSize(size);
        }
    }

    public void updateClassifiers(){
        for(Observer o : observers){
            o.updateList(classifiers);
        }
    }

    public void updatePopup(String message){
        for(Observer o : observers){
            o.showPopup(message);
        }
    }

    public void updateGraph(ArrayList<Point> points,String xAxis,String yAxis, String graphName){
        for(Observer o : observers){
            o.updateGraph(points,xAxis,yAxis,graphName);
        }
    }

    public void updateInformationArea(String information){
        for(Observer o : observers){
            o.updateInformationArea(information);
        }
    }

    public void updateTestResult(String result){
        for(Observer o : observers){
            o.updateTestResult(result);
        }
    }

    public String getDataInfo(){
        StringBuilder result = new StringBuilder();
            int numAttributes = fullData.numAttributes();
            result.append("Total attribute count: ").append(numAttributes);

            for (int i = 0; i < numAttributes; i++) {
                Attribute attribute = fullData.attribute(i);
                result.append("Attribute ").append(i + 1).append(": ").append(attribute.toString());
            }
            return String.valueOf(result);
    }

}
