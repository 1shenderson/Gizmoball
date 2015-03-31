
package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import model.Ball;
import model.gizmo.Absorber;
import model.gizmo.Gizmo;

public class FileHandling {

	public void save(List<Gizmo> gizmoList, List<Ball> ballsList, List<ArrayList<Object>> triggersList, List<ArrayList<Object>> connectList, Map<String, Integer> rotateMap, String fileName, double gravity, double friction1, double friction2){

		String command = "";

		try{
			PrintWriter pw = new PrintWriter(fileName, "UTF-8");
			for(Ball b: ballsList){
				command = b.toString();
				pw.println(command);
			}
			for(Gizmo g: gizmoList){
				command = g.toString();
				pw.println(command);
			}
			for(ArrayList<Object> t: triggersList){
				command = t.get(0) + " key " + t.get(3) + " " + t.get(2) + " " + t.get(1);
				pw.println(command);
			}
			for(ArrayList<Object> c: connectList){
				command = c.get(0) + " " + c.get(1) + " " + c.get(2);
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
			pw.println("Gravity " + gravity);
			pw.println("Friction " + friction1 + " " + friction2);
			pw.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<ArrayList<Object>> load(File file){

		ArrayList<ArrayList<Object>> gizmoList = new ArrayList<ArrayList<Object>>();
		Set<String> idSet = new HashSet<String>();
		idSet.add("OuterWalls");

		try {
			Scanner sc = new Scanner(file);
			while(sc.hasNext()){
				double grav, frict1, frict2, xExact, yExact, xv, yv;
				int x, y, keyNumber, x2, y2;
				String type, gizmoID, id, direction; 
				ArrayList<Object> gizmoInfo = new ArrayList<Object>();
				type = sc.next();
				gizmoInfo.add(type);

				switch(type){
				case "Gravity":
					grav = sc.nextDouble();
					gizmoInfo.add(grav);
					gizmoList.add(gizmoInfo);
					continue;
				case "Friction":
					frict1 = sc.nextDouble();
					frict2 = sc.nextDouble();
					gizmoInfo.add(frict1);
					gizmoInfo.add(frict2);
					gizmoList.add(gizmoInfo);
					continue;
				default:
					id = sc.next();
					gizmoInfo.add(id);
				}
				switch(type){
				case "Rotate":
					gizmoList.add(gizmoInfo);
					continue;
				case "Ball":
					xExact = sc.nextDouble();
					yExact = sc.nextDouble();
					xv = sc.nextDouble();
					yv = sc.nextDouble();
					gizmoInfo.add(xExact);
					gizmoInfo.add(yExact);
					gizmoInfo.add(xv);
					gizmoInfo.add(yv);
					break;
				case "Absorber":
					x = sc.nextInt();
					y = sc.nextInt();
					x2 = sc.nextInt();
					y2 = sc.nextInt();
					gizmoInfo.add(x);
					gizmoInfo.add(y);
					gizmoInfo.add(x2);
					gizmoInfo.add(y2);
					break;
				case "KeyConnect":
					gizmoInfo.remove(1);
					keyNumber = sc.nextInt();
					direction = sc.next();
					gizmoID = sc.next();
					gizmoInfo.add(keyNumber);
					gizmoInfo.add(direction);
					gizmoInfo.add(gizmoID);
					gizmoList.add(gizmoInfo);
					continue;
				case "Connect":
					gizmoID = sc.next();
					gizmoInfo.add(gizmoID);
					gizmoList.add(gizmoInfo);
					continue;
				default:
					x = sc.nextInt();
					y = sc.nextInt();
					gizmoInfo.add(x);
					gizmoInfo.add(y);
				}
				try {
					uniqueIDCheck(idSet, id);
					idSet.add(id);
				} catch (IOException e) {
					if(id.equals("OuterWalls")){
						System.out.println("OuterWalls is a special identifier and cannot be used for other gizmos. The gizmo was not added.");
					}
					else{
						System.out.println("Gizmo ID already exists for another gizmo, second gizmo with id: " + id + " will not be added.");	
					}
					continue;
				}
				gizmoList.add(gizmoInfo);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return gizmoList;
	}

	private void uniqueIDCheck(Set<String> idSet, String id) throws IOException{
		if(idSet.contains(id)){
			throw new IOException();
		}
	}
}
