import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

public class FirstParty {
        public static void main(String[] args)
        {
            Date dateOfMessaging = new Date();
            DateFormat dateFormat = new SimpleDateFormat("MM.dd.yyyy");
            String dateToShow = dateFormat.format(dateOfMessaging.getTime());
            System.out.println("Start messaging on " + dateToShow);

            //Now, a connection is established and the message is sent to Party Two
            try{
                DatagramSocket datagramSocket = new DatagramSocket(5557);
                //This loop continues sending and receiving data until the messaging finishes
                do {
                    System.out.print("Your message: ");
                    Scanner in = new Scanner(System.in);
                    String message = in.nextLine();
                    //Sending data
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    byteBuffer.order(ByteOrder.BIG_ENDIAN);
                    for (int j=0; j < message.length(); j++) {
                        byteBuffer.putChar(message.charAt(j));
                    }
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
                    Date date = new Date();
                    Timestamp timestamp = new Timestamp(date.getTime());
                    System.out.print("PartyTwo: ");
                    System.out.print(receivedMessage);
                    String timeString = timestamp.toString();
                    String timeToShow = timeString.substring(11, 19);
                    System.out.println(" (" + timeToShow + ")");

                }while (true);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
}
