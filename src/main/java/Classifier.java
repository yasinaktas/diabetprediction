import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Instance;
import weka.core.Instances;

public abstract class Classifier {
    Instances trainingData;
    Instances testData;
    J48 j48Classifier;
    RandomForest randomForestClassifier;
    NaiveBayes naiveBayesClassifier;
    String classifierName;
    ClassifierType type;
    double successPercent;
    long trainingDataSize;
    double trainingTimeMillis;
    abstract double testData(Instance instance);
    abstract void trainModel(Instances instances);
}
