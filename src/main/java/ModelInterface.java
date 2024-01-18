public interface ModelInterface {
    void initialize();
    void addObserver(Observer o);
    void removeObserver(Observer o);
    void createTestDataSet(long size);
    void createTrainingDataSet(long size);
    void trainModel(ClassifierType type);
    void createGraph(int[] rows, AxisNames xAxis, AxisNames yAxis);
    void createInformation(int selectedRow);
    void testExample(int selectedRow, ExampleData exampleData);
}
