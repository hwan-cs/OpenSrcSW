package dsa.OpenSrcSW;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;

public class kuir 
{
	private static DecimalFormat df2 = new DecimalFormat("#.##");
	@SuppressWarnings({"rawtypes", "unchecked", "nls"})
	public static void main(String[] args) throws ParserConfigurationException, FileNotFoundException, TransformerException, IOException, ClassNotFoundException
	{
		// TODO Auto-generated method stub
		String [] listhtmml = {"떡.html", "라면.html", "아이스크림.html", "초밥.html","파스타.html"};;
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		Document kkmaxml = makeKeyword.KKMAXML(listhtmml);
		DOMSource source = new DOMSource(kkmaxml);
		StreamResult result = new StreamResult(new FileOutputStream(new File("index.xml")));
		transformer.transform(source, result);
		
		Document htmlxml = makeCollection.HTMLTOXML(listhtmml);
		DOMSource source2 = new DOMSource(htmlxml);
		StreamResult result2 = new StreamResult(new FileOutputStream(new File("collection.xml")));
		
		transformer.transform(source2, result2);
		
		indexer.Indexer();
		indexer.CalcSim();
	}
}
