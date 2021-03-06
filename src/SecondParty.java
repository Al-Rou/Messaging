import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Scanner;

public class SecondParty {
        public static void main(String[] args)
        {
            //A connection is established and the first number is received from Player One
            try {
                DatagramSocket datagramSocket = new DatagramSocket(5558);
                //This loop continues sending and receiving data until the messaging finishes
                while (true){
                    //Receiving data
                    String receivedMess = "";
                    ByteBuffer byteBuffer2 = ByteBuffer.allocate(1024);
                    byteBuffer2.order(ByteOrder.BIG_ENDIAN);
                    DatagramPacket datagramPacket2 = new DatagramPacket(byteBuffer2.array(),
                            byteBuffer2.array().length);
                    datagramSocket.receive(datagramPacket2);

                    do {
                        receivedMess += byteBuffer2.getChar();
                    }while (byteBuffer2.hasRemaining());
                    System.out.print("PartyOne: ");
                    System.out.println(receivedMess);
                    //Sending data
                    System.out.print("Your message: ");
                    Scanner in = new Scanner(System.in);
                    String sendingMess = in.nextLine();
                    ByteBuffer byteBuffer3 = ByteBuffer.allocate(1024);
                    byteBuffer3.order(ByteOrder.BIG_ENDIAN);
                    for (int j=0; j < sendingMess.length(); j++) {
                        byteBuffer3.putChar(sendingMess.charAt(j));
                    }
                    DatagramPacket datagramPacket3 = new DatagramPacket(byteBuffer3.array(),
                            byteBuffer3.position(), datagramPacket2.getAddress(),
                            datagramPacket2.getPort());
                    datagramSocket.send(datagramPacket3);
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }



}
