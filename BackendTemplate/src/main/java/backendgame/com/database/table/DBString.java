package backendgame.com.database.table;

import java.io.IOException;
import java.io.RandomAccessFile;

public class DBString{
	public RandomAccessFile rfData;
	public RandomAccessFile rfBTree;
	
	private byte[] data;
	private int dataLength;
	private byte[] dataCompare;
	private int compare;
	private long l;
	public DBString(String path) throws IOException {
		rfData = new RandomAccessFile(path+".StringData", "rw");
		rfBTree = new RandomAccessFile(path+".StringIndex", "rw");
		if(rfBTree.length()==0) {/*Phần tử đầu tiên là null*/
			rfData.writeShort(0);
			rfBTree.writeLong(0);
		}
	}
	
	public String findStringByIndex(int index) throws IOException {
//		System.err.println("Method is not safe");
		rfBTree.seek(index*8);
		return readStringByOffset(rfBTree.readLong());
	}
	public String readStringByOffset(long location) throws IOException {
		rfData.seek(location);
		byte[] _data = new byte[rfData.readShort()];
		rfData.read(_data);
		return new String(_data);
	}	
	
	public long getOffset(String str) throws IOException {if(str==null)return -1;return getOffset(str.getBytes());}
	private long getOffset(byte[] _data) throws IOException {
		if(rfBTree.length()==0) {
			long beginLocation = rfData.length();
			rfBTree.writeLong(beginLocation);
			
			rfData.seek(beginLocation);
			rfData.writeShort(_data.length);
			rfData.write(_data);
			return beginLocation;
		}
		////////////////////////////////////////////////////////////////////
		long id;
		if(rfBTree.length()!=0){
			id = findStringId(_data);
			if(id>-1)
				return id;
		}
		
		long location = rfData.length();
		rfData.seek(location);
		rfData.writeShort(_data.length);
		rfData.write(_data);
		//////////////////////l luôn trùng với r
		long lengBTree = rfBTree.length();
		if(compare<0) {//S nằm bên trái l
			if(l*8==lengBTree) {
				rfBTree.seek(lengBTree);
				rfBTree.writeLong(location);
			}else {
				_data=new byte[(int) (lengBTree - l*8)];
				rfBTree.seek(l*8);
				rfBTree.read(_data);
				
				rfBTree.seek(l*8);
				rfBTree.writeLong(location);
				rfBTree.write(_data);
			}
		}else {//S nằm bên phải l
			l++;//Dịch qua phải 1 vị trí
			if(l*8==lengBTree) {//lúc khởi tạo thì r = length/8 -1
				rfBTree.seek(lengBTree);
				rfBTree.writeLong(location);
			}else {
				_data=new byte[(int) (lengBTree - l*8)];
				rfBTree.seek(l*8);
				rfBTree.read(_data);
				
				rfBTree.seek(l*8);
				rfBTree.writeLong(location);
				rfBTree.write(_data);
			}
		}
		return location;
	}

	private long findStringId(byte[] _data) throws IOException {
		data = _data;
		dataLength = data.length;
		dataCompare = new byte[dataLength];
		
		l = 0;
		long r = rfBTree.length()/8 - 1;//r là nơi sẽ indexData mới
		long m;
		while (l < r) {
			m = (l + r) / 2;
			
			calculateCompare(m);
			if(compare==0) {
				rfBTree.seek(m*8);
				return rfBTree.readLong();
			}
			
			if(compare>0)
				l = m + 1;
			else
				r = m - 1;
		}
		
		calculateCompare(l);
		if(compare==0){
			rfBTree.seek(l*8);
			return rfBTree.readLong();
		}

		return -1;
	}

	private void calculateCompare(long id) throws IOException {
		rfBTree.seek(id*8);
		rfData.seek(rfBTree.readLong());
//		rfData.read(buff2);
//		compare = dataLength - (((buff2[0] & 0xff) << 8) | (buff2[1] & 0xff));
		compare = dataLength - rfData.readShort();
		if(compare==0) {
			rfData.read(dataCompare);
			for(int i=0;i<dataLength;i++)
				if(data[i]!=dataCompare[i]) {
					compare = data[i]-dataCompare[i];
					return;
				}
			compare=0;
		}		
	}

	public void close() {if(rfBTree!=null)try {rfBTree.close();rfBTree=null;} catch (IOException e) {e.printStackTrace();}if(rfData!=null)try {rfData.close();rfData=null;} catch (IOException e) {e.printStackTrace();}}
	
	public void trace() {
		try {
			trace((int) (rfBTree.length()/8));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void trace(int _length) throws IOException{
		System.out.println("rfData("+rfData.length()+")	rfBTree("+rfBTree.length()+")	==>	Row : "+(rfBTree.length()/8));
//		int length = (int) (rfBTree.length()/8);
//		length = length<_length?length:_length;
//	
//		rfBTree.seek(0);
//		for(int i=0;i<length;i++) {
//			long location = rfBTree.readLong();
//			rfData.seek(location);
//			byte[] str=new byte[rfData.readShort()];
//			rfData.read(str);
//			
//			System.out.println("	"+i+"	"+new String(str)+"		Btree("+location+")	");
//		}
	}
}
