import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;


public class Parsers {

	public static void readTopicNames(){

		G.topicsMap = new HashMap<String, Integer>();
		G.topics = new String[135];

		G.topicFrequency = new ArrayList<HashMap<String, Integer>>();

		FileInputStream fstream;
		BufferedReader br;
		try {


			fstream = new FileInputStream("topics.data");
			br = new BufferedReader(new InputStreamReader(fstream));


			String line;

			int topicsCount = 0;

			while ((line = br.readLine()) != null)   {


				G.topicsMap.put(line, new Integer(topicsCount));


				G.topics[topicsCount] = line;

				topicsCount++;

				G.topicFrequency.add(new HashMap<String, Integer>());

			}



			br.close();

			System.out.println("## Done loading " + G.topicsMap.size() + " topics");


		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//for(int i = 0; i< G.topicsMap.size(); ++i){

		System.out.println(G.topicsMap.toString());
		//}




	}







	public static void readTrainingArticles(){

		FileInputStream fstream;
		BufferedReader br;
		try {


			fstream = new FileInputStream("training.data");
			br = new BufferedReader(new InputStreamReader(fstream));


			String line;



			int currentTopicIndex = -1;




			G.articleCountInTopic = new int[135];
			G.wordCountInTopic = new int[135];

			while ((line = br.readLine()) != null){


				if(line.isEmpty()){
					continue;		
				}

				else if(   !(line.contains(" "))  && G.topicsMap.containsKey(line)   ){

					currentTopicIndex = G.topicsMap.get(line);
					G.trainingArticleCount++;

					G.articleCountInTopic[currentTopicIndex]++;


				} else {

					String[] words = line.split("[;, \\.]");
					
					G.wordCountInTopic[currentTopicIndex] += words.length;

					for(int i=0; i<words.length; ++i){

						if(G.topicFrequency.get(currentTopicIndex).containsKey(words[i])){

							G.topicFrequency.get(currentTopicIndex).put(words[i], G.topicFrequency.get(currentTopicIndex).get(words[i])+ 1 );
						} else {

							G.topicFrequency.get(currentTopicIndex).put(words[i], new Integer(1));

						}
					}


				}





			}

			br.close();



		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}


		for(int i=0; i<135; ++i)

			if(G.topicFrequency.get(i).size()!=0)
				System.out.println("" + G.topics[i] + " Size: " + G.topicFrequency.get(i).size());

	}


	
	
	
	public static void readTestArticles(){

		
		double[] topicProbabilities = new double[135];
		
		FileInputStream fstream;
		BufferedReader br;
		try {


			fstream = new FileInputStream("test.data");
			br = new BufferedReader(new InputStreamReader(fstream));


			String line;



			int realTopicIndex = -1;

			
			
			
			HashMap<String, Integer> currentArticleFrequency = new HashMap<String, Integer>();


			G.articleCountInTopic = new int[135];

			while ((line = br.readLine()) != null){


				if(line.isEmpty()){
					continue;		
				}

				else if(   !(line.contains(" "))  && G.topicsMap.containsKey(line)   ){

					
					double maxProb = 0.0, iMax = -1;
					
		//			System.out.println("topic probabilities " + topicProbabilities.toString());
					
					
					for(int i=0; i<135; ++i){
						
	//					System.out.println("Prob" + i + " : " + topicProbabilities[i]);
						
						if(topicProbabilities[i] > maxProb){
							maxProb = topicProbabilities[i];
							iMax = i;
						}
						
					}
					
					System.out.println("imax: " + iMax + " real: " + realTopicIndex );
					
					//if(iMax == realTopicIndex) System.out.println("Correct");
					//else System.out.println("wrong");
					
					
					realTopicIndex = G.topicsMap.get(line);
					G.testArticleCount++;
					
					currentArticleFrequency.clear();
								
					for(int j=0; j<135; ++j) topicProbabilities[j] = 1.0;
					
					


				} else {

					String[] words = line.split("[;, \\.]");

					for(int i=0; i<words.length; ++i){
						
//						for(int j=0; j<135; ++j) topicProbabilities[j] = 1.0;
						
						for(int topic=0; topic<G.topics.length; ++topic){
							
							topicProbabilities[topic] *= getWordProbabilityInTopic(words[i], topic);
								
						}
						
					}
					
					for(int topic=0; topic<G.topics.length; ++topic){
						
						topicProbabilities[topic] *= getTopicPriorProbability(topic);
							
					}
					
					


				}





			}

			br.close();



		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}


	}
	
	
	public static double getTopicPriorProbability(int topicIndex){
		
		return (double) G.articleCountInTopic[topicIndex] / (double) G.trainingArticleCount;
		
	}
	
	public static double getWordProbabilityInTopic(String word, int topicIndex){
		
		
		if(G.topicFrequency.get(topicIndex).containsKey(word)){
			
			return (double)(G.topicFrequency.get(topicIndex).get(word)) / (double) G.wordCountInTopic[topicIndex];
			
		} else {
			
			return G.SMALL_PROBABILITY;
		}
		
		
	}
	


	//		 

}

