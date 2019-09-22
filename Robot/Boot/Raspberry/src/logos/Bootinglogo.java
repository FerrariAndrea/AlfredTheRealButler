package logos;
import java.awt.Point;
import java.util.HashMap;

public class Bootinglogo {

	
	public static void printMatrix(Boolean[][] matrix) {
		String stamp ="-------------------------\n";
		for(int x =0;x<matrix.length;x++) {
			for(int y =0;y<matrix[x].length;y++) {
				if(matrix[x][y]) {
					stamp=stamp+"#";
				}else {
					stamp=stamp+" ";
				}
			}
			stamp=stamp+"\n";			
		}
		System.out.println(stamp);		
	}
	public static  byte[]  convertMatrix(Boolean[][] matrix) {
		byte[] ris = new byte[8];
		for(int x =0;x<matrix.length;x++) {
				int acc =0;
			for(int y =0;y<matrix[x].length;y++) {
				int exp =7-y;
				int actualVal = (int)Math.pow(2,exp);
				if(matrix[x][y]) {
					acc =acc+actualVal;
				}
			}
			ris[x]= (byte)acc;	
		}	
		return ris;
	}
	
	private static Boolean[][] _actual= {
			{true,true,true,true,true,true,true,true},
			{false,false,false,false,false,false,false,true},
			{false,false,false,false,false,false,false,true},
			{false,false,false,false,true,false,false,true},
			{false,false,false,true,false,false,false,true},
			{true,false,false,false,false,false,false,true},
			{true,false,false,false,false,false,false,true},
			{true,true,true,true,true,true,true,true},
	};
	private static HashMap<Integer,Point> _map= new HashMap<Integer,Point> ();
	public static void init() {
		_map.put(0, new Point(0,0));
		_map.put(1, new Point(1,0));
		_map.put(2, new Point(2,0));
		_map.put(3, new Point(3,0));
		_map.put(4, new Point(4,0));
		_map.put(5, new Point(5,0));
		_map.put(6, new Point(6,0));
		_map.put(7, new Point(7,0));
		_map.put(8, new Point(7,1));
		_map.put(9, new Point(7,2));
		_map.put(10, new Point(7,3));
		_map.put(11, new Point(7,4));
		_map.put(12, new Point(7,5));
		_map.put(13, new Point(7,6));
		_map.put(14, new Point(7,7));
		_map.put(15, new Point(6,7));
		_map.put(16, new Point(5,7));
		_map.put(17, new Point(4,7));
		_map.put(18, new Point(3,7));
		_map.put(19, new Point(2,7));
		_map.put(20, new Point(1,7));
		_map.put(21, new Point(0,7));
		_map.put(22, new Point(0,6));
		_map.put(23, new Point(0,5));
		_map.put(24, new Point(0,4));
		_map.put(25, new Point(0,3));
		_map.put(26, new Point(0,2));
		_map.put(27, new Point(0,1));
		_map.put(28, new Point(0,0));
		
	}
	private static int _inizio=0 ;
	private static int _fine=25;
	
	public static Boolean[][]  next() {
		
		Point temp =_map.get(_inizio);		
		_actual[temp.x][temp.y]=false;
		_inizio++;
		if(_inizio>28) {
			_inizio=0;
		}
		temp =_map.get(_fine);		
		_actual[temp.x][temp.y]=true;
		_fine++;
		if(_fine>28) {
			_fine=0;
		}
		if(_actual[3][3]) {
			_actual[3][3]=false;
			_actual[3][4]=true;
			_actual[4][3]=true;
			_actual[4][4]=false;
			
		}else {
			_actual[3][3]=true;
			_actual[3][4]=false;
			_actual[4][3]=false;
			_actual[4][4]=true;
		}
		return _actual;
	}

	
}
