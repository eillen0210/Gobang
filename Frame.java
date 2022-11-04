package chess;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

class Frame extends JFrame implements MouseListener {//使用鼠標事件處理創建JFrame窗口
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	int x, y;// 棋子座標，黑子先下
	int x1,y1;//鼠標座標
	int flag = 1;// 判斷結束，1=開始，0=结束
	
	boolean canPlay = true;
	
	int[][] allChess = new int[14][14];// 没有棋子=0，黑子=1，白棋=2
	
	int chessSum = 0;// 棋子總數
	BufferedImage bgImage = null;
	BufferedImage BImage = null;
	BufferedImage WImage = null;

	public Frame() {
		this.setTitle("五子棋");
		setSize(350, 350);
		try {//背景
			bgImage = ImageIO.read(new File("C:\\Users\\USER\\Desktop\\chess\\1.jpg"));
			BImage = ImageIO.read(new File("C:\\Users\\USER\\Desktop\\chess\\2.jpg"));
			WImage = ImageIO.read(new File("C:\\Users\\USER\\Desktop\\chess\\2.jpg"));
		} catch (IOException e1) {
			e1.printStackTrace();//列印異常的堆疊資訊
		}
		addMouseListener(this);//進行滑鼠事件的處理
		setVisible(true); 
	}
	public void paint(Graphics g) {
		// 棋格
		g.drawImage(bgImage,0,0,this);//放圖片
		
		for(int colum=58;colum<330 ;colum=colum+30){//colum
		 g.drawLine(38,colum,308,colum);
		}
		for(int row=38;row<330;row=row+30){//row
		 g.drawLine(row, 58,row, 329);
		}

		//下棋
		if(chessSum%2==1 && chessSum>0){
			System.out.print("黑：");
		}else if(chessSum%2==0 && chessSum>0){
			System.out.print("白：");
		}
		
		for(int i=0;i<allChess.length;i++) { 
			for(int j=0;j<allChess.length;++j) {
				if(allChess[i][j]==1) {//黑子
					int tempX=i*30+38;
					int tempY=j*30+58;
					g.drawImage(BImage,0,0,this)
					 g.setColor(Color.black);
					 g.fillOval(tempX-13,tempY-13,25,25);
					
				}

				if(allChess[i][j]==2) {//白子
					int tempX=i*30+38;
					int tempY=j*30+58;
					g.setColor(Color.white);
					g.fillOval(tempX-13,tempY-13,25,25);

				}

			}
		}
		//下的那個棋子用红框表示
		if(chessSum>0) {
		 g.setColor(Color.red);
		 g.drawOval(x*30+38-13, y*30+58-13, 25,25);
		}
		chessSum++;
		System.out.println("總數為"+(chessSum-1));
	}

	
	public void mouseClicked(MouseEvent e) {//處理滑鼠點擊事件
			 x=e.getX();
		     y=e.getY();
		 
       if(canPlay) {
		 	if(x>=38 && x<=400 && y>=58 && y<=400) {
				x=(x-38)/30;
				y=(y-58)/30;
				System.out.println("("+x+","+y+")");
				if(allChess[x][y]==0){//此處没有棋子->可以下
					if(flag==1) {//黑子
						allChess[x][y]=1;
						this.checkFive();//判斷五子相連
						
						flag=2;//換白子
					}else {
						allChess[x][y]=2;//白子
						this.checkFive();//判斷五子相連
						
						flag=1;//換黑子
					}
					this.repaint();//畫出棋子
				}
	   		}
      	}
    	  
    }
	
	//判斷勝負
    public  void checkFive(){
    	int color=allChess[x][y];
    	int count=1;//計算棋子連成個數
    	
    	for(int i=1;i<5;i++) {//橫向右邊，相同?
    		if(x>=6)//6+4=10，無法再擺放
    			break;
    		if(color==allChess[x+i][y]) {
    			count++;
    		}
    		checkWin(count);
    	}
    	count=1;//歸零
    	
    	
    	for(int i=1;i<5;i++) {//橫向左邊，相同?
    		if(x<=3)//無法再擺放
    			break;
    		if(color==allChess[x-i][y]) {
    			count++;
    		}
    		checkWin(count);
    	}
    	count=1;//歸零
    	
    	for(int i=1;i<5;i++) {//直向往下，相同?
    		if(y>=6)//無法再擺放
    			break;
    		if(color==allChess[x][y+i]) {
    			count++;
    		}
    		checkWin(count);
    	}
    	count=1;//歸零

    	for(int i=1;i<5;i++) {//直向往上，相同?
    		if(y<=3)//無法再擺放
    			break;
    		if(color==allChess[x][y-i]) {
    			count++;
    		}
    		checkWin(count);
    	}
    	count=1;//歸零

    	for(int i=1;i<5;i++) {//右斜往上，相同?
    		if(y<=3||x>=6)//無法再擺放
    			break;
    		if(color==allChess[x+i][y-i]) {
    			count++;
    		}
    		checkWin(count);
    	}
    	count=1;//歸零
    	
    	for(int i=1;i<5;i++) {//左斜往下，相同?
    		if(x<=3||y>=6)//無法再擺放
    			break;
    		
    		if(color==allChess[x-i][y+i]) {
    			count++;
    		}
    		checkWin(count);
    	}
    	count=1;//歸零
    	
    	for(int i=1;i<5;i++) {//左斜往上，相同?
    		if(x<=3||y<=3)//無法再擺放
    			break;
    		if(color==allChess[x-i][y-i]) {
    			count++;
    		}
    		checkWin(count);
    	}
		count=1;//歸零
		
    	for(int i=1;i<5;i++) {//右斜向下，相同?
    		if(y>=6||x>=6)
    			break;
    		if(color==allChess[x+i][y+i]) {
    			count++;
    		}
    		checkWin(count);

    	}
    	count=1;//歸零
    	
    }
    
	public void mouseEntered(MouseEvent e) {//處理滑鼠進入指定元件事件
		 x1=e.getX();
		 y1=e.getY();
	     if(x1>=38&&x1<=400&&y1>=58&&y1<=400) {
	    	 //38+9*30=308，58+9*30=328
	    	 setCursor(new Cursor(Cursor.HAND_CURSOR));
	    	
	     }
	    
    }
		
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//處理滑鼠離開指定元件事件
	}
 
	public void mousePressed(MouseEvent arg0) {
		//處理按住滑鼠按鈕事件
		
	}
 
	public void mouseReleased(MouseEvent e) {
		//處理釋放滑鼠按鈕事件
 	}

	public void checkWin(int count) {
	    if(chessSum!=100){//是否為平局，總數= 10*10
			if(count>=5) {
				if(allChess[x][y]==1) {
					JOptionPane.showMessageDialog(this, "黑子勝出");
				}else if(allChess[x][y]==2) {
					JOptionPane.showMessageDialog(this, "白子勝出");
				}
				setVisible(true); 
				dispose();
				JOptionPane.showMessageDialog(this, "進入下一輪");
				canPlay=false;//结束
				new Frame();
			}
		}else{
				JOptionPane.showMessageDialog(this, "- 平局 -");
				setVisible(true); 
				dispose();
				JOptionPane.showMessageDialog(this, "進入下一輪");
				canPlay=false;//结束
				new Frame();
		}		
	   
	}
}