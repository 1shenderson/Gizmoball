

package controller;

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
				int x = sc.nextInt();
				int y = sc.nextInt();
				int w = sc.nextInt();
				
				gizmoInfo.add(type);
				gizmoInfo.add(id);
				gizmoInfo.add(x);
				gizmoInfo.add(y);
				gizmoInfo.add(w);
				
				gizmoList.add(gizmoInfo);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return gizmoList;
	}
}
