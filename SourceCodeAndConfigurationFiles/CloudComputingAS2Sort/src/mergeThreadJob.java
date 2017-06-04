import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;


public class mergeThreadJob implements Runnable{
	
	String file1;
	String file2;
	 public mergeThreadJob(String file1,String file2){
	        this.file1=file1;
	        this.file2=file2;
	        
	    }
	 
	    @Override
	    public void run() {
	    	
	    	File fileOne = new File("./dest/"+file1);
	    	File fileTwo = new File("./dest/"+file2);
	    	try{
	    	
			FileReader fileReaderOne = new FileReader(fileOne);
			FileReader fileReaderTwo = new FileReader(fileTwo);
			
			BufferedReader bufferedReaderOne = new BufferedReader(fileReaderOne);
			BufferedReader bufferedReaderTwo = new BufferedReader(fileReaderTwo);
			
			File fileop = new File("./dest/"+file1+"new");
			FileWriter fileWriter = new FileWriter(fileop);
			String lineOne= bufferedReaderOne.readLine();
			String lineTwo=bufferedReaderTwo.readLine();
			boolean lineonenull= false;
			boolean linetwonull=false;
			while (true) 
			{
				if(SortingFile.comparestring(lineOne.substring(0, 10), lineTwo.substring(0, 10)))
				{
					
					fileWriter.write(lineOne);
					fileWriter.append('\n');
					if((lineOne = bufferedReaderOne.readLine())==null)
					{
						lineonenull=true;
						break;
					}
				}
				else
				{
					fileWriter.write(lineTwo);
					fileWriter.append('\n');
					if((lineTwo = bufferedReaderTwo.readLine())==null)
					{linetwonull=true;
						break;
					}
				}
				
			//	stringBuffer.append(lineTwo);
				//stringBuffer.append("\n");
			}//end while
			
				if(linetwonull==true)
				{
					do{
						fileWriter.write(lineOne);
						fileWriter.append('\n');
					}while((lineOne = bufferedReaderOne.readLine())!=null);
				}
				else if(lineonenull==true)
				{
					do{
						fileWriter.write(lineTwo);
						fileWriter.append('\n');
					}while((lineTwo = bufferedReaderTwo.readLine())!=null);
					
				}
				fileOne.delete();
				fileTwo.delete();
				fileWriter.flush();
				fileWriter.close();
				bufferedReaderOne.close();
				bufferedReaderTwo.close();
	    	}
	    	catch(Exception E)
	    	{
	    		System.out.print(E.getMessage());
	    	}
			//StringBuffer stringBuffer = new StringBuffer();
	    	
	    	
	    	
	    }
	 

}
