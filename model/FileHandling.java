
package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandling {

	public void save(ArrayList<ArrayList<Object>> gizmoList, File file, String fileName){

		String command = "";
		ArrayList<Object> gizmoInfo = new ArrayList<Object>();
		
		try{
			PrintWriter pw = new PrintWriter(fileName, "UTF-8");
			for(int i = 0; i < gizmoList.size(); i++){
				gizmoInfo = gizmoList.get(i);
				for(int j = 0; j < gizmoInfo.size(); j++){
					if(j != (gizmoInfo.size() - 1)){
						command = command + gizmoInfo.get(j) + " ";
					}
					else{
						command = command + gizmoInfo.get(j);
					}
				}
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
				else{
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
