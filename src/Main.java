import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;

public class Main extends JPanel{
    final int BF_WIDTH=576;
    final int BF_HEIGHT=576;
    final int QADRANT_SIZE=64;

    final int UP=1;
    final int DOWN=2;
    final int LEFT=3;
    final int RIGHT=4;
    final int TOP_Y= BF_HEIGHT-QADRANT_SIZE;
    final int TOP_X= BF_WIDTH-QADRANT_SIZE;

    String[][] objects = {
            {"B", "B", "B","G", "G" , "W", "W", "G", "B"},
            {"B", "G", "B","B", "G" , "W", "W", "G", "B"},
            {"B", "B", "B","G", "G" , "W", "W", "G", "G"},
            {"G", "B", "G","G", "G" , "W", "W", "W", "B"},
            {"B", "G", "B","G", "G" , "W", "G", "W", "G"},
            {"B", "B", "B","G", "G" , "W", "W", "W", "B"},
            {"G", "B", "G","G", "G" , "W", "W", "G", "B"},
            {"B", "B", "G","B", "B" , "W", "W", "G", "B"},
            {"B", "G", "G","B", "B" , "W", "W", "G", "B"},
};

    //1-Up 2-Down 3-Left 4-Right
    int direction =3;
    int bulletX= -100;
    int bulletY=-100;
    int tankX=256;
    int tankY =256;
    int fire=0;

    void move(int direction) throws Exception{
        this.direction=direction;

        if(dontCanMove()){
            System.out.println("Can't move!!!!");
            fire();
            return;
        }
        for(int i=0; i<64;i++) {
            if (direction == 1) {
                tankY--;
            }
            if (direction == 2) {
                tankY++;
            }
            if (direction == 3) {
                tankX--;
            }
            if (direction == 4) {
                tankX++;
            }
            Thread.sleep(33);
            repaint();
        }
    }

    boolean dontCanMove(){

        return (direction == UP && tankY==0) || (direction ==DOWN && tankY == TOP_Y)
                || (direction==LEFT && tankX==0) ||(direction==RIGHT && tankX==TOP_X)
                || nextObject(direction).equals("B");
    }
    String nextObject(int direction){
        int y=tankY;
        int x=tankX;
        switch (direction){
            case UP:
                y-=QADRANT_SIZE;
                break;
            case DOWN:
                y+=QADRANT_SIZE;
                break;
            case LEFT:
                x-=QADRANT_SIZE;
                break;
            case RIGHT:
                x+=QADRANT_SIZE;
                break;
        }
        return objects[y/QADRANT_SIZE][x/QADRANT_SIZE];
    }

    boolean processInterception(){
        int x=bulletX/64;
        int y=bulletY/64;
        if(objects[y][x].equals("B")){
           objects[y][x] = "G";
           return true;
        }
        return false;
    }

    void fire() throws Exception {
        bulletX=tankX+25;
        bulletY=tankY+25;
        while(bulletX>0 && bulletX<BF_WIDTH && bulletY>0 && bulletY<BF_HEIGHT){
            if(direction==1){
                bulletY--;
            } else if(direction==2){
                bulletY++;
            } else if(direction==3){
                bulletX--;
            } else{
                bulletX++;
            }

            if (processInterception()) {
                destroyBullet();
                repaint();
            }
            Thread.sleep(20);
            repaint();
        }
        destroyBullet();
        repaint();
    }
    void destroyBullet(){
        bulletY=0;
        bulletX=0;

    }

    void runTheGame() throws Exception {
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
        while(tankX!=0){
            move(direction);
        }
        fire();
    }



    public static void main(String[] args) throws Exception{
        Main main= new Main();
        main.runTheGame();
    }


    Main() throws Exception{
        JFrame frame = new JFrame( "Dandy Tanks");
        frame.setMinimumSize(new Dimension(BF_WIDTH,BF_HEIGHT+22));
        frame.getContentPane().add(this);
        frame.setLocation(0,0);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
    class FieldKeyListener extends KeyAdapter{
       @Override
        public void keyPressed(KeyEvent e){
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key==KeyEvent.VK_LEFT){
                direction=LEFT;
            }
           if(key==KeyEvent.VK_RIGHT){
               direction=RIGHT;
           }
           if(key==KeyEvent.VK_DOWN){
               direction=DOWN;
           }if(key==KeyEvent.VK_UP){
               direction=UP;
           }
           if(key==KeyEvent.VK_W){
               fire=1;
           }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Environment
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(objects[i][j].equals("B")){
                g.setColor(new Color(238, 133, 41));
                }else if(objects[i][j].equals("G")){
                g.setColor(new Color(35, 129, 58));
                }else{
                g.setColor(new Color(41, 169, 238));
            }
            g.fillRect(j*QADRANT_SIZE,i*QADRANT_SIZE,QADRANT_SIZE,QADRANT_SIZE);
        }
        }

        //draw Tank
        g.setColor(Color.red);
        g.fillRect(tankX,tankY,QADRANT_SIZE,QADRANT_SIZE);
        g.setColor(Color.green);
        if(direction==1){
            g.fillRect(tankX+20,tankY,24,34);
        } else if(direction ==2){
            g.fillRect(tankX+20,tankY+30,24,34);
        }else if (direction ==3){
            g.fillRect(tankX,tankY+20,34,24);
        }else{
            g.fillRect(tankX+30,tankY+20,34,24);
        }
        g.setColor(Color.ORANGE);
        g.fillRect(bulletX,bulletY, 14,14);


    }

}