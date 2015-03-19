
package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

import model.gizmo.Gizmo;

public class FileHandling {

	public void save(ArrayList<Gizmo> gizmoList, String fileName){

		String command = "";
		
		try{
			PrintWriter pw = new PrintWriter(fileName, "UTF-8");
			for(int i = 0; i < gizmoList.size(); i++){
				String gizmoType = gizmoList.get(i).getType();
				String gizmoID = gizmoList.get(i).getID();
				int x = gizmoList.get(i).getX();
				int y = gizmoList.get(i).getY();
				command = gizmoType + " " + gizmoID + " " + x + " " + y + " ";
				if(i < gizmoList.size() - 1){
					pw.println(command);
				}
				else {
					pw.printf(command);
				}
				command = "";
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
				else if(!type.equalsIgnoreCase("Rotate")){
					int x = sc.nextInt();
					int y = sc.nextInt();
					gizmoInfo.add(x);
					gizmoInfo.add(y);
				}
				if(type.equals("Line")){
					int w = sc.nextInt();
					gizmoInfo.add(w);
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
