
package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import model.gizmo.Absorber;
import model.gizmo.Gizmo;

public class FileHandling {

	public void save(ArrayList<Gizmo> gizmoList, ArrayList<Ball> ballsList, List<ArrayList<Object>> triggersList, Map<String, Integer> rotateMap, String fileName){

		String command = "";

		try{
			PrintWriter pw = new PrintWriter(fileName, "UTF-8");
			for(Ball b: ballsList){
				double x = b.getExactX();
				double y = b.getExactY();
				double xv = b.getXVelocity();
				double yv = b.getYVelocity();
				command = "Ball" + " " + b.getID() + " " + x + " " + y + " " + xv + " " + yv;
				pw.println(command);
			}
			for(Gizmo g: gizmoList){
				String gizmoType = g.getType();
				String gizmoID = g.getID();
				int x;
				int y;
				if(gizmoType.equals("LeftFlipper")){
					x = (g.getX() - 6) / 25;
					y = g.getY() / 25;
				}
				else if(gizmoType.equals("RightFlipper")){
					x = (g.getX() - 43) / 25;
					y = g.getY() / 25;
				}
				else{
					x = g.getX() / 25;
					y = g.getY() / 25;
				}
				if(gizmoType.equals("Absorber")){
					Absorber a = (Absorber) g;
					int width = a.getWidth() / 25;
					int height = a.getHeight() / 25;
					command = gizmoType + " " + gizmoID + " " + x + " " + y + " " + width + " " + height;
				}
				else{
					command = gizmoType + " " + gizmoID + " " + x + " " + y;
				}
				
				pw.println(command);
			}
			for(ArrayList<Object> t: triggersList){
				if(t.get(0).equals("KeyConnect")){
					command = t.get(0) + " key " + t.get(1) + " " + t.get(2) + " " + t.get(3);
				}
				else if(t.get(0).equals("Connect")){
					command = t.get(0) + " " + t.get(1) + " " + t.get(2);
				}
				pw.println(command);
			}
			Iterator<Entry<String, Integer>> it = rotateMap.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>)it.next();
		        int size = (int) pair.getValue();
		        for(int i = 0; i < size; i++){
		        	command = "Rotate" + " " + pair.getKey();
		        	pw.println(command);
		        }
		        it.remove();
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
			while(sc.hasNext()){
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
