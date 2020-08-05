package movida.bartoluccipolisena;

public class Utils {
	
	public static boolean checkData(String line, String name,int num,String separator) {
		boolean result = line.startsWith(name) && line.indexOf(separator) >= 0;
		if(result) {
			if(name.equalsIgnoreCase("Cast")) {
				return line.split(separator).length >= 2;
			}else {
				return line.split(separator).length == num;
			}
		}
		return  result;
	}
	
}
