import weka.core.Instances;
import java.io.*;
import weka.classifiers.trees.Id3;
import weka.classifiers.lazy.*;
import weka.core.*;
import java.util.*;
import weka.classifiers.*;



class L1classify
{
	String trainingFile;
	String testingFile;
	String resultFile;
	
	L1classify(String trainingFile,String testingFile,String resultFile)	
	{
		this.trainingFile = trainingFile;
		this.testingFile = testingFile;
		this.resultFile = resultFile;
	}
	
	public void l1classify()
	{
		
		try
		{
			double distribution[];
			String line;
			int lastCut;
			int index=0;
			Vector cutBoundary = new Vector();
			IBk ibk;
			ObjectInputStream ois;
			Instance ins;	
			
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
 				ibk = new IBk(5);	
				i.setClassIndex(i.numAttributes() - 1); 
				ibk.buildClassifier(i);
				Classifier cls[];
				cls = weka.classifiers.Classifier.makeCopies(ibk,1);
//				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("L1Classifier"));
//				oos.writeObject(cls[0]);
				reader.close();
				
			}
			

			System.out.println("Classifier is Ready ");
			
			ins = new Instance(61);
			BufferedReader in = new BufferedReader(new FileReader(testingFile) );
			BufferedWriter out ;
			int count_result=0;
			System.out.println("cut_obtained = [");
			while((line=in.readLine())!=null)
			{
				index++;
			//	System.out.println(line);
				StringTokenizer s = new StringTokenizer(line,",");
				
				for(int j=0;j<60;j++)		
					ins.setValue(j,Double.parseDouble(s.nextToken()));
				
// 	This one for First Level Classifier

				distribution = ibk.distributionForInstance(ins);
				if(distribution[0]>distribution[1])
				{
//					if((index-lastCut)>100)
					{
						//System.out.println("Cut at "+index+"\t"+distribution[0]);
						System.out.print(index+",");
						cutBoundary.add(index+"\n");
						count_result++;
						lastCut=index;
					}
					
					
				}
			}
			System.out.println("total_result= " + count_result );
			out = new BufferedWriter(new FileWriter(resultFile));
			for(int i=0;i<cutBoundary.size();i++)
				out.write((String)cutBoundary.get(i));
			out.flush();
						
		}
		catch(Exception e)
		{
			e.printStackTrace();
		
		}
		
	}
	
	public static void main(String args[])
	{
		
		L1classify l1c = new L1classify("traindata.ARFF","testdata.ARFF","result.ARFF");
		l1c.l1classify();
		
	}
}