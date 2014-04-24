import weka.core.Instances;
import java.io.*;
import weka.classifiers.trees.Id3;
import weka.classifiers.lazy.*;
import weka.core.*;
import java.util.*;
import weka.classifiers.*;



class L2Classify
{
	String trainingFile;
	String testingFile;
	String resultFile;
	
	L2Classify(String trainingFile,String testingFile,String resultFile)	
	{
		this.trainingFile = trainingFile;
		this.testingFile = testingFile;
		this.resultFile = resultFile;
	}
	
	
	public void l2Classify()
	{
		try
		{
			double distribution[];
			String line;
			int index=0;
			Vector gradualBoundary = new Vector();
			int lastCut=-100,lastGradual=-100,beginGradual=0,endGradual=0,tempGradual=0;
			ObjectInputStream ois;
			IBk ibk;

 			
 			try
			{
				ois = new ObjectInputStream(new FileInputStream(trainingFile));
				ibk = (IBk)ois.readObject();
			}
			catch(Exception e)
			{
				System.out.println("Classifier Not Found.. Creating from Training Data ");
				Reader reader = new FileReader(trainingFile);
				Instances i = new Instances(reader);
 				ibk = new IBk(10);	
				i.setClassIndex(i.numAttributes() - 1); 
				ibk.buildClassifier(i);
				Classifier cls[];
				cls = weka.classifiers.Classifier.makeCopies(ibk,1);
//				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("L2Classifier"));
//				oos.writeObject(cls[0]);
				reader.close();
				
			}
			

			System.out.println("Classifier is Ready ");
			
			Instance ins = new Instance(61);
			
			BufferedReader in = new BufferedReader(new FileReader(testingFile) );
			BufferedWriter out ;

			while((line=in.readLine())!=null)
			{
				index++;
				
				StringTokenizer s = new StringTokenizer(line,",");
			
				for(int j=0;j<60;j++)		
					ins.setValue(j,Double.parseDouble(s.nextToken()));
				
				distribution = ibk.distributionForInstance(ins);

				if(distribution[1]>distribution[0])
				{
					System.out.println("Some Gradual\t"+(index)+"\t"+beginGradual+"\t"+tempGradual);
					//					if((index-lastGradual)>100)
					{
						if(beginGradual==0)
							beginGradual=index;
						else
						
							if((index-beginGradual)<8 || (index-tempGradual)<8)
							{
								tempGradual = index;
							}
							else
							{
								if((tempGradual-beginGradual)>5)
								{
									
									gradualBoundary.add(new GradualObject(beginGradual,tempGradual));
									beginGradual = index;
									
								}
								else
									beginGradual=index;
							}
								
					}
					
					
				}
								
				if(index%100==0)
					System.out.println(index+"");
			}

			out = new BufferedWriter(new FileWriter(resultFile));
			for(int i=0;i<gradualBoundary.size();i++)
			{
				out.write((GradualObject)gradualBoundary.get(i)+"\n");
				out.flush();
			}
						
		}
		catch(Exception e)
		{
			e.printStackTrace();
		
		}
		
		
	}
	
	public static void main(String args[])
	{
		L2Classify l2c = new L2Classify("traindata.ARFF","testdata.ARFF","result.ARFF");
		l2c.l2Classify();
	}
}

class GradualObject
{
	int begin;
	int end;
	GradualObject(int begin,int end)
	{
		this.begin  = begin;
		this.end = end;
	}
	public String toString()
	{
		return (begin + "\t"+  end);
	}
	
}


