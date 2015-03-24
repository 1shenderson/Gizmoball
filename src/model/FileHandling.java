
package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.gizmo.Absorber;
import model.gizmo.Gizmo;

public class FileHandling {

	public void save(ArrayList<Gizmo> gizmoList, ArrayList<Ball> ballsList, String fileName){

		String command = "";

		try{
			PrintWriter pw = new PrintWriter(fileName, "UTF-8");
			for(Ball b: ballsList){
				double x = b.getExactX();
				double y = b.getExactY();
				double xv = b.getXVelocity();
				double yv = b.getYVelocity();
				command = "Ball" + " " + b.getID() + " " + x + " " + y + " " + xv + " " + yv;
			}
			for(Gizmo g: gizmoList){
				String gizmoType = g.getType();
				String gizmoID = g.getID();
				if(gizmoType.equals("Absorber")){
					Absorber a = (Absorber) g;
					int x = a.getX();
					int y = a.getY();
					int width = a.getWidth();
					int height = a.getHeight();
					command = gizmoType + " " + gizmoID + " " + x + " " + y + " " + width + " " + height;
				}
				else if(gizmoType.equals("KeyConnect")){
					command = gizmoType + " " + gizmoID;
				}
				else if(gizmoType.equals("Connect")){
					command = gizmoType + " " + gizmoID;
				}
				else if(!gizmoType.equals("Rotate")){
					int x = g.getX();
					int y = g.getY();
					command = gizmoType + " " + gizmoID + " " + x + " " + y;
				}
				pw.println(command);
			}
			pw.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<ArrayList<Object>> load(File file){

		ArrayList<ArrayList<Object>> gizmoList = new ArrayList<ArrayList<Object>>();

		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNextLine()){
				ArrayList<Object> gizmoInfo = new ArrayList<Object>();
				String type = sc.next();
				String id = sc.next();
				gizmoInfo.add(type);
				gizmoInfo.add(id);

				if(type.equals("Ball")){
					double x = sc.nextDouble();
					double y = sc.nextDouble();
					double xv = sc.nextDouble();
					double yv = sc.nextDouble();
					gizmoInfo.add(x);
					gizmoInfo.add(y);
					gizmoInfo.add(xv);
					gizmoInfo.add(yv);
				}
				else if(type.equals("Absorber")){
					int x = sc.nextInt();
					int y = sc.nextInt();
					int x2 = sc.nextInt();
					int y2 = sc.nextInt();
					gizmoInfo.add(x);
					gizmoInfo.add(y);
					gizmoInfo.add(x2);
					gizmoInfo.add(y2);
				}
				else if(type.equals("KeyConnect")){
					int keyNumber = sc.nextInt();
					String direction = sc.next();
					String gizmoID = sc.next();
					gizmoInfo.add(keyNumber);
					gizmoInfo.add(direction);
					gizmoInfo.add(gizmoID);
				}
				else if(type.equals("Connect")){
					String gizmoID = sc.next();
					gizmoInfo.add(gizmoID);
				}
				else if(!type.equals("Rotate")){
					int x = sc.nextInt();
					int y = sc.nextInt();
					gizmoInfo.add(x);
					gizmoInfo.add(y);
				}
				gizmoList.add(gizmoInfo);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return gizmoList;
	}
}
