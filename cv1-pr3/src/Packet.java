public class Packet {
    private final int packetNumber;

    public Packet(int packetNumber) {
        this.packetNumber = packetNumber;
    }

    public int getPacketNumber() {
        return packetNumber;
    }

    public void process() {
        System.out.println("Processing packet: " + packetNumber);
    }
}