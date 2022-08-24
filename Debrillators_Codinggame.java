//Coding games Defibrillators Exercise

import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        String LON = in.next();
		//Cast current longitude from string to double
        double cur_lon = Double.parseDouble(LON.replace(",", "."));
        String LAT = in.next();
		//cast current latitude from string to double
        double cur_lat = Double.parseDouble(LAT.replace(",", "."));
        int N = in.nextInt();
        if (in.hasNextLine()) {
            String n = in.nextLine();
        }
        double min = 999999999;
        String loc_name = "";
        for (int i = 0; i < N; i++) {
            String DEFIB = in.nextLine();
            String[] parts = DEFIB.split(";");
            //cast defibrillator longitude and latitude from string to double
            double def_lon = Double.parseDouble(parts[4].replace(",", "."));
            double def_lat = Double.parseDouble(parts[5].replace(",", "."));
            //calculate distance
            double x = (def_lon-cur_lon)*Math.cos((cur_lat+def_lat)/2);
            double y = def_lat - cur_lat;
            double d = Math.sqrt(Math.pow(x,2) + Math.pow(y,2)) * 6371;
			//find the min distance
            if (d < min){
                min = d;
                loc_name = parts[1];
            }
        }
        System.out.println(loc_name);

        // Write an answer using System.out.println()
        // To debug: System.err.println("Debug messages...");
    }
}