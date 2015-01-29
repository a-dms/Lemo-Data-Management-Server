package de.lemo.dms.processing.classification;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import de.lemo.dms.processing.questions.QDatabase;
import de.lemo.dms.processing.resulttype.ResultListUserInstance;
import de.lemo.dms.processing.resulttype.UserInstance;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
/**
 * Generates a little ARFF file with different attribute types.
 *
 * @author FracPete
 */
public class Classifier {

	private int numAttributes;
	private Instances instances;
	private List<UserInstance> userInstancesTraining;
	private NaiveBayes classifier;
	private List<UserInstance> userInstancesTesting;

	public List<UserInstance> getUserInstancesTesting() {
		return userInstancesTesting;
	}

	public void setUserInstancesTesting(List<UserInstance> userInstancesTesting) {
		this.userInstancesTesting = userInstancesTesting;
	}

	public Classifier() {
	}

	public Classifier(Long trainCourseId, Long testCourseId) {
		userInstancesTraining = new QDatabase().queryAllUserInstances(trainCourseId);
		userInstancesTesting = new QDatabase().queryAllUserInstances(testCourseId);
		setUserInstances(userInstancesTraining);
		createWekaData(userInstancesTraining);
		//crossValidateClassifier();
		trainClassifier();
		applyClassifier();
		//saveArff();
	}

	private void applyClassifier() {
	    createWekaData(userInstancesTesting);
	    double predictedClass;
		for(int i=0; i < instances.numInstances(); i++){
			try {
				predictedClass=classifier.classifyInstance(instances.instance(i));
				userInstancesTesting.get(i).setClassId((int)predictedClass);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			Evaluation eval = new Evaluation(instances);
			eval.evaluateModel(classifier, instances);
			System.out.println(eval.toSummaryString("\nResults\n======\n", false));	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	private void saveArff() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("/home/vk/Desktop/UserInstances_vs_2.arff"));
			writer.write(instances.toString());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void crossValidateClassifier() {

		classifier = new NaiveBayes();         // new instance of tree
		try {
			//classifier.buildClassifier(instances);   // build classifier
			Evaluation eval = new Evaluation(instances);
			eval.crossValidateModel(classifier, instances, 3, new Random(1));
			System.out.println("Instances: " +instances.numInstances());
			System.out.println("Correct classified: "+eval.correct());
			System.out.println("Percentage correct classified: "+eval.pctCorrect());
			System.out.println("Kappa: "+eval.kappa());
			System.out.println("Precision: "+eval.precision(1));
			System.out.println("Recall: "+eval.recall(1));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     // set the options
	}

	private void trainClassifier(){
		classifier = new NaiveBayes(); 
		try {
			classifier.buildClassifier(instances);
			Evaluation eval = new Evaluation(instances);
			eval.evaluateModel(classifier, instances);
			System.out.println(eval.toSummaryString("\nResults\n======\n", false));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createWekaData(List<UserInstance> userInstances) {
		FastVector      atts;

		// 1. set up attributes
		atts = defineAttributes();
		numAttributes = atts.size();
		// 2. create Instances object
		instances = new Instances("UserInstances", atts, 0);
		// 3. fill with data		
		for(UserInstance userInstance : userInstances){
			instances.add(createWekaInstance(userInstance));			
		}
		instances.setClassIndex(instances.numAttributes() - 1);
		// 4. output data
		System.out.println("Class attribute: " + instances.numAttributes());		
	}
	
	private Instance createWekaInstance(UserInstance userInstance) {
		double[] vals = new double[numAttributes];
		// - numeric
		vals[0] = userInstance.getDownVotes();
		vals[1] = userInstance.getImageCount();
		vals[2] = userInstance.getLinkCount();
		vals[3] = userInstance.getUpVotes();
		vals[4] = userInstance.getWordCount();
		vals[5] = userInstance.getReceivedUpVotes();
		vals[6] = userInstance.getReceivedDownVotes();
		vals[7] = userInstance.isForumUsed()?0:1;
		vals[8] = userInstance.getAnswerCount();
		vals[9] = userInstance.getCommentCount();
		vals[10] = userInstance.getPostCount();
		vals[11] = userInstance.getClassId();

		return new Instance(1.0, vals);
	}

	private FastVector defineAttributes() {
		FastVector atts = new FastVector();

		atts.addElement(new Attribute("DownVotes"));
		atts.addElement(new Attribute("ImageCount"));
		atts.addElement(new Attribute("LinkCount"));
		atts.addElement(new Attribute("UpVotes"));
		atts.addElement(new Attribute("WordCount"));
		atts.addElement(new Attribute("ReceivedUpVotes"));
		atts.addElement(new Attribute("ReceivedDownVotes"));

		FastVector forumActivity = new FastVector();
		forumActivity.addElement("active");
		forumActivity.addElement("passive");
		atts.addElement(new Attribute("ForumParticipation", forumActivity));
		
		atts.addElement(new Attribute("AnswerCount"));
		atts.addElement(new Attribute("CommentCount"));
		atts.addElement(new Attribute("PostCount"));
		
		FastVector classId = new FastVector();
		classId.addElement("failed");
		classId.addElement("passed");
		atts.addElement(new Attribute("ClassId", classId));

		return atts;
	}

	public Instances getInstances() {
		return instances;
	}

	public void setInstances(Instances instances) {
		this.instances = instances;
	}

	public List<UserInstance> getUserInstances() {
		return userInstancesTraining;
	}

	public void setUserInstances(List<UserInstance> userInstances) {
		this.userInstancesTraining = userInstances;
	}

	public ResultListUserInstance trainAndTestUserInstances(
			List<UserInstance> trainInstances, List<UserInstance> testInstances) {
		userInstancesTraining = trainInstances;
		userInstancesTesting = testInstances;
		setUserInstances(userInstancesTraining);
		createWekaData(userInstancesTraining);
		//crossValidateClassifier();
		trainClassifier();
		applyClassifier();
		saveArff();
		return new ResultListUserInstance(getUserInstancesTesting());
	}
}