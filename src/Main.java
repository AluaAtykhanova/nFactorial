import javax.swing.*;
import java.awt.*;

public class Main extends JPanel{
    final int BF_WIDTH=576-64;
    final int BF_HEIGHT=576-64;

    //1-Up 2-Down 3-Left 4-Right
    int direction =2;
    int bulletX= 320;
    int bulletY=320;
    int x=256, y =256;

    void move(int direction) throws Exception{
        this.direction=direction;


        if(direction==1){
                y--;
        }
        if (direction == 2){
            y++;
        }
        if(direction == 3){
            x--;
        }
        if(direction == 4){
            x++;
        }
        Thread.sleep(33);
        repaint();

    }

    void runTheGame() throws Exception {
        while(y!=0){
            move(direction=1);
        }

    }



    public static void main(String[] args) throws Exception{
        Main main= new Main();
        main.runTheGame();
    }
    Main() throws Exception{
        JFrame frame = new JFrame( "Dendy Tanks");
        frame.setMinimumSize(new Dimension(BF_WIDTH,BF_HEIGHT+22));
        frame.getContentPane().add(this);
        frame.setLocation(0,0);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.red);
        g.fillRect(x,y,64,64);

        g.setColor(Color.green);
        if(direction==1){
            g.fillRect(x+20,y,24,34);
        } else if(direction ==2){
            g.fillRect(x+20,y+30,24,34);
        }else if (direction ==3){
            g.fillRect(x,y+20,34,24);
        }else{
            g.fillRect(x+30,y+20,34,24);
        }
        g.setColor(Color.ORANGE);
        g.fillRect(bulletX,bulletY, 14,14);

    }

}