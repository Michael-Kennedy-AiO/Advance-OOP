package database_game.table;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import backendgame.com.core.BGUtility;
import database_game.AccountType;
import database_game.DatabaseManager;
import server.config.PATH;

class DBGame_AccountLoginTest {
	private DBGame_AccountLogin dbAccount;
	private DBGame_UserData dbUserData;
	private static final short DBId=10000;
	private File folder;
	public int count=0;
	private void add(String hash,int range) throws IOException {
		dbAccount.insertAccount(hash, (byte) range, "", dbUserData);
		count++;
	}

	@Test void test() throws IOException {
		folder=new File(PATH.DATABASE_FOLDER+"/"+DBId);
		if(folder.exists()==false)
			folder.mkdirs();
		DBGame_AccountLogin.databaseManager=DatabaseManager.gI();
		dbAccount=new DBGame_AccountLogin(DBId);
		dbUserData=new DBGame_UserData(DBId);
		dbUserData.initNewDatabase(null);
		add("WHSqeFoUxi",5);add("X861nqcXvmaAS",3);add("aT6lH1U7BTxuwTdYAN",0);add("ofHiZkkYFIQ",1);add("BbuQRDX3DVp6L",2);add("ZYvJAmk7zhsGte",1);add("SkLrQfG9vaCaOfQd8",6);add("qL0Cq4umhKuuAz",5);add("Tdm8rEkEy3kzopFut",5);add("MyhIsNdprxgvzu6w5K",2);add("3WHeZUoss3Pk9FKl9as",0);add("3t1vLYNr8RvAiY",1);add("sRPGDZsOUo",6);add("Aga6uyJqle",6);add("OWbMCBcNhJ0DZ7",1);add("zvYCI8QiXbtmvNWll9",5);add("3BS315hQ5nzkFSump",6);add("GUUlzGs5tbzHivWE",1);add("qSvlvPRwgo3",6);add("19R2wvF8HFP4GrpH7dH",5);add("3fefV9zDEiwR6",5);add("CHdHhqC1X8Yy",1);add("hdTufDdBWgd2vdTf2",5);add("wJDqg6RBqZ",3);add("o8iUlqs7maI",4);add("AtfKskXYkGo",3);add("jEiSthPBd65E9",3);add("NznpK8fImKlHfgfvQ",5);add("gH4Skfy0YJozIFe3yr",0);add("MndUttZGYKxG",5);add("UhFcWO2S3mOCJ32G",3);add("PKGJmPcZjYAQDnogD7",4);add("zsTeGrrvN5",3);add("LHQufz876pykyMKnU5b",6);add("7sQxeCiywhA",4);add("H34Zq1YPhuzi0wPU7j",3);add("xlazrpcs3FX",4);add("VjdWpvwD55CIi4jlaD",3);add("nvUvC8IwLUTrDzzJiKW",4);add("FRlgzsrkUvMP",2);add("4yxBwxG1EKMBYAGOQZ",1);add("dgTreOehNHQDR354qCQ",5);add("vXEdGBrcnuwM5Ow",1);add("62qCCSnSRi7gi7d8FqW",2);add("6hi2EAogRwTXKTaY",0);add("KSVWTd1NWT",4);add("5vf6tk0rDjI",3);add("5Kkw4oiw5JDSdttF",6);add("KAyVWmoKrpm3Pt",1);add("1VtshshfOYaDlcJZ6tA",4);add("CVdmwdUeMxyA7lR",2);add("Dương Đức Trí",0);add("mIwXmEZS62Ho",4);add("rMWEBTWYWo7MV94c52d",0);add("3Z4RcW198hQMDB",4);add("818g8KHE84AQIWi",4);add("u3VXaZW91wO",0);add("TL92mV5N4tWKUnHtxO",1);add("XKq19xO0Mg1",5);add("ukm9UKGyARVpa4Yb8",2);add("Uy4GuE8P5O5T",1);add("KdArKNT2nsdViZz",3);add("ioEtmFzS2W",3);add("039RGK1Vm65nFCw",5);add("PlGa7fF7WHS",3);add("EOMuW8uceku7XaPGuVT",6);add("bv4D5EQitz4LgA",3);add("IkSEKVqyFVFTZYB",1);add("KOGTuTE0MEKcjwL1b6M",4);add("eObdlpwwTG8C",6);add("O9hD1Ki6zI",3);add("KUlzr59x5E",1);add("ej9kD4VlF9rWxF",4);add("0U5BidMEF1xjV6h",3);add("xFSlejvMkORDRH0",6);add("4z8cmtDXftgSEd",5);add("gT7RGT2NjVUPKRVqqN",2);add("rEieC8s0yoEC",3);add("tBrLRmtFrpGSscQ",4);add("KBzQ6ZeCpjP",6);add("FAVTzHkvjhwkl",3);add("xWIRpw7bxjASAgAmVE",0);add("kuUpYKmJnEUol",2);add("2ar2b2XN4yrf3buk",3);add("szgU3yamQlOF",0);add("Lv3JDCLu3sQJ",0);add("c4psmZcJQaYTPDLN",6);add("oFFFCOsimt",4);add("bmdNDc3YkrZVQw",0);add("SDW1BbfZNSYtdQOtQ",1);add("HNUHRPaIcs",4);add("sJlVvGUO5q",3);add("6XtOh8vwDBRKn5cGfMR",6);add("y9PbiCrJA0",3);add("iyuCh2N057fmN3DE",1);add("ixxX6ymJwRWW",4);add("LyRi9XUtfpabjs",1);add("xQvWfdrwh1SsOo",1);add("Fih0ncAaYX",3);add("sqnheWFYaE9PELhQ",3);add("Ww4XrfKclnn7BDo",6);add("ruZxu9tR01H9qrZG",4);add("OUg7Rw55Ay",6);add("Dương Đức Tiến",6);add("zZLuKrNs38",1);add("svxQfLpgcZPMlS",1);add("PkNBC3OOv3fryW",0);add("RWXqKRiXHEqK",1);add("bd7Xte6SSb5vc",5);add("P2XPqTlGqp5Q",2);add("Zy1RR19Bu7A7a",0);add("QXtWleK7TZwRal",5);add("QgFxoUcnGQiDNevY",6);add("v0AQPdAOxOMSBC",3);add("MtkYIEgTrnCWlvDiAUo",6);add("Fbo2GykRxkQO3XztJ",6);add("kAm8Zi9yYTVstxV0wGc",6);add("nQFeaJI0R1d",4);add("eoT7Dd5Tr9t",3);add("rHwBSqqJW0",3);add("CH3PeqpeVOEb",0);add("pmS8uGgFzu4gxzHf",1);add("0CT4OtYE3kU58ymCngT",3);add("HISxDm3IiHiUACrC",1);add("GZ6pzeuAw23",5);add("dtYMQ5bDZmA1I7w25Wm",2);add("p5QfKmDX8U0ckxnM",5);add("pfWey8Oowni",5);add("wy6UgDOjb3PCWbUkmh",1);add("DK1BWrmc8f",5);add("gvTpxhF9Zxns8vYS",0);add("D0U8j8BpIpbwuqf8kSe",5);add("37HtdzZQMvQGTgu",1);add("UkXyyhY4bT7pWc",5);add("FYNMTtGGV3HysYHsey",6);add("briSVmf2FWHJp",3);add("Wa3PDumpcOgPR20",3);add("Ko4jxfcNNJNNcbXWK5",1);add("IUxZ6BE3vW4JGObsiW",5);add("anh34b42xgLSo",1);add("k3UHQAopOQaMYxt5US",2);add("1mmR0rkeqi8WYl9l",4);add("7cUiNcZd2W6H0guA1",3);add("0qMZd1mRD9MG8ncP4v",4);add("eL9590j7eL7U",1);add("HdxMHs8BPgtbRwzym9",6);add("9tcqDs6uPGw4PYDZ",2);add("6kg3wSpqStKy9H",0);add("uHDfI8RFWes75qz",2);add("9rCaSjS4GUN7OYq5sbp",0);add("ODmPXp8DlDitRPzN",6);add("klShKMvgDMK4eMCr",0);add("EI159hUyPj",1);add("DE9rfdaW0OpwJSW5A",3);add("xymG3KICgSFqzo9SW",2);add("oIiEykDfI2YV4kjZNi",3);add("KC7WCVJpNsB",0);add("EQqe2d6mowSSrIviY",4);add("5xllNPFbp1xJa",0);add("CZr1a1jhO14VjPC1",2);add("Dương Đức Trí",1);add("RC1DY2DC8owo7IO5",3);add("9SobrLMXle0Z",4);add("3cLsEs2es8pTZjTFea",6);add("KiRAyu8gLGEmT",5);add("3bL9EnwUE1lHp",5);add("YzeRhYlafbYQFUvd",2);add("VqSzHC5EM7jXn4rUw",2);add("zA7YMV8hZ8lfHTJq",6);add("OZmZzrNdvGiM",2);add("qGuukrWL3R4GF",0);add("UscgN2EP7KV2yGnd",4);add("Dylm4ugODw6A6WCnOU1",5);add("P9Nn3hjp2akpRBkg0Y",3);add("yNdchzJ6OW",0);add("nXL0loifxcn07XH",0);add("pnsGXzuLJIZkj",1);add("BQwXk2y2kC",3);add("HURnuDUNdbxhqRV0",0);add("OgZ84Xeda9VTRS",5);add("AVluKdfM3ZTYopi",1);add("OZ29G85aoN7xqvKRxZ",1);add("tARJCHTmNqjqu",0);add("bPUURkR6Ut57txkc",5);add("zBv4a1hzRD",6);add("wW6K2cKTyPUBBmA",6);add("36Yo8kRsjqKPZ4vO3K",3);add("u8Rwh6nB5sPIBziCdJ",3);add("vHPyG43nOtU5xR",4);add("YFQa2gLkG6z",2);add("r4gsFeNOm8SWUTd",3);add("p35bz2V7lL4CVT",4);add("eyN1K6yHZ7M7HOqga",0);add("4FLd0A4VnhO",0);add("pq1KiQlnisY4Fq3Rzpr",3);add("VkiRNooj6I1mO9B8",0);add("Q2jIupbohvFHFb1",3);add("GHD8Z47GFS7",3);add("094smsJEBOxsjG9kG3",0);add("I7HeN6zTblEuCvxnXH",0);add("Uv9PxjLlrr",0);add("EZHlFoxOOIcw5",2);add("TBtNNzv1i70e0uaa0W",2);add("3tslQljgiuPI",5);add("05DkI8LytaFbPK4E",1);add("qAmPNFnw3H",6);add("zvwbOpHx0LOROjA",6);add("LcNzwr08ww8KtPLsT",5);add("OHEoxDeGRid",1);add("Wc4oD4NRzfLXfPbDJ",6);add("36qnWsew2sWI",6);add("xRoiDkaBJ2tzwy0cX5O",2);add("Y6UGKRHUWuklXXjBae",6);add("NhbY2ejbZBgTDRCNK",0);add("DpUyfaBTJVV",6);add("hByS3INxtbw",4);add("5143FjM4FSrY5v0vM9",2);add("owWQ8wGztfQMYnffY",0);add("zyVVcGqVermBZdRoE1",6);add("Nguyễn Thùy Dung",6);add("ZHwNP5GzntlvUQHX9f",2);add("OC3PeQyz0d1I1T5",0);add("h6iwKwitkmJGXRSCEF",6);add("kwjIAt0dLgeumrwkMp",6);add("F6qEhFavMIB",1);add("LErn1wQd7he",3);add("tTfpFrtrBFR24cj2IF",0);add("X76Q5KkNAW0",1);add("PECneLk2dXg0",6);add("zvLdyDO1zSRVy9Dv",0);add("oWdKaknGoVQs",3);add("daCyVTdWqIBGnD7ddT",6);add("uMhYStekieHa",6);add("6febkxaqvHT",6);add("fwUXCoRQqrCa",0);add("YUdelA9AFuaI0ahs2",0);add("PHEID471JuVLnbiKRb",0);add("1Y7EpdfHPdCsjGZo4",1);add("q3jV7MCEQw",4);add("f5wmUWKBdsjK",1);add("PuNbNZ17CheOt5VQ",2);add("12kqUSzY8ex",1);add("AFl2ajfafZAM",3);add("w4LBWyW9fpPmo",2);add("MzcKEDYipag2ocq",5);add("8PD6VBzJZfYeQ",5);add("eVJTEWvhmOGQtj5DB",3);add("1LHNp15yPIOz",1);add("GGHTxm7SDhEf0Rj",1);add("1G0zmvsPgkkgpeS",4);add("3idJytef1WBxd2rnb",6);add("1JU8QpNUJsFIEecQEv0",0);add("fVch4Lm3Adgmq",0);add("j3O8k84x2HR5ELn",3);add("0F3Dw2ORTXKRc",6);add("bu1teD4QeZ5Kk",4);add("EJ61L1CYELK4R3rCdSd",3);add("W01Ze0Yy19",0);add("HUO6DfSNpC",0);add("qwlUAjNrPX2",2);add("ESfeRwPxnROC",3);add("ZLXeoLHgWoAQtU5Bkw",6);add("51wseEw3lS6W9V0P5",4);add("XqiwqvbP6f",1);add("Qn1uivXWnK",2);add("5esN8G1StO3pqI6u7n",6);add("R8wG9kTeB3xxRf8by",4);add("nhMLBDDLQoPImEX",4);add("hG7nWyXBoz",0);add("EjYf7Bfz1TQBNfwWZ2",0);add("tRMU3yisGkW",2);add("HMrI0BSS5EYB0Dhw",1);add("qhedlmdV51",4);add("Yz1LJxzYryVObWs4S7B",0);add("sWur1fPYjG",4);add("ap5SrkeZ93X6LUGV",5);add("ppexQx1jMnrHC1xx",2);add("Ztxsni7pNF",4);
		add("Dương Đức Trí",AccountType.Facebook);
		
		
		byte[] dataFind = "Dương Đức Trí".getBytes();
		byte[] db="9SobrLMXle0Z".getBytes();
		
		System.out.println(Arrays.toString(dataFind));
		System.out.println(Arrays.toString(db));
		
		System.out.println(dbAccount.querryIndex("Dương Đức Trí").length);
//		dbAccount.trace();
		
		dbAccount.rfData.seek(dbAccount.getOffsetInBtree(68));
		System.out.println(dbAccount.rfData.readUTF());
		
		dbAccount.rfData.seek(dbAccount.getOffsetInBtree(68));
		System.out.println(Arrays.toString(dbAccount.rfData.readUTF().getBytes()));
		dbAccount.trace();
	}

	
	
	@AfterEach
	void tearDown() throws Exception {
		dbAccount.closeDatabase();
		dbUserData.closeDatabase();
		BGUtility.deleteFolder(folder);
	}
}
