package dsa.OpenSrcSW;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;

public class searcher 
{
	private static DecimalFormat df2 = new DecimalFormat("#.##");
	public static void InnerProduct() throws FileNotFoundException, IOException, ClassNotFoundException
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
		
		double cosA = 0.0;
		double cosB = 0.0;
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
						cosA += Math.pow(((double)queryCnt.get(queryWord.indexOf(key))), 2);
						cosB += Math.pow(Double.parseDouble(foo), 2);
						queryResult[index]+=result;
					}
				}
				
			}
		}
		double id0 = 0,id1=0,id2=0,id3=0,id4=0;
		for(int i=0;i<queryResult.length;i++)
		{
			queryResult[i]=queryResult[i]/(Math.sqrt(cosA)*Math.sqrt(cosB));
		}
		for(int i=0;i<queryResult.length;i++)
		{
			if(i==0) id0=queryResult[i];
			if(i==1) id1=queryResult[i];
			if(i==2) id2=queryResult[i];
			if(i==3) id3=queryResult[i];
			if(i==4) id4=queryResult[i];
		}
		Arrays.sort(queryResult);
		int index =0;
		String [] listhtmml = {"떡.html", "라면.html", "아이스크림.html", "초밥.html","파스타.html"};
		for(int i = queryResult.length-1;i>1;i--)
		{
			if(queryResult[i]==0) 
			{
				for(int j=0;j<3;j++)
				{
					if(index<5&&((5-i+j)<4))
					{
						System.out.println((5-i+j)+". "+listhtmml[index]+": 0.0");
						index++;
					}
					else
						return;
				}
			}
			if(queryResult[i]==id0) 
			{
				System.out.println((5-i)+". 떡.html: "+df2.format(id0));
				id0=-1.0;
				index=1;
			}
			else if(queryResult[i]==id1) 
			{
				System.out.println((5-i)+". 라면.html: "+df2.format(id1));
				id1=-1.0;
				index=2;
			}
			else if(queryResult[i]==id2) 
			{
				System.out.println((5-i)+". 아이스크림.html: "+df2.format(id2));
				id2=-1.0;
				index=3;
			}
			else if(queryResult[i]==id3) 
			{
				System.out.println((5-i)+". 초밥.html: "+df2.format(id3));
				id3=-1.0;
				index=4;
			}
			else if(queryResult[i]==id4) 
			{
				System.out.println((5-i)+". 파스타.html: "+df2.format(id4));
				id4=-1.0;
				index=5;
			}
		}
	}
}