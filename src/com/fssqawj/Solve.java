package com.fssqawj;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Solve {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File trainFile = new File("spam_train.txt");
		File stopFile = new File("stop_word.txt");
		//Set<String>iSet = new HashSet<String>();
		Map<String, Integer> iMapBad = new HashMap<String, Integer>();
		Map<String, Integer> iMapGood = new HashMap<String, Integer>();
		List<String>stopword = new ArrayList<String>();
		InputStreamReader reader;
		BufferedReader br;
		String temp = "";
		
		reader = new InputStreamReader(new FileInputStream(stopFile),"utf-8");
		br = new BufferedReader(reader);
		
		while((temp = br.readLine()) != null){
			stopword.add(temp);
		}
		
		
		
		
		reader = new InputStreamReader(new FileInputStream(trainFile),"utf-8");
		br = new BufferedReader(reader);
		
		
		int badMail = 0,goodMail = 0;
		int allMail = 0;
		
		
		OutputStreamWriter writer;
		writer = new OutputStreamWriter(new FileOutputStream("dump"),"utf-8");
		

			
			
		while((temp = br.readLine()) != null){
			String[] tem = temp.split(" ");
			if(tem[0].contains("0")){
				badMail ++;
				Set<String> iSet = new HashSet<String>();
				for(int i = 1;i < tem.length;i ++){
					String word = tem[i];
					if(iSet.contains(word) || stopword.contains(word))continue;
					iSet.add(word);
					if(iMapBad.containsKey(word)){
						iMapBad.put(word, iMapBad.get(word) + 1);
					}
					else iMapBad.put(word, 1);
				}
			}
			else {
				goodMail ++;
				Set<String> iSet = new HashSet<String>();
				for(int i = 1;i < tem.length;i ++){
					String word = tem[i];
					if(iSet.contains(word) || stopword.contains(word))continue;
					iSet.add(word);
					if(iMapGood.containsKey(word)){
						iMapGood.put(word, iMapGood.get(word) + 1);
					}
					else iMapGood.put(word, 1);
				}
			}
			allMail ++;
		}
		
		//System.out.println(badMail + " " + goodMail + " " + allMail);
		br.close();
		reader.close();
		
		File testFile = new File("spam_test.txt");
		reader = new InputStreamReader(new FileInputStream(testFile),"utf-8");
		br = new BufferedReader(reader);
		int hit = 0;
		int allTest = 0;
		while((temp = br.readLine()) != null){
			String[] tem = temp.split(" ");
			int catg = Integer.parseInt(tem[0]);
			double badsorce = 1,goodsorce = 1;
			Set<String> iSet = new HashSet<String>();
			int max_l = 0;
			for(int i = 1;i < tem.length;i ++){
				String word = tem[i];
				//max_l = Math.max(max_l, word.length());
				//if(word.length() > 40)max_l ++;
				if(iSet.contains(word) || stopword.contains(word))continue;
				iSet.add(word);
				int badcnt = 0,goodcnt = 0;
				if(iMapBad.containsKey(word)){
					badcnt = iMapBad.get(word);
				}
				
				if(iMapGood.containsKey(word)){
					goodcnt = iMapGood.get(word);
				}
				
				//badsorce += Math.log((1.0 * badcnt + 1) / (badMail + 1));
				//goodsorce += Math.log((1.0 * goodcnt + 1) / (goodMail + 1));
				
				badsorce *= (1.0 * badcnt + 1) / (badMail + 1);
				goodsorce *= (1.0 * goodcnt + 1) / (goodMail + 1);
				
			}
			//badsorce += Math.log(1.0 * badMail / allMail);
			//goodsorce += Math.log(1.0 * goodMail / allMail);
			
			badsorce *= 1.0 * badMail / allMail;
			goodsorce *= 1.0 * goodMail / allMail;
			int pre = 0;
			if(goodsorce > badsorce)pre = 1;
			//if(max_l >= 20)pre = 1;
			if(catg == pre)hit ++;
			else {
				writer.write(pre + " " + temp + "\n");
			}
			
			allTest ++;
		}
		
		System.out.println("all = " + allTest + " hit = " + hit);
		
		br.close();
		reader.close();
		writer.close();
	}

}



