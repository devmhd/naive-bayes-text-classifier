import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;


public class Parsers {

	public static void readTopicNames(){
		
		G.topicsMap = new HashMap<String, Integer>();
		G.topics = new String[135];
		
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
		
		G.trainingArticles = new ArrayList<Article>();
		
		
		
		
		FileInputStream fstream;
		BufferedReader br;
		try {
			
			
			fstream = new FileInputStream("training.data");
			br = new BufferedReader(new InputStreamReader(fstream));
			

			String line;
			
			int lineCount = 0;
			int articleCount = 0;
			
			Article currentArticle = null;
			
			
			int expecting = 0;

			while ((line = br.readLine()) != null){
				
				System.out.print("" + lineCount++ + ": ");
				
				System.out.print("" + expecting + " ");
				System.out.println(line);
				
				
				if(line.isEmpty()){
					System.out.println("<<<<EMPTY>>>>>");
					if(expecting == 4){
						
						expecting = 0;
						G.trainingArticles.add(currentArticle);
						System.out.println("Article added: " + G.trainingArticles.size());
						
					}
					continue;
					
				}
				
				if(expecting == 0){
					
					System.out.print("Topic: " + line);
					
					Integer topicIndex = G.topicsMap.get(line);
					
					System.out.println(" Index: " + topicIndex);
					
					currentArticle = new Article(topicIndex);
					expecting = 1;
					continue;
				  
				}
				
				if(expecting == 1){
					
					currentArticle.setTitle(line);
					expecting = 2;
					continue;
				}
				
				if(expecting == 2){
					
					expecting = 3;
					continue;
				}
				
				if(expecting == 3){
					
					currentArticle.addLine(line);
					expecting = 4;
					continue;
				}
				
				if(expecting == 4){
					
					currentArticle.addLine(line);
					continue;
				}
			
			
			 
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
	
		
	
	
}
