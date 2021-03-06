import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class SecondParty {
        public static void main(String[] args)
        {
            //A connection is established and the first number is received from Player One
            try {
                DatagramSocket datagramSocket = new DatagramSocket(5558);
                //Date is stamped
                Date dateOfMessaging = new Date();
                DateFormat dateFormatForMessaging = new SimpleDateFormat("MM.dd.yyyy");
                System.out.println(dateFormatForMessaging.format(dateOfMessaging.getTime()));
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
                    //Time of receiving message is stamped
                    Date dateOfReceiving = new Date();
                    DateFormat dateFormatForReceived = new SimpleDateFormat("HH:mm:ss");
                    String timeToShow = dateFormatForReceived.format(dateOfReceiving.getTime());
                    System.out.print("PartyOne: ");
                    System.out.print(receivedMess);
                    System.out.println(" (" + timeToShow + ")");
                    //Sending data
                    System.out.print("Your message: ");
                    Scanner in = new Scanner(System.in);
                    String sendingMess = in.nextLine();
                    Date dateOfSending = new Date();
                    Timestamp timestamp = new Timestamp(dateOfSending.getTime());
                    String sentTime = timestamp.toString();
                    String sentTimeToShow = sentTime.substring(11, 19);
                    System.out.println("(sent at " + sentTimeToShow + ")");
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
