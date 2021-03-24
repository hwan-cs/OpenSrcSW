package dsa.OpenSrcSW;

import java.io.BufferedReader;

import java.util.List;
import org.snu.ids.kkma.ma.MExpression;
import org.snu.ids.kkma.ma.MorphemeAnalyzer;
import org.snu.ids.kkma.ma.Sentence;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.jsoup.Jsoup;
public class makeCollection 
{
	public static int count = 0;
	
	public static Document HTMLTOXML(String [] html) throws ParserConfigurationException
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
			String str = document.body().text(); 
			body.appendChild(doc.createTextNode(str));
			docId.appendChild(body);
		}
		return doc;
	}

}
