import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        // Create a circular buffer (cyclic queue)
        Queue<Packet> packetQueue = new LinkedList<>();

        // Add packets to the queue
        packetQueue.add(new Packet(1));
        packetQueue.add(new Packet(2));
        packetQueue.add(new Packet(3));
        packetQueue.add(new Packet(4));

        // Simulate cyclic processing
        int rounds = 2; // Number of times to cycle through the packets
        for (int i = 0; i < rounds; i++) {
            for (Packet packet : packetQueue) {
                packet.process();
            }
        }
    }
}