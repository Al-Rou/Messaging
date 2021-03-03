import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Random;
import java.util.Scanner;

public class FirstParty {
        public static void main(String[] args)
        {
            System.out.println("Start messaging:");
            Scanner in = new Scanner(System.in);
            String message = in.nextLine();
            //Now, a connection is established and the message is sent to Party Two
            try{
                DatagramSocket datagramSocket = new DatagramSocket(5557);
                //This loop continues sending and receiving data until the messaging finishes
                do {
                    //Sending data
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    byteBuffer.order(ByteOrder.BIG_ENDIAN);
                    for (int j=0; j < message.length(); j++) {
                        byteBuffer.putChar(message.charAt(j));
                    }
                    //byteBuffer.putChar('/');
                    DatagramPacket datagramPacket = new DatagramPacket(byteBuffer.array(), byteBuffer.position(),
                            InetAddress.getLocalHost(), 5558);
                    datagramSocket.send(datagramPacket);
                    //Receiving data
                    ByteBuffer byteBufferResponse = ByteBuffer.allocate(1024);
                    byteBufferResponse.order(ByteOrder.BIG_ENDIAN);
                    DatagramPacket datagramPacketRes = new DatagramPacket(byteBufferResponse.array(),
                            byteBufferResponse.array().length);
                    datagramSocket.receive(datagramPacketRes);
                    String receivedMessage = "";
                    do {
                        receivedMessage += byteBufferResponse.getChar();
                    }while (byteBufferResponse.hasRemaining());
                    System.out.println(receivedMessage);

                }while (2 > 1);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
}
