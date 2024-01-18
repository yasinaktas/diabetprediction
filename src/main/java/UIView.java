import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.TableHeaderUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class UIView implements ActionListener,Observer{

    private ControllerInterface controller;
    private ModelInterface model;

    private JLabel labelDataInfo;
    private JLabel labelTrainingDataInfo;
    private JLabel labelTestDataInfo;
    private SpinnerModel spinnerTestSizeModel;
    private JSpinner spinnerTestDataSize;
    private JButton buttonCreateTestData;
    private SpinnerModel spinnerTrainingSizeModel;
    private JSpinner spinnerTrainingDataSize;
    private JButton buttonCreateTrainingData;
    private JComboBox<ClassifierType> comboBoxChooseModel;
    private JProgressBar progressbar;
    private JButton buttonTrainModel;
    private JTable tableClassifiers;
    private DefaultTableModel modelTableClassifiers;
    private XYSeries seriesJ48;
    private XYSeries seriesRandomForest;
    private XYSeries seriesNaiveBayes;
    private XYPlot plot;
    private JFreeChart chart;
    private XYSeriesCollection dataset;
    private JComboBox<AxisNames> comboBoxXAxis;
    private JComboBox<AxisNames> comboBoxYAxis;
    private JButton buttonDrawGraph;
    private JTextPane textPane;
    private JComboBox<String> comboBoxGenders;
    private JTextField textFieldAge;
    private JComboBox<Double> comboBoxHeartDisease;
    private JComboBox<Double> comboBoxHyperTension;
    private JComboBox<String> comboBoxSmokingHistory;
    private JTextField textFieldBmi;
    private JTextField textFieldHbA1cLevel;
    private JTextField textFieldBloodGlucoseLevel;
    private JComboBox<Double> comboBoxDiabetes;
    private JButton buttonTestExample;

    private long fullDataSize = 0;
    private long testDataSize = 0;
    private long trainingDataSize = 0;

    public UIView(ControllerInterface controller, ModelInterface model) {
        this.controller = controller;
        this.model = model;
        model.addObserver((Observer) this);
    }

    public void create(){
        int height = 900;
        GridBagConstraints c = new GridBagConstraints();

        JFrame frame = new JFrame("Diabetic Prediction");

        JPanel panel_main = new JPanel();

        JPanel column1 = new JPanel();
        column1.setPreferredSize(new Dimension(300,height));
        column1.setLayout(new GridBagLayout());

        JPanel panelDataInfo = new JPanel();
        panelDataInfo.setLayout(new GridBagLayout());
        labelDataInfo = new JLabel("Full Data Set Size : ");
        c.gridwidth = 1; c.gridx = 0; c.gridy = 0; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,10,5,5);
        panelDataInfo.add(labelDataInfo,c);
        labelTrainingDataInfo = new JLabel("Training Data Set Size : ");
        c.gridwidth = 1; c.gridx = 0; c.gridy = 1; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,10,5,5);
        panelDataInfo.add(labelTrainingDataInfo,c);
        labelTestDataInfo = new JLabel("Test Data Set Size : ");
        c.gridwidth = 1; c.gridx = 0; c.gridy = 2; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,10,5,5);
        panelDataInfo.add(labelTestDataInfo,c);
        panelDataInfo.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY,1)," Data Set Information "));
        c.gridwidth = 1; c.gridx = 0; c.gridy = 0; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(10,5,0,0);
        column1.add(panelDataInfo,c);

        JPanel panelCreateTestData = new JPanel();
        panelCreateTestData.setLayout(new GridBagLayout());
        JLabel labelTestData = new JLabel("Number of Test Data");
        c.gridwidth = 1; c.gridx = 0; c.gridy = 0; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,10,5,5);
        panelCreateTestData.add(labelTestData,c);
        spinnerTestSizeModel = new SpinnerNumberModel(0, 0, 0, 1);
        spinnerTestDataSize = new JSpinner();
        spinnerTestDataSize.setModel(spinnerTestSizeModel);
        c.gridwidth = 1; c.gridx = 0; c.gridy = 1; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,5,5,5);
        panelCreateTestData.add(spinnerTestDataSize,c);
        buttonCreateTestData = new JButton("Create Test Data");
        buttonCreateTestData.addActionListener(this);
        buttonCreateTestData.setPreferredSize(new Dimension(buttonCreateTestData.getPreferredSize().width,50));
        buttonCreateTestData.setMinimumSize(new Dimension(buttonCreateTestData.getPreferredSize().width,50));
        c.gridwidth = 1; c.gridx = 0; c.gridy = 2; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,5,5,5);
        panelCreateTestData.add(buttonCreateTestData,c);
        panelCreateTestData.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY,1)," Create Test Data "));
        c.gridwidth = 1; c.gridx = 0; c.gridy = 1; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(10,5,0,0);
        column1.add(panelCreateTestData,c);

        JPanel panelCreateTrainingData = new JPanel();
        panelCreateTrainingData.setLayout(new GridBagLayout());
        JLabel labelTrainingData = new JLabel("Number of Training Data");
        c.gridwidth = 1; c.gridx = 0; c.gridy = 0; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,10,5,5);
        panelCreateTrainingData.add(labelTrainingData,c);
        spinnerTrainingSizeModel = new SpinnerNumberModel(0, 0, 0, 1);
        spinnerTrainingDataSize = new JSpinner();
        spinnerTrainingDataSize.setModel(spinnerTrainingSizeModel);
        c.gridwidth = 1; c.gridx = 0; c.gridy = 1; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,5,5,5);
        panelCreateTrainingData.add(spinnerTrainingDataSize,c);
        buttonCreateTrainingData = new JButton("Create Training Data");
        buttonCreateTrainingData.addActionListener(this);
        buttonCreateTrainingData.setPreferredSize(new Dimension(buttonCreateTrainingData.getPreferredSize().width,50));
        buttonCreateTrainingData.setMinimumSize(new Dimension(buttonCreateTrainingData.getPreferredSize().width,50));
        c.gridwidth = 1; c.gridx = 0; c.gridy = 2; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,5,5,5);
        panelCreateTrainingData.add(buttonCreateTrainingData,c);
        panelCreateTrainingData.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY,1)," Create Training Data "));
        c.gridwidth = 1; c.gridx = 0; c.gridy = 2; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(10,5,0,0);
        column1.add(panelCreateTrainingData,c);

        JPanel panelTrainModel = new JPanel();
        panelTrainModel.setLayout(new GridBagLayout());
        JLabel labelChooseModel = new JLabel("Select Model to Train");
        c.gridwidth = 1; c.gridx = 0; c.gridy = 0; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,12,5,5);
        panelTrainModel.add(labelChooseModel,c);
        comboBoxChooseModel = new JComboBox<>(ClassifierType.values());
        c.gridwidth = 1; c.gridx = 0; c.gridy = 1; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,5,5,5);
        panelTrainModel.add(comboBoxChooseModel,c);
        progressbar = new JProgressBar();
        progressbar.setIndeterminate(true);
        c.gridwidth = 1; c.gridx = 0; c.gridy = 2; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,10,5,10);
        panelTrainModel.add(progressbar,c);
        buttonTrainModel = new JButton("Train Model");
        buttonTrainModel.addActionListener(this);
        buttonTrainModel.setPreferredSize(new Dimension(buttonTrainModel.getPreferredSize().width,50));
        buttonTrainModel.setMinimumSize(new Dimension(buttonTrainModel.getPreferredSize().width,50));
        c.gridwidth = 1; c.gridx = 0; c.gridy = 3; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,5,5,5);
        panelTrainModel.add(buttonTrainModel,c);
        panelTrainModel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY,1)," Train Model "));
        c.gridwidth = 1; c.gridx = 0; c.gridy = 3; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(10,5,0,0);
        column1.add(panelTrainModel,c);
        JPanel panelColumn1Fill = new JPanel();
        c.gridwidth = 1; c.gridx = 0; c.gridy = 10; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(10,0,0,0);
        column1.add(panelColumn1Fill,c);


        JPanel column2 = new JPanel();
        column2.setPreferredSize(new Dimension(500,height));
        column2.setLayout(new GridBagLayout());

        JPanel panelTableClassifiers = new JPanel();
        panelTableClassifiers.setLayout(new GridBagLayout());
        modelTableClassifiers = new DefaultTableModel();
        modelTableClassifiers.addColumn("Algorithm");
        modelTableClassifiers.addColumn("Train Data Size");
        modelTableClassifiers.addColumn("Training Time");
        modelTableClassifiers.addColumn("Success Percent");
        tableClassifiers = new JTable(modelTableClassifiers){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend)
            {
                super.changeSelection(rowIndex, columnIndex, !extend, extend);
            }
        };
        tableClassifiers.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = tableClassifiers.getSelectedRow();
                controller.selectedClassifier(selectedRow);
            }
        });
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        JScrollPane scrollPaneTable = new JScrollPane(tableClassifiers);
        c.gridwidth = 1; c.gridx = 0; c.gridy = 0; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,5,5,5);
        panelTableClassifiers.add(scrollPaneTable,c);
        panelTableClassifiers.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY,1)," Classifiers "));
        c.gridwidth = 1; c.gridx = 0; c.gridy = 0; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(10,5,0,5);
        column2.add(panelTableClassifiers,c);


        JPanel panelDrawGraph = new JPanel();
        panelDrawGraph.setLayout(new GridBagLayout());
        JLabel labelXAxis = new JLabel("X Axis");
        c.gridwidth = 1; c.gridx = 0; c.gridy = 0; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,12,5,5);
        panelDrawGraph.add(labelXAxis,c);
        JLabel labelYAxis = new JLabel("Y Axis");
        c.gridwidth = 1; c.gridx = 1; c.gridy = 0; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,12,5,5);
        panelDrawGraph.add(labelYAxis,c);
        comboBoxXAxis = new JComboBox<>(AxisNames.values());
        c.gridwidth = 1; c.gridx = 0; c.gridy = 1; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,5,5,5);
        panelDrawGraph.add(comboBoxXAxis,c);
        comboBoxYAxis = new JComboBox<>(AxisNames.values());
        c.gridwidth = 1; c.gridx = 1; c.gridy = 1; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,5,5,5);
        panelDrawGraph.add(comboBoxYAxis,c);
        buttonDrawGraph = new JButton("Draw Graph");
        buttonDrawGraph.addActionListener(this);
        c.gridwidth = 1; c.gridx = 2; c.gridy = 1; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,5,5,5);
        panelDrawGraph.add(buttonDrawGraph,c);
        c.gridwidth = 1; c.gridx = 0; c.gridy = 1; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(10,5,0,5);
        panelDrawGraph.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY,1)," Draw Graph "));
        column2.add(panelDrawGraph,c);

        JPanel panelTestExample = new JPanel();
        panelTestExample.setLayout(new GridBagLayout());
        JLabel labelCol1 = new JLabel("Gender");
        c.gridwidth = 1; c.gridx = 0; c.gridy = 0; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,12,5,5);
        panelTestExample.add(labelCol1,c);
        String[] genders = new String[]{"Female","Male","Other"};
        comboBoxGenders = new JComboBox<>(genders);
        c.gridwidth = 1; c.gridx = 0; c.gridy = 1; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,12,5,5);
        panelTestExample.add(comboBoxGenders,c);
        JLabel labelCol2 = new JLabel("Age");
        c.gridwidth = 1; c.gridx = 1; c.gridy = 0; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,12,5,5);
        panelTestExample.add(labelCol2,c);
        textFieldAge = new JTextField();
        c.gridwidth = 1; c.gridx = 1; c.gridy = 1; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,12,5,5);
        panelTestExample.add(textFieldAge,c);
        JLabel labelCol3 = new JLabel("Hypertension");
        c.gridwidth = 1; c.gridx = 2; c.gridy = 0; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,12,5,5);
        panelTestExample.add(labelCol3,c);
        Double[] hypertension = new Double[]{0.0,1.0};
        comboBoxHyperTension = new JComboBox<>(hypertension);
        c.gridwidth = 1; c.gridx = 2; c.gridy = 1; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,12,5,5);
        panelTestExample.add(comboBoxHyperTension,c);
        JLabel labelCol4 = new JLabel("Heart Disease");
        c.gridwidth = 1; c.gridx = 0; c.gridy = 2; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,12,5,5);
        panelTestExample.add(labelCol4,c);
        Double[] heartDisease = new Double[]{0.0,1.0};
        comboBoxHeartDisease = new JComboBox<>(heartDisease);
        c.gridwidth = 1; c.gridx = 0; c.gridy = 3; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,12,5,5);
        panelTestExample.add(comboBoxHeartDisease,c);
        JLabel labelCol5 = new JLabel("Smoking History");
        c.gridwidth = 1; c.gridx = 1; c.gridy = 2; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,12,5,5);
        panelTestExample.add(labelCol5,c);
        String[] smoking = new String[]{"never","No Info","current","former","ever","not current"};
        comboBoxSmokingHistory = new JComboBox<>(smoking);
        c.gridwidth = 1; c.gridx = 1; c.gridy = 3; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,12,5,5);
        panelTestExample.add(comboBoxSmokingHistory,c);
        JLabel labelCol6 = new JLabel("BMI");
        c.gridwidth = 1; c.gridx = 2; c.gridy = 2; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,12,5,5);
        panelTestExample.add(labelCol6,c);
        textFieldBmi = new JTextField();
        c.gridwidth = 1; c.gridx = 2; c.gridy = 3; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,12,5,5);
        panelTestExample.add(textFieldBmi,c);
        JLabel labelCol7 = new JLabel("HbA1c Level");
        c.gridwidth = 1; c.gridx = 0; c.gridy = 4; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,12,5,5);
        panelTestExample.add(labelCol7,c);
        textFieldHbA1cLevel = new JTextField();
        c.gridwidth = 1; c.gridx = 0; c.gridy = 5; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,12,5,5);
        panelTestExample.add(textFieldHbA1cLevel,c);
        JLabel labelCol8 = new JLabel("Blood Glucose Level");
        c.gridwidth = 1; c.gridx = 1; c.gridy = 4; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,12,5,5);
        panelTestExample.add(labelCol8,c);
        textFieldBloodGlucoseLevel = new JTextField();
        c.gridwidth = 1; c.gridx = 1; c.gridy = 5; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,12,5,5);
        panelTestExample.add(textFieldBloodGlucoseLevel,c);
        JLabel labelCol9 = new JLabel("Diabetes");
        c.gridwidth = 1; c.gridx = 2; c.gridy = 4; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,12,5,5);
        panelTestExample.add(labelCol9,c);
        Double[] diabetes = new Double[]{0.0,1.0};
        comboBoxDiabetes = new JComboBox<>(diabetes);
        c.gridwidth = 1; c.gridx = 2; c.gridy = 5; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,12,5,5);
        panelTestExample.add(comboBoxDiabetes,c);
        buttonTestExample = new JButton("Test Example");
        buttonTestExample.addActionListener(this);
        buttonTestExample.setPreferredSize(new Dimension(buttonCreateTestData.getPreferredSize().width,50));
        buttonTestExample.setMinimumSize(new Dimension(buttonCreateTestData.getPreferredSize().width,50));
        c.gridwidth = 3; c.gridx = 0; c.gridy = 6; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,5,5,5);
        panelTestExample.add(buttonTestExample,c);

        c.gridwidth = 1; c.gridx = 0; c.gridy = 2; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(10,5,0,5);
        panelTestExample.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY,1)," Test an Example "));
        column2.add(panelTestExample,c);


        JPanel panelColumn2Fill = new JPanel();
        c.gridwidth = 1; c.gridx = 0; c.gridy = 10; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(10,0,0,0);
        column2.add(panelColumn2Fill,c);


        JPanel column3 = new JPanel();
        column3.setPreferredSize(new Dimension(500,height));
        column3.setLayout(new GridBagLayout());

        JPanel panelGraph = new JPanel();
        panelGraph.setLayout(new GridBagLayout());
        dataset = new XYSeriesCollection();
        seriesJ48 = new XYSeries("J48");
        seriesNaiveBayes = new XYSeries("Naive Bayes");
        seriesRandomForest = new XYSeries("Random Forest");
        dataset.addSeries(seriesJ48);
        dataset.addSeries(seriesNaiveBayes);
        dataset.addSeries(seriesRandomForest);
        chart = ChartFactory.createScatterPlot("", "", "", dataset, PlotOrientation.VERTICAL, true, true, false);
        plot = (XYPlot) chart.getPlot();
        plot.getRenderer().setDefaultItemLabelGenerator(new StandardXYItemLabelGenerator());
        plot.getRenderer().setDefaultItemLabelsVisible(true);
        plot.getRenderer().setSeriesPaint(0, Color.GREEN);
        plot.getRenderer().setSeriesPaint(1, Color.BLUE);
        plot.getRenderer().setSeriesPaint(2, Color.ORANGE);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(450, 370));
        c.gridwidth = 1; c.gridx = 0; c.gridy = 0; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,5,5,5);
        panelGraph.add(chartPanel,c);
        panelGraph.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY,1)," Graph "));
        c.gridwidth = 1; c.gridx = 0; c.gridy = 0; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(10,0,0,5);
        column3.add(panelGraph,c);

        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new GridBagLayout());

        textPane = new JTextPane();
        textPane.setBackground(Color.BLACK);
        textPane.setForeground(Color.WHITE);
        textPane.setEditable(false);
        JScrollPane scrollPane_logs = new JScrollPane(textPane);
        scrollPane_logs.setPreferredSize(new Dimension(scrollPane_logs.getPreferredSize().width,300));
        textPane.setMargin(new Insets(10,10,10,10));
        c.gridwidth = 1; c.gridx = 0; c.gridy = 0; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.HORIZONTAL; c.insets = new Insets(5,5,5,5);
        panelInfo.add(scrollPane_logs,c);
        panelInfo.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY,1)," Information "));
        c.gridwidth = 1; c.gridx = 0; c.gridy = 1; c.weightx = 1.0; c.weighty = 0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(10,0,0,5);
        column3.add(panelInfo,c);

        JPanel panelColumn3Fill = new JPanel();
        c.gridwidth = 1; c.gridx = 0; c.gridy = 10; c.weightx = 1.0; c.weighty = 1.0; c.fill = GridBagConstraints.BOTH; c.insets = new Insets(10,0,0,0);
        column3.add(panelColumn3Fill,c);


        panel_main.add(column1);
        panel_main.add(column2);
        panel_main.add(column3);
        frame.add(panel_main);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == buttonCreateTestData){
            long size = Long.parseLong(String.format("%.0f",spinnerTestDataSize.getValue()));
            controller.createTestData(size);
        }else if(e.getSource() == buttonCreateTrainingData){
            long size = Long.parseLong(String.format("%.0f",spinnerTrainingDataSize.getValue()));
            controller.createTrainingData(size);
        }else if (e.getSource() == buttonTrainModel){
            if(trainingDataSize <= 0){
                showPopup("No training data to train model!");
            }else{
                controller.trainModel((ClassifierType)comboBoxChooseModel.getSelectedItem());
            }
        }else if(e.getSource() == buttonDrawGraph){
            int[] selectedRows = tableClassifiers.getSelectedRows();
            controller.drawGraph(selectedRows,(AxisNames) comboBoxXAxis.getSelectedItem(),(AxisNames) comboBoxYAxis.getSelectedItem());
        }else if(e.getSource() == buttonTestExample){
            int selectedRow = tableClassifiers.getSelectedRow();
            if(selectedRow < 0){
                textPane.setText("No selected model");
                return;
            }
            String gender = (String)comboBoxGenders.getSelectedItem();
            double heartDisease = (double)comboBoxHeartDisease.getSelectedItem();
            double hypertension = (double)comboBoxHyperTension.getSelectedItem();
            String smokingHistory = (String)comboBoxSmokingHistory.getSelectedItem();
            double diabetes = (double)comboBoxDiabetes.getSelectedItem();
            double age,bmi,hba1cLevel,bloodGlucoseLevel;
            try{
                age = Double.parseDouble(textFieldAge.getText());
                bmi = Double.parseDouble(textFieldBmi.getText());
                hba1cLevel = Double.parseDouble(textFieldHbA1cLevel.getText());
                bloodGlucoseLevel = Double.parseDouble(textFieldBloodGlucoseLevel.getText());
            }catch (Exception ex){
                textPane.setText(ex.getMessage());
                return;
            }
            ExampleData exampleData = new ExampleData(gender,age,hypertension,heartDisease,smokingHistory,bmi,hba1cLevel,bloodGlucoseLevel,diabetes);
            controller.testExample(selectedRow,exampleData);
        }
    }

    private void setLabelDataInfo(long size){ labelDataInfo.setText("Full Data Set Size : " + size); }
    private void setLabelTrainingDataInfo(long size){ labelTrainingDataInfo.setText("Training Data Set Size : " + size); }
    private void setLabelTestDataInfo(long size){ labelTestDataInfo.setText("Test Data Set Size : " + size); }

    public void enableTrainButton(){buttonTrainModel.setEnabled(true);}
    public void disableTrainButton(){buttonTrainModel.setEnabled(false);};
    public void showTrainProgressBar(){progressbar.setVisible(true);};
    public void hideTrainProgressBar(){progressbar.setVisible(false);};

    private void fillClassifierTable(List<Classifier> classifiers){
        modelTableClassifiers.setRowCount(0);
        int i=0;
        for(Classifier classifier : classifiers){
            addClassifierToTable(classifier,i);
            i++;
        }
    }

    private void addClassifierToTable(Classifier classifier, int index){
        modelTableClassifiers.addRow(new Object[]{"(" + (index+1) +") " + classifier.type,classifier.trainingDataSize,classifier.trainingTimeMillis,classifier.successPercent});
    }


    @Override
    public void showPopup(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    @Override
    public void updateFullDataSize(long size) {
        fullDataSize = size;
        spinnerTestSizeModel = new SpinnerNumberModel(0, 0, fullDataSize, 1);
        spinnerTestDataSize.setModel(spinnerTestSizeModel);
        spinnerTrainingSizeModel = new SpinnerNumberModel(0, 0, fullDataSize, 1);
        spinnerTrainingDataSize.setModel(spinnerTrainingSizeModel);
        setLabelDataInfo(size);
    }

    @Override
    public void updateTrainingDataSize(long size) {
        trainingDataSize = size;
        setLabelTrainingDataInfo(size);
    }

    @Override
    public void updateTestDataSize(long size) {
        testDataSize = size;
        setLabelTestDataInfo(size);
    }

    @Override
    public void updateList(List<Classifier> classifiers) {
        hideTrainProgressBar();
        enableTrainButton();
        fillClassifierTable(classifiers);
    }

    @Override
    public void updateGraph(ArrayList<Point> points,String xAxis,String yAxis, String graphName) {
        plot.getDomainAxis().setLabel(xAxis);
        plot.getRangeAxis().setLabel(yAxis);
        chart.setTitle(graphName);
        seriesJ48.clear();
        seriesRandomForest.clear();
        seriesNaiveBayes.clear();
        for(Point point : points){
            if(point.getType() == ClassifierType.J48) seriesJ48.add(point.getX(),point.getY());
            if(point.getType() == ClassifierType.NaiveBayes) seriesNaiveBayes.add(point.getX(),point.getY());
            if(point.getType() == ClassifierType.RandomForest) seriesRandomForest.add(point.getX(),point.getY());
        }
        plot.getRenderer().setDefaultItemLabelGenerator(new CustomXYItemLabelGenerator(points));
        plot.getRenderer().setDefaultItemLabelsVisible(false);
    }

    @Override
    public void updateInformationArea(String information) {
        textPane.setText(information);
        System.out.println(information);
    }

    @Override
    public void updateTestResult(String result) {
        textPane.setText(result);
    }
}

class CustomXYItemLabelGenerator extends StandardXYItemLabelGenerator {
    ArrayList<Point> points;
    public CustomXYItemLabelGenerator(ArrayList<Point> points) {
        super();
        this.points = points;
    }
    @Override
    public String generateLabel(XYDataset dataset, int series, int item) {
        int index=0;
        for(int i=0;i<points.size();i++){
            if(points.get(i).getX() == dataset.getXValue(series,item) && points.get(i).getY() == dataset.getYValue(series,item)){
                index = i;
            }
        }
        return points.get(index).getName();
    }
}