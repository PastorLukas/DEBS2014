/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.sbapr.debs2014.util;

/**
 *
 * @author LuPa
 */
public class Formatter {
    
    public static String padRight(String str, int padBy, char withChar) {
        padBy += (str != null) ? str.length() : 0;
        return String.format("%1$-" + padBy + "s", str).replace(' ', withChar);
    }
    
    public static String padLeft(String str, int padBy, char withChar) {
        padBy += str.length();
        return String.format("%1$" + padBy + "s", str).replace(' ', withChar);
    }    
    
    public static String alignToCenter(String label, int lineLength, char c) {        
        int len = lineLength;
        len -= (label != null) ? label.length() : 0;
        int left  = len >> 1;
        int right = len - left;                
        return padLeft(padRight(label, right, c),left, c);
    }
}
