
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

//main game class
public class Game implements Runnable{
    
    private Display display;
    private Thread thread;
    
    private boolean running  = false;
    public int width, height;
    private String title;
    
    private BufferStrategy bufferStrategy;
    private Graphics g;
    
    private BufferedImage backgroundImage;
    
    public Game(String title, int width, int height) {
		
	this.width = width;
	this.height = height;
        this.title = title;
    }
    
    private void init(){//get things ready for the game
        display = new Display(title, width, height);
        backgroundImage = ImageLoader.loadImage("/Images/bg1.png");
    }
    
    private void update(){//tick
        
    }
    private void render(){
        bufferStrategy =  display.getCanvas().getBufferStrategy();
        
        if(bufferStrategy == null){
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        
        g = bufferStrategy.getDrawGraphics();//draw
        
        //clear
        g.clearRect(0, 0, width, height);
        
        //start
       g.drawImage(backgroundImage, 0, 0, null);
        
        //end
        bufferStrategy.show();
        g.dispose();
    }
    
    public void run(){
        init();
        
        while(running){
            update();
            render();  
            System.out.println("Running");
        }
        stop();
    }
    
    public synchronized void start(){
        
        if(running)
            return;      
        
        running = true;
        thread = new Thread(this);
        thread.start();
    }
    
    public synchronized void stop(){
        
        if(!running)
            return;
        
        running = false;
        try{
            thread.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}