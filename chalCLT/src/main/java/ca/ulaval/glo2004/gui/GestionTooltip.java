
package ca.ulaval.glo2004.gui;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.UUID;
import javax.swing.JPanel;
import javax.swing.JToolTip;

/**
 *
 * @author will
 */
public class GestionTooltip extends JPanel {
    private JToolTip tooltip; 
    private ArrayList<Polygon> polyList = new ArrayList<Polygon>();
    private boolean flagTooltip = true;
    

    public GestionTooltip()
    {
        tooltip = new JToolTip();
        tooltip = (JToolTip) this.add(tooltip);
        tooltip.setSize(200,70);
        tooltip.setTipText("");
        tooltip.setVisible(true);
        tooltip.setLocation(0, 0);
        //repaint();
    }
    public void updateTooltipInfo(Point positionSouris, ArrayList<Polygon> polyList, ArrayList<String> nomList, AffineTransform transform){
        boolean detecte = false;
        for(int i = 0; i < polyList.size(); i++)
        {
            if(isSourisDansPoly(polyList.get(i),positionSouris,transform))
            {       
                tooltip.setTipText(nomList.get(i));
                detecte = true;
            }

        }
        if(!detecte)
            tooltip.setTipText("Aucun polygon détecté");
        
        this.polyList = polyList;
        suivreSouris(positionSouris);

    }
    
    public void suivreSouris(Point positionSouris)
    {
        int tooltipX = positionSouris.x+12;
        int tooltipY = positionSouris.y+32;
        tooltip.setLocation(tooltipX, tooltipY);
        repaint();
    }
    
    public Polygon selectPolygon(Point positionSouris,ArrayList<Polygon> polyList, ArrayList<String> nomList, AffineTransform transform)
    {
        Polygon selected = null;
        int polyIndex = -1;
        for(int i = 0; i < polyList.size(); i++)
        {
            if(isSourisDansPoly(polyList.get(i),positionSouris,transform))
            {       
                polyIndex = i;
            }
        }
        
        for(int i = 0; i< polyList.size(); i++)
        {
            if(polyIndex == i)
            {
                selected = polyList.get(i);
            }
        }
        return selected;
    }
    
    public boolean isSourisDansPoly(Polygon polygon, Point positionSouris, AffineTransform transform)
    {
        boolean estDansPoly = false;
        if(polygon != null)
        {
            Polygon poly = new Polygon(polygon.xpoints,polygon.ypoints,polygon.npoints);

                // Transform les points d'un polygone pour ceux affichés
            for(int j = 0; j < poly.npoints; j++) {
                int x = poly.xpoints[j];
                int y = poly.ypoints[j];
                Point pointSrc = new Point(x, y);
                Point pointDest = new Point();

                transform.transform(pointSrc, pointDest);

                poly.xpoints[j] = pointDest.x;
                poly.ypoints[j] = pointDest.y;
            }

            if(poly.contains(positionSouris))
            {       
                estDansPoly = true;
            }
        }
        return estDansPoly;
    }
               
    public void toggleTooltip(){
        flagTooltip = !flagTooltip;
        tooltip.setVisible(flagTooltip);
    }
    
    public UUID getPolygonID(Polygon poly,ArrayList<Polygon> polyList, ArrayList<String> nomList)
    {
        UUID id = null;
        for(int i = 0; i < polyList.size(); i++)
        {
            Polygon polygon = polyList.get(i);
            if(poly != null && polygon.xpoints == poly.xpoints && polygon.ypoints == poly.ypoints)
            {
                int debutIndex = nomList.get(i).indexOf("(");
                int finIndex = nomList.get(i).indexOf(")");
                if(debutIndex < finIndex)
                id = UUID.fromString(nomList.get(i).substring(debutIndex+1, finIndex));
            }
        }
        return id;
    }
    

}
