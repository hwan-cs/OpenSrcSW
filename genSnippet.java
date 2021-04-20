package dsa.OpenSrcSW;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.jsoup.Jsoup;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class genSnippet 
{
	public static void main(String[] args) throws ParserConfigurationException, FileNotFoundException, TransformerException, IOException, ClassNotFoundException
	{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		ArrayList<String> word = new ArrayList<String>();
		int size=0;
		String content = "";
		try 
		{
		    BufferedReader in = new BufferedReader(new FileReader("input.txt"));
		    String str;
		    while ((str = in.readLine()) != null) 
			{
		    	content +=str;
		    	size++;
		    	word.add(str);

			}
		    in.close();
		}
		catch (IOException e) {
			}
		
		Scanner keyboard = new Scanner(System.in);
		int [] frequency = new int[word.size()];
		for(int i=0;i<frequency.length;i++)
			frequency[i]=0;
		System.out.println("키워드를 입력하세요");
		String input =keyboard.nextLine();
		for(int i=0;i<word.size();i++)
		{
			int num = 0;
			String str ="";
			for(int j=0;j<word.get(i).length();j++)
			{
				if(word.get(i).substring(j,j+1).equals(" "))
				{
					if(word.get(i).contains(str))
					{
						frequency[i]++;
					}
					str="";
				}
				else
				{
					str+=word.get(i).substring(j,j+1);
				}
			}
		}
		int highest =0;
		for(int i=0;i<frequency.length;i++)
		{
			if(frequency[i]>=frequency[highest])
				highest=i;
		}
		//키워드가 가장 많이 포함된 라인을 출
		System.out.println(word.get(highest));
	}

}

