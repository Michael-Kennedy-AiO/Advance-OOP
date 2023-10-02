package utility;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

import backendgame.com.core.DBDefine_DataType;
import backendgame.com.core.database.BGColumn;
import backendgame.com.database.BGBaseDatabase;
import backendgame.com.database.entity.BGCondition;
import database_game.AccountType;

class Define {

	@Test void test() {
		System.out.println("**************************************************************************************");
		System.out.println("DBTYPE_PRIMARY1 : "+BGBaseDatabase.DBTYPE_1Primary);
		System.out.println("DBTYPE_PRIMARY2 : "+BGBaseDatabase.DBTYPE_2Primary);
		System.out.println("DBTYPE_ROW : "+BGBaseDatabase.DBTYPE_Row);
		System.out.println("DBTYPE_LINE : "+BGBaseDatabase.DBTYPE_LineNode);
		System.out.println("DBTYPE_LEADERBOARD_LINK : "+BGBaseDatabase.DBTYPE_LeaderboardLink);
		System.out.println("DBTYPE_LEADERBOARD_COMMIT : "+BGBaseDatabase.DBTYPE_LeaderboardCommit);
		System.out.println("**************************************************************************************");
		Field[] listField = DBDefine_DataType.class.getDeclaredFields();
		for(Field f:listField)
			try {
				if(Modifier.isStatic(f.getModifiers()))
					System.out.println("    public const sbyte "+f.getName() + "	= "+f.get(null)+";");
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		System.out.println("**************************************************************************************");
		listField = BGCondition.class.getDeclaredFields();
		for(Field f:listField)
			try {
				if(Modifier.isStatic(f.getModifiers()))
					System.out.println(f.getName() + " : "+f.get(null));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		System.out.println("**************************************************************************************");
		listField = BGColumn.class.getDeclaredFields();
		for(Field f:listField)
			try {
				if(Modifier.isStatic(f.getModifiers()))
					System.out.println("    public const sbyte "+f.getName() + "	= "+f.get(BGColumn.class)+";");
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		System.out.println("**************************************************************************************");
		System.out.println(AccountType.getName(AccountType.Device)+" : "+AccountType.Device);
		System.out.println(AccountType.getName(AccountType.SystemAccount)+" : "+AccountType.SystemAccount);
		System.out.println(AccountType.getName(AccountType.Facebook)+" : "+AccountType.Facebook);
		System.out.println(AccountType.getName(AccountType.Google)+" : "+AccountType.Google);
		System.out.println(AccountType.getName(AccountType.AdsId)+" : "+AccountType.AdsId);
		System.out.println(AccountType.getName(AccountType.Apple)+" : "+AccountType.Apple);
		System.out.println(AccountType.getName(AccountType.EmailCode)+" : "+AccountType.EmailCode);
		System.out.println("**************************************************************************************");
	}

}
