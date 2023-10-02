package backendgame.com.database;

import java.io.IOException;
import java.io.RandomAccessFile;

import backendgame.com.core.database.BGColumn;
import backendgame.com.core.server.BackendSession;
import backendgame.com.database.entity.BGDTO_Att;

public class BGBTree {
	public static final void removeIndex(RandomAccessFile rf,long index) throws IOException {
		long offset=index*8;
		long length=rf.length();
		byte[] data=new byte[BackendSession.SOCKET_BUFFER];
		
		while(offset+BackendSession.SOCKET_BUFFER<length) {
			rf.seek(offset+8);
			rf.read(data);
			rf.seek(offset);
			rf.write(data);
			offset+=BackendSession.SOCKET_BUFFER;
		}
		
		rf.seek(offset+8);
		rf.read(data,0,(int) (length-offset-8));
		rf.seek(offset);
		rf.write(data,0,(int) (length-offset-8));
		
		rf.setLength(length-8);
	}
	
	
	
	
	public static void copyAllData(RandomAccessFile source,RandomAccessFile dest) throws IOException {
		long dbLength=source.length();
		if(dbLength==0)
			return;
		source.seek(0);
		dest.seek(0);
		if(dbLength<BackendSession.SOCKET_BUFFER) {
			byte[] _data=new byte[(int) dbLength];
			source.read(_data);
			dest.write(_data);
		}else{
			byte[] Buffer=new byte[BackendSession.SOCKET_BUFFER];
			long count=0;
			while(count+BackendSession.SOCKET_BUFFER<dbLength) {
				source.read(Buffer);
				dest.write(Buffer);
				count+=BackendSession.SOCKET_BUFFER;
			}
			source.read(Buffer,0,(int) (dbLength-count));
			dest.write(Buffer,0,(int) (dbLength-count));
		}
	}
	
	
	public static BGDTO_Att[] getDuplicationNewOld(BGColumn[] newDes,BGColumn[] oldDes) {
		int numberDuplication=0;
		int numberColNew = newDes.length;
		int numberColOld = oldDes.length;
		for(int n=0;n<numberColNew;n++)
			for(int o=0;o<numberColOld;o++)
				if(oldDes[o].ColumnName.equals(newDes[n].ColumnName)) {
					numberDuplication++;
					break;
				}
		
		BGDTO_Att[] listCopy = new BGDTO_Att[numberDuplication];
		numberDuplication=0;
		BGDTO_Att dtoData;
		
		for(int n=0;n<numberColNew;n++)
			for(int o=0;o<numberColOld;o++)
				if(oldDes[o].ColumnName.equals(newDes[n].ColumnName)) {
					dtoData=new BGDTO_Att();
					dtoData.indexDescribe = n;
					dtoData.oldType=oldDes[o].Type;
					dtoData.oldOffsetRow = oldDes[o].OffsetRow;
					listCopy[numberDuplication++]=dtoData;
					break;
				}
		
		return listCopy;
	}
}
