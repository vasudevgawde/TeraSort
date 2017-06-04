
import java.io.IOException;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class HadoopSort {

	  public static class SortMapper
	       extends Mapper<Object, Text, Text, Text>{

	    public void map(Object key, Text value, Context context
	                    ) throws IOException, InterruptedException {
	      String first = value.toString().substring(0, 10);
	      String second = value.toString().substring(10);
	      
	      context.write(new Text(first), new Text(second));
	      
	    }
	  }

	  public static class SortReducer
	       extends Reducer<Text,Text,Text,Text> {

	    public void reduce(Text key, Text values,
	                       Context context
	                       ) throws IOException, InterruptedException {
	    
	      context.write(new Text(key.toString()+values.toString()),new Text(""));
	    }
	  }

	  public static void main(String[] args) throws Exception {
		  
	    Configuration conf = new Configuration();
	    conf.set("mapred.textoutputformat.separator", ""); 
	    Job job = new Job(conf,"HadoopSort");
	    
	    job.setJarByClass(HadoopSort.class);
	    job.setMapperClass(SortMapper.class);
	    job.setCombinerClass(SortReducer.class);
	    job.setReducerClass(SortReducer.class);
	    
	    job.setInputFormatClass(TextInputFormat.class);
	 
	    //regarding output
	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(Text.class);
	    FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    
	   
	    System.exit(job.waitForCompletion(true) ? 0 : 1);
	  }
	}
