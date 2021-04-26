package com.schutzchess.main;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {

	 LinkedList <RenderObject> object = new LinkedList<RenderObject>();
	 
	 public void tick() {
		/* for(int i = 0;i<object.size(); i++) {
			 RenderObject temp = object.get(i);
			 temp.tick();
		 }*/
		 
	 }
	 
	 public void render(Graphics g) {
		 for (int i = 0; i<object.size();i++) {
			 RenderObject temp = object.get(i);
			 temp.render(g);
		 }
	 }
	 
	 public void addObject(RenderObject obj) {
		  this.object.add(obj);
	 }
	 
	 public void removeObject(RenderObject obj) {
		 this.object.remove(obj);
	 }
	
}
