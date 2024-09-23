import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        Queue<Packet> packetQueue = new LinkedList<>();

        packetQueue.add(new Packet(1));
        packetQueue.add(new Packet(2));
        packetQueue.add(new Packet(3));
        packetQueue.add(new Packet(4));

        int rounds = 2;
        for (int i = 0; i < rounds; i++) {
            for (Packet packet : packetQueue) {
                packet.process();
            }
        }
    }
}