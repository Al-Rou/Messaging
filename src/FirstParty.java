import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class FirstParty {
        public static void main(String[] args)
        {
            Date dateOfMessaging = new Date();
            DateFormat dateFormat = new SimpleDateFormat("MM.dd.yyyy");
            String dateToShow = dateFormat.format(dateOfMessaging.getTime());
            System.out.println("Start messaging on " + dateToShow + " (type \"&\" at the end of your message in order to send)");

            //Now, a connection is established and the message is sent to Party Two
            try{
                DatagramSocket datagramSocket = new DatagramSocket(5557);
                //This loop continues sending and receiving data until the messaging finishes
                do {
                    System.out.print("Your message: ");
                    Scanner in = new Scanner(System.in);
                    List<String> messageList = new ArrayList<>();
                    while (true){
                        String message = in.nextLine();
                        if(message.equals("&"))
                        {
                            break;
                        }
                        else {
                            messageList.add(message);
                        }
                    }
                    //Time of sending message is stamped
                    Date dateOfSending = new Date();
                    DateFormat dateFormatForSent = new SimpleDateFormat("HH:mm:ss");
                    String timeOfSending = dateFormatForSent.format(dateOfSending.getTime());
                    System.out.println("(sent at " + timeOfSending + ")");
                    //Sending data
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    byteBuffer.order(ByteOrder.BIG_ENDIAN);
                    for (int i=0; i < messageList.size(); i++)
                    {
                        for (int j=0; j < messageList.get(i).length(); j++)
                        {
                            byteBuffer.putChar(messageList.get(i).charAt(j));
                        }
                        byteBuffer.putChar('\n');
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
                    Date dateOfReceiving = new Date();
                    Timestamp timestamp = new Timestamp(dateOfReceiving.getTime());
                    System.out.print("PartyTwo: ");
                    System.out.print(receivedMessage);
                    String timeString = timestamp.toString();
                    String timeOfReceiving = timeString.substring(11, 19);
                    System.out.println(" (" + timeOfReceiving + ")");

                }while (true);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
}
