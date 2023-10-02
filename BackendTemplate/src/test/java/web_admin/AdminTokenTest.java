package web_admin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import backendgame.com.core.BGUtility;
import backendgame.com.core.TimeManager;
import server.config.CipherBinary;

class AdminTokenTest {
	private byte[] buffer;
	private int currentReading;
	@Test void test() {
		long l = System.currentTimeMillis();
		AdminToken.cipherBinary=new CipherBinary();
		AdminToken adminToken=new AdminToken();
		AdminToken adminDecode=new AdminToken();
		
		
		for(int i=0;i<1000000;i++) {
			adminToken.username = BGUtility.alphanumeric(3+BGUtility.random.nextInt(100));
			adminToken.timeOut = System.currentTimeMillis() + TimeManager.A_MINUTES_MILLISECOND;
			String token = adminToken.toHashString();
			assertTrue(adminDecode.decode(token, true));
			assertEquals(adminToken.username, adminDecode.username);
			assertEquals(adminToken.timeOut, adminDecode.timeOut);
		}
		
		
		
		buffer=new byte[] {0, 0, 0, 0, 0, 0, 3, -30};
		currentReading=0;
		
		System.out.println("====>"+AAAaaa());
		
		System.out.println("AdminTokenTest : "+(System.currentTimeMillis()-l)+" ms");
	}

	
	private long AAAaaa() {
		long l0 = buffer[currentReading] & 0xFF;
		long l1 = buffer[currentReading+1] & 0xFF;
		long l2 = buffer[currentReading+2] & 0xFF;
		long l3 = buffer[currentReading+3] & 0xFF;
		long l4 = buffer[currentReading+4] & 0xFF;
		long l5 = buffer[currentReading+5] & 0xFF;
		long l6 = buffer[currentReading+6] & 0xFF;
		long l7 = buffer[currentReading+7] & 0xFF;
		currentReading+=8;
		
		long r0 = l0 << 56;
		long r1 = l1 << 48;
		long r2 = l2 << 40;
		long r3 = l3 << 32;
		long r4 = l4 << 24;
		long r5 = l5 << 16;
		long r6 = l6 << 8;
		long r7 = l7;
		return r0 + r1 + r2 + r3 + r4 + r5 + r6 + r7;
	}
}
