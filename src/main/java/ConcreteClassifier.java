import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;

public class ConcreteClassifier extends Classifier{

    public ConcreteClassifier(ClassifierType _type, Instances _trainingData, Instances _testData) {
        type = _type;
        trainingData = _trainingData;
        testData = _testData;

        testData.setClassIndex(testData.numAttributes()-1);
        trainingData.setClassIndex(trainingData.numAttributes()-1);

        try {
            NumericToNominal convert = new NumericToNominal();
            String[] options= new String[2];
            options[0]="-R";
            options[1]= Integer.toString(trainingData.classIndex()+1);
            convert.setOptions(options);
            convert.setInputFormat(trainingData);
            trainingData = Filter.useFilter(trainingData, convert);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            NumericToNominal convert = new NumericToNominal();
            String[] options= new String[2];
            options[0]="-R";
            options[1]= Integer.toString(testData.classIndex()+1);
            convert.setOptions(options);
            convert.setInputFormat(testData);
            testData = Filter.useFilter(testData, convert);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        trainModel(trainingData);
    }

    @Override
    double testData(Instance instance) {
        double predictedClass = -1;
        if(this.type==ClassifierType.J48) {
            try {
                predictedClass = j48Classifier.classifyInstance(instance);
                return predictedClass;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if(this.type==ClassifierType.NaiveBayes) {
            try {
                predictedClass = naiveBayesClassifier.classifyInstance(instance);
                return predictedClass;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if(this.type==ClassifierType.RandomForest) {
            try {
                predictedClass = randomForestClassifier.classifyInstance(instance);
                return predictedClass;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return predictedClass;
    }

    @Override
    void trainModel(Instances instances) {
        if(this.type==ClassifierType.J48){
            try {
                long start = System.currentTimeMillis();
                this.j48Classifier = new J48();
                j48Classifier.buildClassifier(instances);
                long end = System.currentTimeMillis();
                this.trainingTimeMillis = end-start;
                Evaluation evaluation = new Evaluation(trainingData);
                evaluation.evaluateModel(j48Classifier,testData);
                this.successPercent = evaluation.pctCorrect();
                this.trainingDataSize = trainingData.numInstances();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if(type==ClassifierType.RandomForest){
            try {
                long start = System.currentTimeMillis();
                this.randomForestClassifier = new RandomForest();
                randomForestClassifier.buildClassifier(instances);
                long end = System.currentTimeMillis();
                this.trainingTimeMillis = end-start;
                Evaluation evaluation = new Evaluation(trainingData);
                evaluation.evaluateModel(randomForestClassifier,testData);
                this.successPercent = evaluation.pctCorrect();
                this.trainingDataSize = trainingData.numInstances();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if(type==ClassifierType.NaiveBayes){
            try {
                long start = System.currentTimeMillis();
                this.naiveBayesClassifier = new NaiveBayes();
                naiveBayesClassifier.buildClassifier(instances);
                long end = System.currentTimeMillis();
                this.trainingTimeMillis = end-start;
                Evaluation evaluation = new Evaluation(trainingData);
                evaluation.evaluateModel(naiveBayesClassifier,testData);
                this.successPercent = evaluation.pctCorrect();
                this.trainingDataSize = trainingData.numInstances();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


}
