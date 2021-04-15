import java.io.IOException;
import java.util.StringTokenizer;
import java.util.HashMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class InvertedIndex {

	public static class TokenizerMapper extends Mapper<Object, Text, Text, Text> {

		private Text word = new Text();
		// private Text file = new Text();
        private String filePath = new String();
        private String folder = new String();
        private String doc = new String();

		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

			//replace everything that is not a number or letters
			StringTokenizer itr = new StringTokenizer(value.toString().replaceAll("[^a-zA-Z0-9\\s]", ""));

            filePath = ((FileSplit)context.getInputSplit()).getPath().toString();
            String [] arr = filePath.split("/");
            doc = arr[arr.length-1];
            folder = arr[arr.length-2];

			while (itr.hasMoreTokens()) {
				word.set(itr.nextToken());
				context.write(word, new Text(folder + ":" + doc));
			}
		}
	}

	public static class IntSumReducer extends Reducer<Text, Text, Text, Text> {
		

		public void reduce(Text key, Iterable<Text> values, Context context)
				throws IOException, InterruptedException {
			
			HashMap<String, Integer> result = new HashMap<String, Integer>();
			for (Text val : values) {
				if(result.containsKey(val.toString())) {
					result.put(val.toString(), result.get(val.toString())+1);
				}
				else {
					result.put(val.toString(), 1);
				}
			}
			for (HashMap.Entry<String,Integer> entry : result.entrySet()) {
				String hKey = entry.getKey();
				Integer hValue = entry.getValue();
	            context.write(key, new Text(hKey + ":" + hValue));
	        }
		}
	}

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "inverted index");
		job.setJarByClass(InvertedIndex.class);
		job.setMapperClass(TokenizerMapper.class);

		// job.setCombinerClass(IntSumReducer.class);

		job.setReducerClass(IntSumReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}