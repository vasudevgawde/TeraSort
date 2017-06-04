import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SortingFile 
{
	public static void main(String args[])
	{
		//String line;
	  //  int count=0;
	  //  String[] arr = new String[100000000];
	    //String[] valus = new String[100000000];
		//fileName = file10kb
		//	fileSizeInByte=10000
			//NumberOfThreads = 20
			//chunkSize=100
		
		Properties pro = new Properties();
		try {
			pro.load(new FileInputStream(new File(
					"./resources/config.properties")));
		} catch (Exception E) {
			System.out.println("Error in reading  properties file");
		}
		
		File f1 = new File("./dest/");
	    File[] files1 = f1.listFiles();
	    for(int k=0;k<files1.length;k++)
	    {
	    	files1[k].delete();
	    }
	    String fileName = pro.getProperty("fileName");
		long starttime = System.nanoTime();
	    long fileSizeinbyte = Long.parseLong(pro.getProperty("fileSizeInByte"));// this is 10MB file
	    long numberOfbytetoRead =Long.parseLong(pro.getProperty("chunkSize")); // this size is equal to 10 MB file at a time per job
	    long totallines = fileSizeinbyte/100;
	    int numberOfThreads =Integer.parseInt(pro.getProperty("NumberOfThreads")); // number of threads for reading in chunks...will be used by exectutor
	    long numberofJobs = fileSizeinbyte/numberOfbytetoRead;
	    long numberoflines = totallines/numberofJobs;
	    ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
	    long byteToStart = 0;
	    for(int job =0;job<numberofJobs;job++)
	    {
	    	Runnable worker = new Threadjob(byteToStart,(int)(long)numberoflines,job,fileName);
            executor.execute(worker);
            byteToStart = (byteToStart+numberOfbytetoRead);
	    }
	    executor.shutdown();
	    while (!executor.isTerminated())
	    {
        }
	    File f = new File("./dest/");
	    File[] files = f.listFiles();
	    System.out.println("Starting the merge");
	    while(files.length>1)
	    {
	    	//this means we have to merge the files which are already sorted...
	    	ExecutorService executornew = Executors.newFixedThreadPool(files.length/2);
	    	int p=0;
	    	for(int k=0;k<files.length/2;k++)
	    	{
	    		Runnable worktodo = new mergeThreadJob(files[p].getName(),files[++p].getName());
	    		p++;
	    		executornew.execute(worktodo);
	    		
	    	}
	    	 executornew.shutdown();
	 	    while (!executornew.isTerminated())
	 	    {
	         }
	    	f = new File("./dest/");
	    	files = f.listFiles();
	    }
	   // System.out.println("Finished all threads");
		//File file = new File("./source/filenew");
		
		/*try (BufferedReader br = new BufferedReader(new FileReader(file))) 
		{
		    
		    
		    while ((line = br.readLine()) != null) {
		    	arr[count]=line.substring(0, 10);
		    	valus[count]=line.substring(11, 98);
		    	count++;
		       
		    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		//String arr[] = {"adEdhl#","`@SAFN","b@Pasdas","43FESs2","Fsd@!`"};
		//String valus[] = {"this is the 1 line"," this is the 2 line","this is the 3 line","this is the 4 line","this is the 5 line"};
	//	mergeSortThearr(arr,valus,0,count-1);
		System.out.println("The file is sorted... ");
		System.out.println("Please find the sorted file at location ./dest/ ");
		//printthearr(arr,valus,count-1);
		long endtime = System.nanoTime();
		long totaltime = endtime-starttime;
		System.out.println("Total time taken in ms :" + totaltime/1000000);
		
	}
	
	static int partition(String arr[],String valuesarr[], int low, int high)
	{
		String pivot = arr[high]; 
        int i = (low-1); 
        for (int j=low; j<=high-1; j++)
        {
           
            if (comparestring(arr[j],pivot))
            {
            	i++;
                String temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                
                String temp2=valuesarr[i];
                valuesarr[i] = valuesarr[j];
                valuesarr[j] = temp2;
                
            }
        }
 
       
        String temp1 = arr[i+1];
        arr[i+1] = arr[high];
        arr[high] = temp1;
 
        String temp3 = valuesarr[i+1];
        valuesarr[i+1] = valuesarr[high];
        valuesarr[high] = temp3;
        return i+1;
		
	}
	static boolean comparestring(String str1,String str2)
	{
		char[] tempstr1 = str1.toCharArray();
		char[] tempstr2 = str2.toCharArray();
		for(int i=0;i<tempstr1.length;i++)
		{
			if(tempstr1[i]<tempstr2[i] && tempstr1[i]!=tempstr2[i])
			{
				return false;
			}
			else if(tempstr1[i]!=tempstr2[i])
			{
				return true;
			}	
		}
		return true;
		
	}
	static void mergeSortThearr(String[] arr1,String[] valu1, int low,int high)
	{
		if (low < high)
        {
            int pi = partition(arr1,valu1, low, high);
            mergeSortThearr(arr1,valu1, low, pi-1);
            mergeSortThearr(arr1,valu1, pi+1, high);
            
        }
		
		
	}
	static void printthearr(String[] arr,String[] value,int last,int jobnum)
	{
		
		String filename = "testout"+jobnum;
		File file = new File("./dest/"+filename);
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<last;i++)
		{
			
				try {
					fileWriter.write(arr[i]+value[i]);
					fileWriter.append('\n');
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//fileWriter.write("a test");
			
			//System.out.print(arr[i] + ": " + value[i]);
			//System.out.println();
		
		}//end of for
		try {
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
