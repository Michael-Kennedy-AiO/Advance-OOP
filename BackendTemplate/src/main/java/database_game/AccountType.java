package database_game;

import backendgame.com.core.BGUtility;

public class AccountType {
	public static final byte Device						=0;
	public static final byte SystemAccount				=1;
	public static final byte Facebook					=2;
	public static final byte Google						=3;
	public static final byte AdsId						=4;
	public static final byte Apple						=5;	
	public static final byte EmailCode					=6;
	
	public static byte random() {
		return (byte) BGUtility.random.nextInt(7);
	}
	
	public static String getName(byte accountType) {
		switch (accountType) {
			case Device:return "Device";
			case SystemAccount:return "SystemAccount";
			case Facebook:return "Facebook";
			case Google:return "Google";
			case AdsId:return "AdsId";
			case Apple:return "Apple";
			case EmailCode:return "EmailCode";
			default:return "Null("+accountType+")";
		}
	}
}
