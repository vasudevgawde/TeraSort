import java.io.IOException;
import java.io.RandomAccessFile;



public class Threadjob implements Runnable {
     
    private long startBy;
    private int numberOfLines;
    private int jobNumber;
    private String fileName;
    
    public Threadjob(long byt,int numLine,int jobID,String filename){
        this.startBy=byt;
        this.numberOfLines=numLine;
        this.jobNumber= jobID;
        this.fileName= filename;
    }
 
    @Override
    public void run() {
    	
    	
    	String[] keyArr= new String[numberOfLines];
    	String[] valArr= new String[numberOfLines];
    	String temp="";
    	try{
    	RandomAccessFile raf = new RandomAccessFile("./source/"+fileName,"rw");
    	for(int linenumber=0;linenumber<numberOfLines;linenumber++)
    	{
    		
    	raf.seek(startBy);
    	temp = raf.readLine();
    	keyArr[linenumber]= temp.substring(0, 10);
    	valArr[linenumber]= temp.substring(10, 98);
    	startBy = startBy + 100;
    	}
    	raf.close();
    	}catch(IOException ex)
    	{
    		ex.printStackTrace();	
    	}
    	//SortingFile sr = new SortingFile();
    	SortingFile.mergeSortThearr(keyArr, valArr, 0, numberOfLines-1);
    	SortingFile.printthearr(keyArr, valArr,numberOfLines,jobNumber);
        //System.out.println(Thread.currentThread().getName()+" Start. Command = "+command);
       // processCommand();
        //System.out.println(Thread.currentThread().getName()+" End.");
    }
 
    
 
   
}