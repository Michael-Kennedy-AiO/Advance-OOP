package server;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import backendgame.com.core.TimeManager;
import backendgame.com.database.table.DBTable_1Primary;

class Server_BackendGameTest {
	public long timeBegin;
	public Random random;
	public static final short DBId=1;
	public static final short TableId=1;
	public RandomAccessFile rf;
	public DBTable_1Primary database1Primary;
	@Test
	void test() throws IOException {
		timeBegin = System.currentTimeMillis();

		System.out.println(TimeManager.gI().getTimeBreakLine(System.currentTimeMillis()));
		System.out.println(TimeManager.gI().getStringTime());
		System.out.println(TimeManager.gI().getDayMonthYearAsInteger(timeBegin));
		System.out.println(TimeManager.gI().getStringDayMonthYear(timeBegin));
		System.out.println("******************");
		System.out.println(TimeManager.gI().getHourMinus());
		System.out.println(TimeManager.gI().getFileName(timeBegin));
	}

	@AfterEach
	void tearDown() throws Exception {
		//2023/7/26-11:12:24
	}
}
