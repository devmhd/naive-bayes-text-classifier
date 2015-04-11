import java.util.ArrayList;
import java.util.HashMap;


public class G {
	
	public static final double SMALL_PROBABILITY = 0.00000000000001;
	
	
	
	
	public static HashMap<String, Integer> topicsMap;
	public static String[] topics;
	
	public static ArrayList<Article> trainingArticles;
	
	public static ArrayList<HashMap<String, Integer>> topicFrequency;
	
	public static int[] articleCountInTopic;
	
	public static int trainingArticleCount = 0;
	public static int testArticleCount = 0;
	
	public static int[] wordCountInTopic;
	

}
