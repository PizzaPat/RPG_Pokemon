/**
 * Created by PizzaPat on 8/31/2016.
 */
public interface Fire {
    public static final int type = 0;
    public static String typeMenu = "1. Ember   2. Fire Blast   3. Fire Punch";
    public int ember();
    public int fireBlast();
    public int firePunch();
    public String getFirePunch();
    public String getEmber();
    public String getFireBlast();
}
