/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.domain.utils;

/**
 *
 * @author Thomas Ouellet
 */
public class ConvertisseurImperial {
    public static final int DPI = 4; // DPI veut dire "dots per inch". Ici on a 4 pixels pour un pouce
    public static double zoom = 1;
    
    public static int poucesEnPixels(double pouces) {
        return (int) (pouces * DPI);
    }

    public static double pixelsEnPouces(int pixels) {
        return pixels / DPI;
    }
}
