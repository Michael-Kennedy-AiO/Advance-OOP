package utility;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;







	
	
	

public class NameGen {
	
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test void test() throws IOException, InterruptedException {
//		DBString dbString=new DBString(PATH.TEST_FOLDER+"/NameGen");
		
//		PrintWriter printWriter = new PrintWriter ("C:\\Devtools\\Github\\RichardLibrray\\BackendTemplate\\src\\test\\java\\utility\\EnglishName.java");
//		
//		printWriter.println("package utility;\n");
//		printWriter.println("import java.util.Random;\n");
//		printWriter.println("public class EnglishName {");
//		printWriter.println("	private static Random random=new Random();");
//		printWriter.println("	private static final int numberName="+dbString.rfBTree.length()/8+";");
//		printWriter.print("	private static final String[] listName = new String[] {");
//		printWriter.print("\""+dbString.findStringByIndex(0)+"\"");
//		for(int i=1;i<dbString.rfBTree.length()/8;i++)
//			if(dbString.findStringByIndex(i).contains(" ")==false)
//				printWriter.print(",\""+dbString.findStringByIndex(i)+"\"");
//		printWriter.println("};");
//		printWriter.println("	public String random() {return listName[random.nextInt(numberName)];}");
//		printWriter.println("}");
//		printWriter.close();
//		
//		System.out.println("Finish");
		
		
		
		
		Elements listElements;
		while(true) {
			listElements = Jsoup.connect("https://www.behindthename.com/random/random.php?gender=both&number=1&sets=5&surname=&usage_eng=1").get().getElementsByClass("random-results");
			if(listElements!=null && listElements.size()>0)
				for(int i=0;i<listElements.size();i++) {
					System.out.println(listElements.get(i).text());
//					dbString.getOffset(listElements.get(i).text());
				}
			Thread.sleep(2000);
		}
	}
	
	
	
//	public static final String getName() {
//		try {
//			String s = null;
//			while (s == null || s.equals("null"))
////				s = Jsoup.connect("https://www.behindthename.com/random/random.php?number=4&sets=1&gender=both&surname=&usage_eng=1").get().getElementsByClass("random-results").get(0).text().replaceAll(" ", "").replaceAll("'", "").replaceAll("`", "");
////				s = Jsoup.connect("https://www.behindthename.com/random/random.php?number=4&sets=1&gender=both&surname=&usage_eng=1").get().getElementsByClass("random-results").get(0).text();
//				s = Jsoup.connect("https://www.behindthename.com/random/random.php?gender=both&number=1&sets=5&surname=&usage_eng=1").get().getElementsByClass("random-results").get(0).text();
//			return s;
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
}
