public interface ControllerInterface {
    void createTestData(long size);
    void createTrainingData(long size);
    void trainModel(ClassifierType type);
    void drawGraph(int[] rows, AxisNames xAxis, AxisNames yAxis);
    void selectedClassifier(int selectedRow);
    void testExample(int row, ExampleData exampleData);
}
