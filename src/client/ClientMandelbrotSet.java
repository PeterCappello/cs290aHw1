/*
 * The MIT License
 *
 * Copyright 2015 peter.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package client;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.rmi.RemoteException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import tasks.TaskMandelbrotSet;

/**
 *
 * @author Peter Cappello
 */
public class ClientMandelbrotSet extends Client
{
    private static final double LOWER_LEFT_X = -2.0;
    private static final double LOWER_LEFT_Y = -2.0;
    private static final double EDGE_LENGTH = -2.0;
    private static final int N_PIXELS = 512;
    private static final int ITERATION_LIMIT = 128;
    
    public ClientMandelbrotSet() throws RemoteException 
    { 
        super( "Mandelbrot Set Visualizer", 
                new TaskMandelbrotSet( LOWER_LEFT_X, LOWER_LEFT_Y, EDGE_LENGTH, N_PIXELS, ITERATION_LIMIT) ); 
    }
    
    /**
     * Run the MandelbrotSet visualizer client.
     * @param args unused 
     * @throws java.rmi.RemoteException 
     */
    public static void main( String[] args ) throws Exception
    {  
        System.setSecurityManager( new SecurityManager() );
        final ClientMandelbrotSet client = new ClientMandelbrotSet();
        Integer[][] value = (Integer[][]) client.runTask();
        client.add( client.getLabel( value ) );
    }
    
    public JLabel getLabel( Integer[][] counts )
    {
        final Image image = new BufferedImage( N_PIXELS, N_PIXELS, BufferedImage.TYPE_INT_ARGB );
        final Graphics graphics = image.getGraphics();
        for ( int i = 0; i < counts.length; i++ )
            for ( int j = 0; j < counts.length; j++ )
            {
                graphics.setColor( getColor( counts[i][j] ) );
                graphics.fillRect( i, j, 1, 1 );
            }
        final ImageIcon imageIcon = new ImageIcon( image );
        return new JLabel( imageIcon );
    }
    
    
    private Color getColor( int i )
    {
        if ( i == ITERATION_LIMIT )
        {
            return Color.BLACK;
        }
        return new Color( i % ITERATION_LIMIT, 0, ( ITERATION_LIMIT - i ) % ITERATION_LIMIT ); 
    }
}
