import com.pi4j.component.gyroscope.Gyroscope;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CFactory;


public class Main {


    public static  void main(String[] args) throws Exception {
        I2CBus bus = I2CFactory.getInstance(I2CBus.BUS_0);

        Bussola hmc5883l = new Bussola(bus);

        hmc5883l.init(hmc5883l.X, Gyroscope.GET_RAW_VALUE_TRIGGER_READ);

        long now = System.currentTimeMillis();

        int measurement = 0;

        while (System.currentTimeMillis() - now < 10000) {

            String sm = toString(measurement, 3);

            String sx = toString(hmc5883l.X.getRawValue(), 7);
            String sy = toString(hmc5883l.Y.getRawValue(), 7);
            String sz = toString(hmc5883l.Z.getRawValue(), 7);

            System.out.print(sm + sx + sy + sz);
            for (int i = 0; i < 24; i++) { System.out.print((char)8); }

            Thread.sleep(100);

            measurement++;
        }
        System.out.println();
    }
    

    public static  String toString(int i, int l) {
        String s = Integer.toString(i);
        return "        ".substring(0, l - s.length()) + s;
    }

}
