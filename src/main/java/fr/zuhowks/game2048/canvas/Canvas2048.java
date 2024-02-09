package fr.zuhowks.game2048.canvas;

import fr.zuhowks.game2048.game.GameManager;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

public class Canvas2048 extends JPanel{
	
    //private final Jeu2048 jeu;
    private final int boxTaille; //reference Ã  la dicipline
    private final GameManager game;
    int xDepart;
    int yDepart;
    Map<Integer, Color> colorBoxes = new HashMap<>();
    
    public Canvas2048(GameManager game) {
    	this.boxTaille =400;
    	this.game = game;
    	this.xDepart = 0;
    	this.yDepart = 0;
    	this.colorBoxes.put(0, Color.LIGHT_GRAY);
    	this.colorBoxes.put(2, new Color(224,224,224));
    	this.colorBoxes.put(4, new Color(255, 229, 204));
    	this.colorBoxes.put(8, new Color(255, 178, 102));
    	this.colorBoxes.put(16, new Color(255, 153, 51));
    	this.colorBoxes.put(32, new Color(255, 102, 102));
    	this.colorBoxes.put(64, new Color(255, 0, 0));
    	this.colorBoxes.put(128, new Color(255, 255, 153));
    	this.colorBoxes.put(256, new Color(255, 255, 102));
    	this.colorBoxes.put(512, new Color(235, 220, 100));
    	this.colorBoxes.put(1024, new Color(230, 205, 85));
    	this.colorBoxes.put(2048, new Color(230, 195, 20));
    }

@Override
protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	Graphics2D g2D = (Graphics2D) g;
	grille(0, 0, g2D);
	score(0, 500, this.game.getClientGame().getScore(), 5F, g2D);
}
public RoundRectangle2D createRoundedRect(int x, int y, double w, double h, Graphics g2D) {
    return new RoundRectangle2D.Double(x, y, w, h, 15, 15);
}
public void Boxes(int x, int y, Graphics2D g2D, int nb) {
	
	double squareSize = 9*boxTaille/40;
	String numberAff = ""+nb;
	float sizeFont = (float) (squareSize/60);
	
	g2D.setColor(colorBoxes.get(nb));
	g2D.fill(createRoundedRect(x, y, squareSize, squareSize, g2D));
	g2D.setFont(g2D.getFont().deriveFont(g2D.getFont().getSize() * sizeFont));
	textCenteredRect(x, y, squareSize, squareSize, numberAff, numberAff, g2D);
	g2D.setFont(g2D.getFont().deriveFont(g2D.getFont().getSize() / sizeFont));
    
}
public void textCenteredRect(int x, int y, double squareSize, double squareSize2, String txtAffMax, String txtAff, Graphics2D g2D){
	FontMetrics metrics = g2D.getFontMetrics();
    int textX = (int) (x + (squareSize - metrics.stringWidth(txtAffMax))/2);
    int textY = (int) (y + ((squareSize2 - metrics.getHeight())/2) + metrics.getAscent());
    g2D.setColor(Color.BLACK);
    g2D.drawString(txtAff, textX, textY);
}

public void grille(int x, int y, Graphics2D g2D) {
	int spaceLines = boxTaille/40;
	int jumpBoxes = boxTaille/4;
	
	g2D.setColor(Color.DARK_GRAY);
	g2D.fill(createRoundedRect(xDepart, yDepart, boxTaille + spaceLines, boxTaille + spaceLines, g2D));

	int[][] grilleClient = this.game.getClientGame().getGridCopy();
	
	x = xDepart + spaceLines;
	y += yDepart + spaceLines;

	for(int i = 0; i < 4; i++) {
		for(int j = 0; j < 4; j++) {
			Boxes(x, y, g2D, grilleClient[i][j]);
			x += jumpBoxes;	
		}
		x = xDepart + spaceLines;
		y += jumpBoxes;
	}
	x = 0;
	y = 0;
}
public void score(int x, int y, int nbScore, Float sizeFont, Graphics2D g2D) {
	String scoreAffMax = "Score: 99999";
	g2D.setColor(Color.BLACK);
	g2D.setFont(g2D.getFont().deriveFont(g2D.getFont().getSize() * sizeFont));
	FontMetrics metrics = g2D.getFontMetrics();
	g2D.setStroke(new BasicStroke(sizeFont));
	g2D.drawRect(x, y, metrics.stringWidth(scoreAffMax)+10, metrics.getHeight());
	if (nbScore <= 99999) {
		textCenteredRect(x+5, y, metrics.stringWidth(scoreAffMax), metrics.getHeight(), scoreAffMax, "Score: "+nbScore, g2D);
	}
	else {
		textCenteredRect(x+5, y, metrics.stringWidth(scoreAffMax), metrics.getHeight(), scoreAffMax, "You broke it !", g2D);
	}
	g2D.setFont(g2D.getFont().deriveFont(g2D.getFont().getSize() / sizeFont));
	
}
}
