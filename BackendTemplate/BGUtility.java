package backendgame.com.core;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Random;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;


public class BGUtility {
    public static final Random random=new Random();
    
    private static final String numeric_value = "0123456789";
    private static final int numeric_length = 10;
    public static final String numeric(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            sb.append(numeric_value.charAt(random.nextInt(numeric_length)));
        return sb.toString();
    }
    
    private static final String alphabetic_value = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int alphabetic_length = 52;
    public static final String alphabetic(int length) {
    	StringBuilder sb = new StringBuilder(length);
    	for (int i = 0; i < length; i++)
    		sb.append(alphabetic_value.charAt(random.nextInt(alphabetic_length)));
    	return sb.toString();
    }
    
    private static final String alphanumeric_value = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int alphanumeric_length = 62;
    public static final String alphanumeric(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            sb.append(alphanumeric_value.charAt(random.nextInt(alphanumeric_length)));
        return sb.toString();
    }
    
    
    private static final String alphanumeric_lowercase_value = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final int alphanumeric_lowercase_length = 36;
    public static final String alphanumericLowercase(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            sb.append(alphanumeric_lowercase_value.charAt(random.nextInt(alphanumeric_lowercase_length)));
        return sb.toString();
    }
    
    private static final String alphanumeric_UPPERCASE_value = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int alphanumeric_UPPERCASE_length = 36;
    public static final String alphanumericUPPERCASE(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            sb.append(alphanumeric_UPPERCASE_value.charAt(random.nextInt(alphanumeric_UPPERCASE_length)));
        return sb.toString();
    }
    
    public static boolean isNullOrEmpty(String s) {
        return s==null || s.equals("");
    }
    
    public static long randomPositiveLong(long MAX) {
		long result = random.nextLong();
		if(result<0)
			result=-result;
		return result%MAX;
	}
    public static int randomPositiveInt(int MAX) {
    	int result = random.nextInt();
    	if(result<0)
    		result=-result;
    	return result%MAX;
    }
//    public static boolean isPrimitive(Class<?> c) {
//        return c==Boolean.class || c==Byte.class || c==Character.class || c==Short.class || c==Integer.class || c==Float.class || c==Long.class || c==Double.class;
//    }
//    public static final String toJsonString(Object object) throws IllegalArgumentException, IllegalAccessException {
//        String str = "{";
//        Class<?>c;
//        Field[] listField = object.getClass().getFields();
//        String nextLine = "";
//        String arrLine;
//        for(Field f:listField)
//            if(Modifier.isStatic(f.getModifiers())==false) {
//                if(f.get(object)==null) {
//                    str = str + nextLine + "\"" + f.getName() + "\" : null";
//                }else {
//                    if(f.getType().isArray()) {
//                        c = f.get(object).getClass().getComponentType();
//                        if (c == boolean.class) {
//                            str = str + nextLine + "\"" + f.getName() + "\" : " + Arrays.toString((boolean[]) f.get(object));
//                        } else if (c == byte.class) {
//                            str = str + nextLine + "\"" + f.getName() + "\" : " + Arrays.toString((byte[]) f.get(object));
//                        } else if (c == short.class) {
//                            str = str + nextLine + "\"" + f.getName() + "\" : " + Arrays.toString((short[]) f.get(object));
//                        } else if (c == int.class) {
//                            str = str + nextLine + "\"" + f.getName() + "\" : " + Arrays.toString((int[]) f.get(object));
//                        } else if (c == float.class) {
//                            str = str + nextLine + "\"" + f.getName() + "\" : " + Arrays.toString((float[]) f.get(object));
//                        } else if (c == long.class) {
//                            str = str + nextLine + "\"" + f.getName() + "\" : " + Arrays.toString((long[]) f.get(object));
//                        } else if (c == double.class) {
//                            str = str + nextLine + "\"" + f.getName() + "\" : " + Arrays.toString((double[]) f.get(object));
//                        } else if (c == Boolean.class || c == Byte.class || c == Short.class || c == Integer.class || c == Float.class || c == Long.class || c == Double.class) {
//                        	str = str + nextLine + "\"" + f.getName() + "\" : " + Arrays.toString((Object[])f.get(object));
//                        } else if (c == String.class) {
//                            str = str + nextLine + "\"" + f.getName() + "\" : " + Arrays.toString((String[]) f.get(object));
//                        } else if (c == Object.class) {
//                            str = str + nextLine + "\"" + f.getName() + "\" : []";
//                        } else {
//                            Object[] list=(Object[]) f.get(object);
//                            str = str + nextLine + "\"" + f.getName() + "\" : [";
//                            arrLine="";
//                            for(int i=0;i<list.length;i++) {
//                                str = str + arrLine + toJsonString(list[i]);
//                                arrLine=",";
//                            }
//                            str = str +"]";
//                        }
//                    }else {//Xét trường hợp variable là Object nhưng value là string hoặc đối tượng khác
//                    	c = f.get(object).getClass();
//                        if(isPrimitive(c)) {
//                            str = str + nextLine + "\"" + f.getName() + "\" : "+f.get(object);
//                        }else if(c==String.class) {
//                            str = str + nextLine + "\"" + f.getName() + "\" : \""+f.get(object)+"\"";
//                        }else if(c==Object.class) {
//                            str = str + nextLine + "\"" + f.getName() + "\" : null";
//                        }else {
//                            str = str + nextLine + "\"" + f.getName() + "\" : "+toJsonString(f.get(object));
//                        }
//                    }
//                }
//                nextLine=",";
//            }
//        return str+"}";
//    }
//    
//    public static final String toPrettyJsonString(Object object) throws IllegalArgumentException, IllegalAccessException {
//        return toPrettyJsonString(object, "");
//    }
//    public static final String toPrettyJsonString(Object object, String tab) throws IllegalArgumentException, IllegalAccessException {
//        String str = "{";
//        Class<?>c;
//        Field[] listField = object.getClass().getFields();
//        String nextLine = "\n";
//        String arrLine;
//        for(Field f:listField)
//            if(Modifier.isStatic(f.getModifiers())==false) {
//                if(f.get(object)==null) {
//                    str = str + nextLine + tab + "\t\"" + f.getName() + "\" : null";
//                }else {
//                    if(f.getType().isArray()) {
//                        c = f.get(object).getClass().getComponentType();
//                        if (c == boolean.class) {
//                            str = str + nextLine + tab + "\t\"" + f.getName() + "\" : " + Arrays.toString((boolean[]) f.get(object));
//                        } else if (c == byte.class) {
//                            str = str + nextLine + tab + "\t\"" + f.getName() + "\" : " + Arrays.toString((byte[]) f.get(object));
//                        } else if (c == short.class) {
//                            str = str + nextLine + tab + "\t\"" + f.getName() + "\" : " + Arrays.toString((short[]) f.get(object));
//                        } else if (c == int.class) {
//                            str = str + nextLine + tab + "\t\"" + f.getName() + "\" : " + Arrays.toString((int[]) f.get(object));
//                        } else if (c == float.class) {
//                            str = str + nextLine + tab + "\t\"" + f.getName() + "\" : " + Arrays.toString((float[]) f.get(object));
//                        } else if (c == long.class) {
//                            str = str + nextLine + tab + "\t\"" + f.getName() + "\" : " + Arrays.toString((long[]) f.get(object));
//                        } else if (c == double.class) {
//                            str = str + nextLine + tab + "\t\"" + f.getName() + "\" : " + Arrays.toString((double[]) f.get(object));
//                        } else if (c == Boolean.class || c == Byte.class || c == Short.class || c == Integer.class || c == Float.class || c == Long.class || c == Double.class) {
//                        	str = str + nextLine + tab + "\t\"" + f.getName() + "\" : " + Arrays.toString((Object[])f.get(object));
//                        } else if (c == String.class) {
//                            str = str + nextLine + tab + "\t\"" + f.getName() + "\" : " + Arrays.toString((String[]) f.get(object));
//                        } else if (c == Object.class) {
//                            str = str + nextLine + tab + "\t\"" + f.getName() + "\" : []";
//                        } else {
//                            Object[] list=(Object[]) f.get(object);
//                            str = str + nextLine + tab + "\t\"" + f.getName() + "\" : [";
//                            arrLine="\n";
//                            for(int i=0;i<list.length;i++) {
//                                str = str + arrLine + tab + "\t\t" + toPrettyJsonString(list[i], tab+"\t\t");
//                                arrLine=",\n";
//                            }
//                            str = str + "\n" + tab + "\t]";
//                        }
//                    }else {
//                    	c = f.get(object).getClass();
//                        if(isPrimitive(c)) {
//                            str = str + nextLine + tab + "\t\"" + f.getName() + "\" : "+f.get(object);
//                        }else if(c==String.class) {
//                            str = str + nextLine + tab + "\t\"" + f.getName() + "\" : \""+f.get(object)+"\"";
//                        }else if(c==Object.class) {
//                            str = str + nextLine + tab + "\t\"" + f.getName() + "\" : null";
//                        }else if(c==Map.class) {
//                        	Map<?,?> map = (Map<?,?>) f.get(object);
//                        	Set<?> listKey = map.keySet();
//                        	arrLine="\n";
//                        	for(Object key : listKey) {
//                        		if(map.get(key)==null || map.get(key).getClass()==Object.class)
//                        			str = str + nextLine + tab + "\t\"" + f.getName() + "\" : null";
//                        		else{
//	                        		c = map.get(key).getClass();
//	                        		if(isPrimitive(c)) {
//	                        			str = str + nextLine + tab + "\t\t\"" + key.toString() + "\" : "+map.get(key);
//	                        		}else if(c==String.class) {
//	                        			str = str + nextLine + tab + "\t\t\"" + key.toString() + "\" : \""+map.get(key)+"\"";
//	                        		}else if(c==Object.class) {
//	                        			str = str + nextLine + tab + "\t\t\"" + key.toString() + "\" : null";
//	                        		}
//	                        		
//	                        		str = str + nextLine + tab + "\t\t\"" + key.toString() + "\" : null";
//	                        		
//	                        		
//	                        		
//	                        	}
//                        		str = str + nextLine + tab + "\t}";
//                        		nextLine=",\n";
//                        	}
//                        }else {
//                            str = str + nextLine + tab + "\t\"" + f.getName() + "\" : "+toPrettyJsonString(f.get(object), tab+"\t");
//                        }
//                    }
//                }
//                nextLine=",\n";
//            }
//        return str+ "\n" + tab + "}";
//    }
    
	public static final long USCLN(long a, long b) {
	    // Nếu a = 0 => ucln(a,b) = b
	    // Nếu b = 0 => ucln(a,b) = a
	    if (a == 0 || b == 0)
	        return a + b;
	    while (a != b)
	        if (a > b)
	            a -= b; // a = a - b
	        else
	            b -= a;
	    return a; // return a or b, bởi vì lúc này a và b bằng nhau
	}
	public static final long BSCNN(long a, long b) {
		if (a >= b)
			for (int i = 1; i < b; i++)
				if ((a * i) % b == 0)
					return a * i;
		else
			for (int k = 1; k < a; k++)
				if ((b * k) % a == 0)
					return b * k;
		return a*b;
	}
	
	
	public static final ArrayList<String> getIpV4() {
		ArrayList<String> all = getIp();
		ArrayList<String> result = new ArrayList<>();
		for(String s:all)
			if(s.contains("."))
				result.add(s);
		return result;
	}
	public static final void traceIpAdress() {
		ArrayList<String> listIp=getIp();
		for(String s:listIp)
			System.out.println(s);
	}
	public static final ArrayList<String> getIp() {
		ArrayList<String> listIp=new ArrayList<String>();
		String s;
		try {
			Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
			while(e.hasMoreElements()){
			    NetworkInterface n = (NetworkInterface) e.nextElement();
			    Enumeration<InetAddress> ee = n.getInetAddresses();
			    while (ee.hasMoreElements()) {
			    	s=ee.nextElement().getHostAddress();
			    	if(s!=null && s.equals("127.0.0.1")==false && s.startsWith("fe80:0:0:0")==false && s.startsWith("0:0:0:0:0:0")==false)
			    		listIp.add(s);
			    }
			}
			return listIp;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return listIp;
	}
	public static final void traceListMac() {
		ArrayList<String>_listMac=getListMacAdress();
		for(int i=0;i<_listMac.size();i++)
			System.out.println("MacAddress : "+_listMac.get(i));
	}
	public static String getMacAddress() throws UnknownHostException, SocketException {
		InetAddress ipAddress = InetAddress.getLocalHost();
		NetworkInterface networkInterface = NetworkInterface.getByInetAddress(ipAddress);
		byte[] macAddressBytes = networkInterface.getHardwareAddress();
		StringBuilder macAddressBuilder = new StringBuilder();

		for (int macAddressByteIndex = 0; macAddressByteIndex < macAddressBytes.length; macAddressByteIndex++) {
			String macAddressHexByte = String.format("%02X", macAddressBytes[macAddressByteIndex]);
			macAddressBuilder.append(macAddressHexByte);

			if (macAddressByteIndex != macAddressBytes.length - 1) {
				macAddressBuilder.append(":");
			}
		}

		return macAddressBuilder.toString();
	}
	public static final ArrayList<String> getListMacAdress() {
		ArrayList<String>_listMac=new ArrayList<>();
		StringBuilder sb=null;
		try {
	        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
	        while(networkInterfaces.hasMoreElements()){
	            NetworkInterface network = networkInterfaces.nextElement();
	            byte[] mac = network.getHardwareAddress();
	            if(mac == null){
	            }else{
	                sb = new StringBuilder();
	                for (int i = 0; i < mac.length; i++){
	                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? ":" : ""));        
	                }
	                _listMac.add(sb.toString());
	            }
	        }
	    } catch (SocketException e){}
		return _listMac;
	}
	
	public static double getProcessCpuLoad() throws Exception {
	    MBeanServer mbs    = ManagementFactory.getPlatformMBeanServer();	
	    ObjectName name    = ObjectName.getInstance("java.lang:type=OperatingSystem");
	    AttributeList list = mbs.getAttributes(name, new String[]{ "ProcessCpuLoad" });

	    if (list.isEmpty())     return Double.NaN;

	    Attribute att = (Attribute)list.get(0);
	    Double value  = (Double)att.getValue();

	    // usually takes a couple of seconds before we get real values
	    if (value == -1.0)      return Double.NaN;
	    // returns a percentage value with 1 decimal point precision
	    return ((int)(value * 1000) / 10.0);
	}
	
	public static final String getRunableJarPath() throws URISyntaxException {
		return new File(BGUtility.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
	}
	
	public static final String getStringException(Exception e) {
		String s = e.toString();
		StackTraceElement[] list = e.getStackTrace();
		if(list!=null)
			for(StackTraceElement st : list)
				s=s+"\n"+st;
		return s;
	}
	
	public static void deleteFolder(File file) {
		if(file.isDirectory()){
			File[] listfile = file.listFiles();
			for(File f:listfile)
				deleteFolder(f);
		}
//		System.out.println("Delete : "+file.getPath());
		file.delete();
	}

	public static final String getMemory(long l) {
		if(l<1024)
			return l+" b";
		if(l<1024*1024) {
			return new DecimalFormat("#.##").format(((double)l)/1024)+" K("+l+")";
		}
		if(l<1024*1024*1024) {
			return new DecimalFormat("#.##").format(((double)l)/(1024*1024))+" M("+l+")";
		}
		return new DecimalFormat("#.##").format(((double)l)/(1024*1024*1024))+" G("+l+")";
	}
}
