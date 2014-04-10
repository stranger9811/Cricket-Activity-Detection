
import java.io.*;
import java.util.*;

public class ColorHistogram1
{
	byte file[];	//Buffer to store content of processing file.
	
	InputStream is; //Reference of InputStream object which refers to file under processing
	int row,col,header,maxframe;
	double feature[][];
	
	Vector histogramVector;
	Vector blockVector;

	int binsize;
	int noofblocks;
	int nooffeatures;
	int previousframes;
	String folderName;	
	String frameExtension;
	String outputfile;
	ColorHistogram1(int row,int col,int header,int maxframe,int binsize,int noofblocks,int nooffeatures,int previousframes,String folderName,String frameExtension,String outputfile) 
	{
		
		this.row = row;
		this.col = col;
		this.header = header;this.nooffeatures =  nooffeatures;
		this.binsize = binsize;
		this.noofblocks = noofblocks;
		this.maxframe = maxframe;
		this.previousframes = previousframes;
		this.folderName = folderName;
		this.frameExtension = frameExtension;
		this.outputfile = outputfile;
			
		file = new byte[row*col*3+header];
		feature =  new double[maxframe][nooffeatures];
		
		histogramVector = new Vector();
		blockVector = new Vector();
		
		
	}

	public void getFile(String fileName) 
	{
		try 
		{
			is = new FileInputStream("testing_snap_ppm/"+folderName+fileName);
			is.read(file);
		}
		catch (Exception e) 
		{
			System.out.println(e);
		}
	}
 
	public void getFeature(int frameno) 
	{
            System.out.println("current_frame"+frameno);
			double r,g,b;
			double y,u,v;
			int color[][] = new int[binsize][binsize];
			int color1[][] = new int[binsize][binsize];
			int block[][][][] =  new int[noofblocks][noofblocks][binsize][binsize];
			int block1[][][][] =  new int[noofblocks][noofblocks][binsize][binsize];
			int col1,row1;
			
			int index;
			double max,temp,max1;
			
			double umin = -112, vmin = -157;

			double urange = (-2*umin)/binsize,vrange = (-2*vmin)/binsize;
			
			
			double rowrange= row/noofblocks,colrange=col/noofblocks;
			
			int i=0,j=0,fileno=1;
			double k=0.0,l=0.0;
			
			
			
			for(i=0;i<binsize;i++)
			for(j=0;j<binsize;j++)
				color[i][j]=0;

			for(row1=0;row1<noofblocks;row1++)
			for(col1=0;col1<noofblocks;col1++)
			for(i=0;i<binsize;i++)
			for(j=0;j<binsize;j++)
				block[row1][col1][i][j]=0;
			
			getFile(frameno+".ppm");
			
			for (int pixel = 0; pixel < row*col  ; pixel++) 
			{
						
				r = file[header+pixel*3+0];
				g= file[header+pixel*3+1];
				b= file[header+pixel*3+2];
	
				r= r+128;
				g= g+128;
				b= b+128;
					
				y =( 0.299*r + 0.587*g + 0.114*b);
   				u = (-0.147*r-0.289*g+0.436*b);
   				v = (0.615*r - 0.515*g-0.1*b);
   				
   				i = (int)((u-umin)/urange);
   				j = (int)((v-vmin)/vrange);
				
				//System.out.println(i+" "+j+" urange "+urange + " u " +u+" umin "+umin);
   				color[i][j]=color[i][j]+1;

 				col1 =(int)( (pixel % col)/colrange);
  				row1 =(int) ((pixel/col)/rowrange);
   				
	  			block[row1][col1][i][j]++;
   					
			}
			
			/* For Previous 30 Frames */
			
			
			index = 0;
			for(index=0;index<histogramVector.size();index++)
			{
				
				color1 = (int[][])(histogramVector.get(histogramVector.size()-index-1));
				block1 = (int[][][][])(blockVector.get(blockVector.size()-index-1));
				k=0;
				
				for(i=0;i<binsize;i++)
				for(j=0;j<binsize;j++)
				{
					//k = k + Math.abs(color1[i][j]-color[i][j]);
					//Above was the previous code
					if(color1[i][j]!= 0 || color[i][j]!=0)
					{
						l= Math.pow(color1[i][j]-color[i][j],2.0);
						l= l/Math.max(color1[i][j],color[i][j]);
						k = k+l;
					}
					else
					{
						k+= 0;
					}
					
				
				}
				
				feature[frameno-1][index] =(double)k/(double)(2*row*col);
				
				k=0;
				
				for(row1=0;row1<8;row1++)
				for(col1=0;col1<8;col1++)
				{
					max=0;
					max1=0;
					for(i=0;i<binsize;i++)
					for(j=0;j<binsize;j++)
					{
						if(block1[row1][col1][i][j] != 0 || block[row1][col1][i][j]!= 0)
						{
						
							l= Math.pow(block1[row1][col1][i][j]-block[row1][col1][i][j],2);
							l= l/Math.max(block1[row1][col1][i][j],block[row1][col1][i][j]);
							temp = l;
							if(max<temp)
							{
								k=k+max;
								max=temp;
							}
							else
							{
								if(max1<temp)
								{
								
									k=k+max1;
									max1=temp;
								}
								else
									k+=temp;
							}
						}
						else 
							k= k+0;
						//Neglecting Top Two Differences
					}
				}
				feature[frameno-1][index+previousframes]=(double)k/(double)(2*row*col);

			}

			for(i=index;i<previousframes;i++)
			{
				feature[frameno-1][i]=1;
				feature[frameno-1][i+previousframes]=1;
			}
	
			
			try
			{
				if(histogramVector.size()==previousframes)
				{
					histogramVector.remove(0);
					blockVector.remove(0);
				}
			}
			catch(Exception e)
			{
				System.out.println("Value of Frame no. \n"+frameno);
			}
			
			histogramVector.add(color);
			blockVector.add(block);
	}
	
	
	public void createARFF()
	{
		int i,j;
		String str="";
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(outputfile));
			/*bw.write("% 1. Title: Frame Classifier\n"+
   					"% \n"+
   					"% 2. Sources:\n"+
   					"%      (a) Creator: Ashok Kumar & Javesh garg\n"+
   					"%      (b) Date: April, 2014\n"+
   					"%\n"+ 
   					"@RELATION VideoSegmentation\n");*/
   			//bw.flush();

   			for(i=1;i<=previousframes*2;i++)
   				str+="@ATTRIBUTE feature"+i+"  NUMERIC\n";
	
			str+="@ATTRIBUTE frame {Cut,NonCut,Gradual}\n"+"\n\n"+"@data\n\n\n";
   			bw.write(str);
   
   
   			bw.flush();
   			
   			String userid,line;
			

		//	BufferedReader in = new BufferedReader(new FileReader("tagBor.txt") );
	

   			for(i=0;i<maxframe;i++)
   			{
   				str="";
   				for(j=0;j<nooffeatures;j++) {
                    if(j!=nooffeatures-1)
                        str = str + feature[i][j] + ",";
                    else
                        str = str + feature[i][j];
                }
		//		line = in.readLine();
				
			//	StringTokenizer s = new StringTokenizer(line,"\t");
			//	s.nextToken();

				/*Get Frame type whether it is shot frame or not */   			
   				//str = str + s.nextToken()+ "\n";
               // str = str + (i+1);
   				str = str + "\n";
   				
   				bw.write(str);
   				bw.flush();
   	
   			}
   		
		}
		catch(Exception e)
		{System.out.println(e);
		}
	
	}
	public static void main(String args[]) 
	{
		int frameno,maxframe=13001;
		int binsize=40;
		int noofblocks=8;
		int nooffeatures=60;
		int previousframes=30;
		int rows=800;
		int cols=800;
		int header=15;
		
		String folderName= "snaps";
		String frameExtension=".ppm";
		String outputfile = "Dataset.ARFF";
		
		
		ColorHistogram1 ch = new ColorHistogram1(rows,cols,15,maxframe,binsize,noofblocks,nooffeatures,previousframes,folderName,frameExtension,outputfile);

		for(int i=1;i<=maxframe;i++)
		{
			
			ch.getFeature(i);
			if(i%100==0)
				System.out.print(i+"...");
		
		}
		ch.createARFF();
	}
	
}

