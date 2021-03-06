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
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //System.out.print("Your message: ");
                                Scanner in = new Scanner(System.in);
                                String message = in.nextLine();
                                //Time of sending message is stamped
                                Date dateOfSending = new Date();
                                DateFormat dateFormatForSent = new SimpleDateFormat("HH:mm:ss");
                                String timeOfSending = dateFormatForSent.format(dateOfSending.getTime());
                                System.out.println("Your message: " + message + " (sent at " + timeOfSending + ")");
                                //Sending data
                                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                                byteBuffer.order(ByteOrder.BIG_ENDIAN);
                                for (int j=0; j < message.length(); j++) {
                                    byteBuffer.putChar(message.charAt(j));
                                }
                                DatagramPacket datagramPacket = new DatagramPacket(byteBuffer.array(), byteBuffer.position(),
                                        InetAddress.getLocalHost(), 5558);
                                datagramSocket.send(datagramPacket);
                            }catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
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
                            }catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                }while (true);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
}
