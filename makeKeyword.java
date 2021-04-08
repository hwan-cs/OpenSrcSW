package dsa.OpenSrcSW;

import java.io.BufferedReader;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import java.io.FileReader;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.jsoup.Jsoup;

public class makeKeyword 
{
	public static int count = 0;
	
	public static Document KKMAXML(String [] html) throws ParserConfigurationException
	{
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		
		Document doc = docBuilder.newDocument();
		Element docs = doc.createElement("docs");
		doc.appendChild(docs);
		
		for (int i =0;i <html.length;i++)
		{
			Element docId = doc.createElement("doc");
			docs.appendChild(docId);
			
			docId.setAttribute("id", Integer.toString(i));
			Element title = doc.createElement("title");
			title.appendChild(doc.createTextNode(html[i].substring(0, html[i].indexOf("."))));
			docId.appendChild(title);
			
			Element body = doc.createElement("body");
			
			String content = "";
			try 
			{
			    BufferedReader in = new BufferedReader(new FileReader(html[i]));
			    String str;
			    while ((str = in.readLine()) != null) 
				{
				    	content +=str;
				}
			    in.close();
			} catch (IOException e) {
			}
			org.jsoup.nodes.Document document = Jsoup.parse(content);
			org.jsoup.nodes.Element elements = document.select("p").first();  
			String str = document.body().text(); 
			String bar = "";
			//init KeyWord Extractor
			KeywordExtractor ke = new KeywordExtractor();
			//extract keywords
			KeywordList kl = ke.extractKeyword(str, true);
			//print result
			for(int j = 0;j<kl.size();j++)
			{
				Keyword kWord = kl.get(j);
				if(j==0)
					bar += kWord.getString()+":"+kWord.getCnt();
				else
					bar += "#"+kWord.getString()+":"+kWord.getCnt();
			}
			body.appendChild(doc.createTextNode(bar));
			docId.appendChild(body);
		}
		return doc;
	}
}
