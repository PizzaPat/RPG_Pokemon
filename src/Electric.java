public interface Electric {
    public static final int type = 3;
    public static String typeMenu = "1. Thunder Shock   2. Thunderbolt   3. Thunder Punch";
    public int thunderShock();
    public int thunderbolt();
    public int thunderPunch();
    public String getThunderPunch();
    public String getThunderbolt();
    public String getThunderShock();
    public String returnMoveName(int move);

}
