package sample;

public class Hardware {

    int ID;
    int proID;
    String Diameter;
    String Pitch;
    String B;
    String K;
    String DK;
    String A;
    String s;
    String Length;
    String Head;
    String Socket;
    String Type;
    int Quan;
    String name;

    public Hardware(int ID, int proID, String diameter, String pitch, String b, String k, String DK, String a, String s, String length, String head, String socket, String type, int quan, String name) {
        this.ID = ID;
        this.proID = proID;
        Diameter = diameter;
        Pitch = pitch;
        B = b;
        K = k;
        this.DK = DK;
        A = a;
        this.s = s;
        Length = length;
        Head = head;
        Socket = socket;
        Type = type;
        Quan = quan;
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getProID() {
        return proID;
    }

    public void setProID(int proID) {
        this.proID = proID;
    }

    public String getDiameter() {
        return Diameter;
    }

    public void setDiameter(String diameter) {
        Diameter = diameter;
    }

    public String getPitch() {
        return Pitch;
    }

    public void setPitch(String pitch) {
        Pitch = pitch;
    }

    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }

    public String getK() {
        return K;
    }

    public void setK(String k) {
        K = k;
    }

    public String getDK() {
        return DK;
    }

    public void setDK(String DK) {
        this.DK = DK;
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getLength() {
        return Length;
    }

    public void setLength(String length) {
        Length = length;
    }

    public String getHead() {
        return Head;
    }

    public void setHead(String head) {
        Head = head;
    }

    public String getSocket() {
        return Socket;
    }

    public void setSocket(String socket) {
        Socket = socket;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getQuan() {
        return Quan;
    }

    public void setQuan(int quan) {
        Quan = quan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
