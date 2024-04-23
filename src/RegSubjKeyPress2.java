import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.net.InetAddress;
import java.sql.Time;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class RegSubjKeyPress2 extends JFrame implements MouseListener, MouseMotionListener {
    static int mousex = 0, mousey = 0;
    static int memx = 0, memy = 0;
    static JLabel l = new JLabel(), l2 = new JLabel();
    static boolean set = false;
    static Dimension small = new Dimension(400,200),
                     large = new Dimension(1200,1000);
    static JFrame  f = new JFrame("Reg Subj Key Press");
    long memt = 0;
    long tclick = 0, ttoclick = 0;
    static String TIME_SERVER = "stdtime.gov.hk";
    boolean started = false;


    RegSubjKeyPress2(){
        try {
            // Create a new instance for the Robot class
            Robot robot = new Robot();
            //opens a file
            File file = new File(TIME_SERVER);

            NTPUDPClient timeClient = new NTPUDPClient();
            timeClient.setDefaultTimeout(10_000);
            InetAddress inetAddress = InetAddress.getByName(TIME_SERVER);
            TimeInfo timeInfo;
            long returnTime, delay, difference;
            Date time;

            int maxcnt = 10, cnt = maxcnt;
            delay = System.currentTimeMillis();
            timeInfo = timeClient.getTime(inetAddress);
            returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();
            time = new Date(returnTime);
            delay -= System.currentTimeMillis(); delay *= -.5;
            System.out.println(time.toString()+": "+(time.toInstant().toEpochMilli()-delay));
            difference = time.toInstant().toEpochMilli() - System.currentTimeMillis() - delay;
            int hkgmt = 8*60*60*1000;
                //as long as there is no great change in network environment.

            //layout of the frame
            f.addMouseMotionListener(this);
            f.addMouseListener(this);

            JPanel      p = new JPanel();

            JButton   loc = new JButton("Set Mouse Loc Again");//button for setting mouse location
            loc.addActionListener((ae)->{
                set = false;
                f.setSize(large);
            });

            JPanel     p3 = new JPanel();
            JLabel     l3 = new JLabel("Input exact time (dd/mm/yyyy hh:mm:ss) to click: ");
            JTextField t3 = new JTextField(10);
            p3.add(l3); p3.add(t3);


            JButton     e = new JButton("Start Timing!");
            e.addActionListener((ae)->{
                ttoclick = timetomillis(t3.getText());
                started = true;
            });
            p.add(l);
            p.add(l2);
            p.add(loc);
            //p.add(syn);
            //p.add(p2);
            p.add(p3);
            p.add(e);
            //System.exit(0);//*/

            f.setSize(large);
            f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            f.setResizable(false);
            f.add(p);
            f.setUndecorated(true);
            f.setOpacity(0.8f);
            f.setVisible(true);

            long now;
            do {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException interruptedException) {}
                now = System.currentTimeMillis() + difference;
                l.setText("Time: " + Instant.ofEpochMilli(now+hkgmt).toString().split("T")[1].split("\\.")[0]);
                if(started) l2.setText("Time Remaining: " + Instant.ofEpochMilli(ttoclick-now).toString().split("T")[1].split("\\.")[0]);
            } while(!started || ttoclick - now > 1000);//*/
            //);
                robot.mouseMove(memx,memy);
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                while(ttoclick - (System.currentTimeMillis() + difference) > 0);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);//*/
            System.exit(0);
        }
        catch (Exception e) {}
    }

    public static long timetomillis(String tim){
        // here we change the time format into the Americans, just as that the java devs adapts.
        try{
            if(tim.contains(" ")){
                String[] s = tim.split(" "), s1 = s[0].split("/"), s2 = s[1].split(":");
                return new Date(s1[1]+"/"+s1[0]+"/"+s1[2]+" "+s2[0]+":"+s2[1]+":"+s2[2]).toInstant().toEpochMilli();
            } else if(tim.contains(":")){
                String[] s2 = tim.split(":");
                Date today = new Date(System.currentTimeMillis());
                //System.out.println((today.getMonth()+1)+"/"+today.getDate()+"/"+today.getYear()+" "+s2[0]+":"+s2[1]+":"+s2[2]);
                return new Date((today.getMonth()+1)+"/"+today.getDate()+"/"+(today.getYear()+1900)+" "+s2[0]+":"+s2[1]+":"+s2[2]).toInstant().toEpochMilli();
            } else return 0;
        } catch (Exception e){ System.out.println("ERROR"); return 0;}
    }

    static int Int(String str){
        return Integer.parseInt(str);
    }

    public static void main(String[] args) {
        new RegSubjKeyPress2();
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousex = e.getX();
        mousey = e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(!set){
            set = true;
            f.setSize(small);
            memt = System.nanoTime();
            memx = e.getX();
            memy = e.getY();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}