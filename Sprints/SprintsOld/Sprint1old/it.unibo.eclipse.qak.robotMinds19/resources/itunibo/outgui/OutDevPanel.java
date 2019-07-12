package itunibo.outgui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Panel;
import java.awt.TextArea;

/**
 * Un dispositivo di virtuale di uscita.
 */
public class OutDevPanel extends Panel { 
 
protected TextArea  outDev;
protected Color bg;
protected Color fg;
 

public OutDevPanel(  int nRow, int nCol, Color bg, Color fg ){
 
	this.bg = bg;
	this.fg = fg;
	configura(nRow, nCol );
}//OutDevPanel

public OutDevPanel( int nRow, int nCol  ){
 	configura(nRow, nCol);
}//OutDevPanel

/**
* Configurazione.
* La TextArea che rappresenta il dispositivo di uscita
* viene inserita in un JScrollPane per abilitare lo scrolling
*/
protected void configura(int nRow, int nCol){
//ScrollPane scrollPanel;
	outDev        = new TextArea(nRow,nCol);
	outDev.setFont( new Font( "Arial",Font.BOLD,14) );
//	outDev.setBackground(bgColor);
	
	outDev.setBackground(bg); //new Color(16, 12, 66)
	outDev.setForeground(fg);	//new Color(151, 138, 255)

//	scrollPanel = new ScrollPane(  );
//	scrollPanel.add(outDev);
//	setLayout( new java.awt.BorderLayout() );
//	add( "Center",scrollPanel ); //layout di default = FlowLayout
	this.setLayout(new java.awt.BorderLayout());
	add( BorderLayout.CENTER,outDev );
	validate();
}//configura

public synchronized void setBgColor( java.awt.Color c){
	outDev.setBackground(c);
	validate();
}

/**
*	Visualizzazione di una stringa sul dispositivo virtuale di uscita.
*/
public synchronized void print( String msg ){
	outDev.append(msg);
	outDev.validate();
}//print

/**
* Visualizzazione di una stringa sul dispositivo virtuale di uscita
* con ritorno a capo.
*/
public synchronized void println( String msg ){
	outDev.append(msg+"\n");
	outDev.validate();
}//println

/**
* Pulisce.
*/

public synchronized void clear(  ){
	outDev.setText("");
	outDev.validate();
}//clear

public synchronized String read(  ){
	return outDev.getText();
}//read

 
public synchronized String getCurVal() {
	return outDev.getText();
}

 
public void addOutput(String msg) {
	println( msg );	
}

 
public void setOutput(String msg) {
	outDev.setText(msg);
	outDev.validate();
}



}//OutDevPanel
