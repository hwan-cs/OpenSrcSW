package dsa.OpenSrcSW;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;

public class indexer 
{
	private static DecimalFormat df2 = new DecimalFormat("#.##");
	@SuppressWarnings({"rawtypes", "unchecked", "nls"}) 
	public static void Indexer() throws ParserConfigurationException, FileNotFoundException, TransformerException, IOException, ClassNotFoundException
	{
		FileOutputStream fileStream = new FileOutputStream("index.post");
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileStream);
		
		ArrayList<String> words = new ArrayList<String>();
		ArrayList<Integer> occurance = new ArrayList<Integer>();
		
		String [] content = new String[5];
		for(int i =0;i<content.length;i++)
			content[i] = "";
		try 
		{
		    BufferedReader in = new BufferedReader(new FileReader("index.xml"));
		    String str = "";
		    int count = 0;
		    while(str != null)
		    {
		    	if(count==5)
		    		break;
			    if((str = in.readLine()).contains("body")) 
				{
				    	content[count] +=str;
				    	count++;
				}
		    }
		    in.close();
		} catch (IOException e) {
		}
		for(int i =0;i<content.length;i++)
			content[i] = content[i].substring(content[i].indexOf("<body>")+6, content[i].indexOf("</body>"));
		
		//init KeyWord Extractor
		int wordIndex = 0;
		int numIndex = 0;
		int [] numCount = new int [5];
		for(int i=0;i<numCount.length;i++)
			numCount[i]=0;
		
		for(int i =0;i<content.length;i++)
		{ 
			numIndex=0;
			wordIndex=0;
			while(true)
			{
				String str = "";
				while(!(content[i].substring(wordIndex,(wordIndex+1)).equals(":")))
				{
					str += content[i].substring(wordIndex,(wordIndex+1));
					wordIndex++;
					numIndex = wordIndex;
				}
				words.add(str);
				numIndex++;
				if(numIndex == content[i].length()-1|| wordIndex == content[i].length())
				{
					occurance.add(Integer.parseInt(content[i].substring(numIndex,(numIndex+1))));
					break;
				}
				
				str = "";
				while(Character.isDigit(content[i].substring(numIndex,(numIndex+1)).charAt(0)))
				{
					str+= content[i].substring(numIndex,(numIndex+1));
					numIndex++;
					wordIndex = numIndex;
				}
				occurance.add(Integer.parseInt(str));
				wordIndex++;
				if(numIndex == content[i].length() || wordIndex == content[i].length()-1)
					break;
			}
		}
		ArrayList <String> id0 = new ArrayList(words.subList(0, 137));
		ArrayList <String> id1 = new ArrayList(words.subList(137, 311));
		ArrayList <String> id2 = new ArrayList(words.subList(311, 402));
		ArrayList <String> id3 = new ArrayList(words.subList(402, 491));
		ArrayList <String> id4 = new ArrayList(words.subList(491, 586));
		HashMap hashMap = new HashMap();
		for(int j =0;j<words.size();j++)
		{
			String val = "";
			if(Collections.frequency(id0, words.get(j)) > 0)
			{
				int temp = 1;
				if(Collections.frequency(id1, words.get(j))>0)
					temp++;
				if(Collections.frequency(id2, words.get(j))>0)
					temp++;
				if(Collections.frequency(id3, words.get(j))>0)
					temp++;
				if(Collections.frequency(id4, words.get(j))>0)
					temp++;
				val += " 0 " + df2.format(occurance.get(id0.indexOf(words.get(j)))*Math.log((5.0/(double)temp)));
			}
			if(Collections.frequency(id1, words.get(j)) != 0)
			{
				int temp = 1;
				if(Collections.frequency(id0, words.get(j))>0)
					temp++;
				if(Collections.frequency(id2, words.get(j))>0)
					temp++;
				if(Collections.frequency(id3, words.get(j))>0)
					temp++;
				if(Collections.frequency(id4, words.get(j))>0)
					temp++;
				val += " 1 " + df2.format(occurance.get(137+id1.indexOf(words.get(j)))*Math.log((5.0/(double)temp)));
			}
			if(Collections.frequency(id2, words.get(j)) != 0)
			{
				int temp = 1;
				if(Collections.frequency(id1, words.get(j))>0)
					temp++;
				if(Collections.frequency(id0, words.get(j))>0)
					temp++;
				if(Collections.frequency(id3, words.get(j))>0)
					temp++;
				if(Collections.frequency(id4, words.get(j))>0)
					temp++;
				val += " 2 " + df2.format(occurance.get(311+ id2.indexOf(words.get(j)))*Math.log((5.0/(double)temp)));
			}
			if(Collections.frequency(id3, words.get(j)) != 0)
			{
				int temp = 1;
				if(Collections.frequency(id1, words.get(j))>0)
					temp++;
				if(Collections.frequency(id2, words.get(j))>0)
					temp++;
				if(Collections.frequency(id0, words.get(j))>0)
					temp++;
				if(Collections.frequency(id4, words.get(j))>0)
					temp++;
				val += " 3 " + df2.format(occurance.get(402+id3.indexOf(words.get(j)))*Math.log((5.0/(double)temp)));
			}
			if(Collections.frequency(id4, words.get(j)) != 0)
			{
				int temp = 1;
				if(Collections.frequency(id1, words.get(j))>0)
					temp++;
				if(Collections.frequency(id2, words.get(j))>0)
					temp++;
				if(Collections.frequency(id3, words.get(j))>0)
					temp++;
				if(Collections.frequency(id0, words.get(j))>0)
					temp++;
				val += " 4 " + df2.format(occurance.get(491+id4.indexOf(words.get(j)))*Math.log((5.0/(double)temp)));
			}
			hashMap.put(words.get(j), val);
		}
		
		objectOutputStream.writeObject(hashMap);
		objectOutputStream.close();
		
		FileInputStream fileInputStream = new FileInputStream("index.post");
		ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
		
		Object object = objectInputStream.readObject();
		objectInputStream.close();
		
		System.out.println("읽어온 객체의 type: "+object.getClass());
		
		HashMap hMap = (HashMap)object;
		Iterator<String> it = hMap.keySet().iterator();
		
		while(it.hasNext())
		{
			String key = it.next();
			String value = (String)hMap.get(key);
			System.out.println(key + "-> "+ value);
		}
	}
	public static void CalcSim() throws FileNotFoundException, IOException, ClassNotFoundException
	{
		ArrayList<Integer> queryCnt = new ArrayList<Integer>();
		ArrayList<String> queryWord = new ArrayList<String>();
		double [] queryResult = new double [5];
		System.out.println("Query를 입력해주세요: ");
		Scanner keyboard = new Scanner(System.in);
		String str = keyboard.nextLine();
		//init KeyWord Extractor
		KeywordExtractor ke = new KeywordExtractor();
		//extract keywords
		KeywordList kl = ke.extractKeyword(str, true);
		//print result
		for(int j = 0;j<kl.size();j++)
		{
			Keyword kWord = kl.get(j);
			queryCnt.add(kWord.getCnt());
			queryWord.add(kWord.getString());
		}
		FileInputStream fileInputStream = new FileInputStream("index.post");
		ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
		
		Object object = objectInputStream.readObject();
		objectInputStream.close();
		
		HashMap hMap = (HashMap)object;
		Iterator<String> it = hMap.keySet().iterator();
		
		ArrayList<String> words = new ArrayList<String>();
		
		while(it.hasNext())
		{
			String key = it.next();
			words.add(key);
			if(queryWord.contains(key))
			{
				for(int i=0;i<((String)hMap.get(key)).length();i++)
				{
					if(((String)hMap.get(key)).substring(i,i+1).equals(" "))
					{
						int index = Integer.parseInt((((String) hMap.get(key)).substring(i+1,i+2)));
						double result =0;
						String foo="";
						while(((i+3)<(((String)hMap.get(key)).length()))&&!(((String)hMap.get(key)).substring(i+3,i+4).equals(" ")))
						{
							foo+=((String)hMap.get(key)).substring(i+3,i+4);
							i++;
						}
						result += ((double)queryCnt.get(queryWord.indexOf(key)))*Double.parseDouble(foo);
						queryResult[index]+=result;
					}
				}
				
			}
		}
		double id0 = 0,id1=0,id2=0,id3=0,id4=0;
		for(int i=0;i<queryResult.length;i++)
		{
			if(i==0) id0=queryResult[i];
			if(i==1) id1=queryResult[i];
			if(i==2) id2=queryResult[i];
			if(i==3) id3=queryResult[i];
			if(i==4) id4=queryResult[i];
		}
		Arrays.sort(queryResult);
		System.out.println(queryResult.length);
		for(int i = queryResult.length-1;i>1;i--)
		{
			if(queryResult[i]==0) return;
			if(queryResult[i]==id0) System.out.println((5-i)+". 떡.html: "+df2.format(id0));
			if(queryResult[i]==id1) System.out.println((5-i)+". 라면.html: "+df2.format(id1));
			if(queryResult[i]==id2) System.out.println((5-i)+". 아이스크림.html: "+df2.format(id2));
			if(queryResult[i]==id3) System.out.println((5-i)+". 초밥.html: "+df2.format(id3));
			if(queryResult[i]==id4) System.out.println((5-i)+". 파스타.html: "+df2.format(id4));
		}
	}
}
