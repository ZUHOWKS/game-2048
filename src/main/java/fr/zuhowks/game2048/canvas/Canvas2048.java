package fr.zuhowks.game2048.canvas;

import fr.zuhowks.game2048.game.GameManager;
import fr.zuhowks.game2048.game.server.stats.GameStatus;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

public class Canvas2048 extends JPanel implements PropertyChangeListener {
	
    //private final Jeu2048 jeu;
    private final int boxTaille; //reference Ã  la dicipline
    private final GameManager game;
    int xDepart;
    int yDepart;
    Map<Integer, Color> colorBoxes = new HashMap<>();
    
    public Canvas2048(GameManager game) {


    	this.boxTaille = 400;
    	this.game = game;
    	this.xDepart = 200;
    	this.yDepart = 200;

		this.game.getClientGame().addPropertyChangeListener(this);

		setupBoxColors();
	}

	private void setupBoxColors() {
		this.colorBoxes.put(0, new Color(203, 191, 178));
		this.colorBoxes.put(2, new Color(237, 227, 217));
		this.colorBoxes.put(4, new Color(234, 223, 200));
		this.colorBoxes.put(8, new Color(240, 177, 123));
		this.colorBoxes.put(16, new Color(242, 148, 102));
		this.colorBoxes.put(32, new Color(243, 123, 97));
		this.colorBoxes.put(64, new Color(243, 93, 60));
		this.colorBoxes.put(128, new Color(235, 205, 113));
		this.colorBoxes.put(256, new Color(235, 203, 97));
		this.colorBoxes.put(512, new Color(234, 199, 80));
		this.colorBoxes.put(1024, new Color(237, 196, 63));
		this.colorBoxes.put(2048, new Color(237, 194, 46));
	}

	@Override
protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	Graphics2D g2D = (Graphics2D) g;
	score(200, 0, this.game.getClientGame().getScore(), 5F, g2D);
	grille(0, 0, g2D);
}
public RoundRectangle2D createRoundedRect(int x, int y, double w, double h, Graphics g2D) {
    return new RoundRectangle2D.Double(x, y, w, h, 7.5, 7.5);
}


	/**
	 * Generate boxes
	 *
	 * @param x | position x
	 * @param y | position y
	 * @param g2D | Graphics2D to generate rounded box
	 * @param nb | number which is contained by the box.
	 */
public void generateBox(int x, int y, Graphics2D g2D, int nb) {
	
	double squareSize = (double) (9 * boxTaille) / 40;
	String numberAff = ""+nb;
	float sizeFont = (float) (squareSize/60);
	
	g2D.setColor(colorBoxes.get(nb));
	g2D.fill(createRoundedRect(x, y, squareSize, squareSize, g2D));
	g2D.setFont(g2D.getFont().deriveFont(g2D.getFont().getSize() * sizeFont));

	if (nb > 0) {
		textCenteredRect(x, y, squareSize, squareSize, numberAff, numberAff, g2D);
	}

	g2D.setFont(g2D.getFont().deriveFont(g2D.getFont().getSize() / sizeFont));
    
}
public void textCenteredRect(int x, int y, double squareSize, double squareSize2, String txtAffMax, String txtAff, Graphics2D g2D){
	FontMetrics metrics = g2D.getFontMetrics();
    int textX = (int) (x + (squareSize - metrics.stringWidth(txtAffMax))/2);
    int textY = (int) (y + ((squareSize2 - metrics.getHeight())/2) + metrics.getAscent());
    g2D.setColor(Color.WHITE);
    g2D.drawString(txtAff, textX, textY);
}

public void grille(int x, int y, Graphics2D g2D) {
	int spaceLines = boxTaille/40;
	int jumpBoxes = boxTaille/4;
	
	g2D.setColor(new Color(186, 172, 159));
	g2D.fill(createRoundedRect(xDepart, yDepart, boxTaille + spaceLines, boxTaille + spaceLines, g2D));

	int[][] grilleClient = this.game.getClientGame().getGridCopy();
	
	x = xDepart + spaceLines;
	y += yDepart + spaceLines;

	for(int i = 0; i < 4; i++) {
		for(int j = 0; j < 4; j++) {
			generateBox(x, y, g2D, grilleClient[i][j]);
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
	g2D.setColor(new Color(186, 172, 159));
	g2D.setFont(g2D.getFont().deriveFont(g2D.getFont().getSize() * sizeFont));

	FontMetrics metrics = g2D.getFontMetrics();
	g2D.setStroke(new BasicStroke(sizeFont));
	g2D.fillRect(x, y, metrics.stringWidth(scoreAffMax)+10, metrics.getHeight());
	if (nbScore <= 99999 && this.game.getGameStatus() == GameStatus.IN_GAME) {
		textCenteredRect(x+5, y, metrics.stringWidth(scoreAffMax), metrics.getHeight(), scoreAffMax, "Score: "+nbScore, g2D);
	}
	else if (this.game.getGameStatus() != GameStatus.LOOSE || this.game.getGameStatus() != GameStatus.NOT_IN_GAME) {
		textCenteredRect(x+5, y, metrics.stringWidth(scoreAffMax), metrics.getHeight(), scoreAffMax, "You broke it !", g2D);
	}
	g2D.setFont(g2D.getFont().deriveFont(g2D.getFont().getSize() / sizeFont));
	
}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.repaint();
	}
}
