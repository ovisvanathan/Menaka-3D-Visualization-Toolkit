/*******************************************************************************
 * Copyright (c) 2014 Mamallan Software LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Affero GNU Public License 
 * which accompanies this distribution, and is available at
 * http://www.affero.org/oagpl.html
 * 
 * This license permits the use of this code for educational,
 * non-commercial work. Users needing it for commercial purposes can
 * contact us at om.visvanathan@mamallansoft.com.
 *  
 * 
 * Copyright (C) 2016-2020 Almanac.com
 * *
 * Version: AGPL
 *
 * The names Mamallan Software, Meghrules, Almanac, Ingenuity are copyrighted
 * and any use of the above withour prior permission is forbidden.
 * The contents of this file may be used under the terms of
 * the AGPL only.
 * A copy of the Affero GPL license can be obtained from  * http://www.affero.org/oagpl.html
 ******************************************************************************/
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Iterator;

import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;

float xangle = .01;
float angle = .05;

int scalefactor = 80;

int CUBESIZE = 3;

Cube cube;

// Cube [] rotationCubes;

// PShape [] rotationArrows; 

PButton pbstart;
PButton pbpause;
PButton pbstop;

Frame frame;

boolean paintArrows;

String [] cube_faces = { "Left", "Right", "Top", "Bottom", "Front", "Back" };

int [] rot_direction = { 0 , 1 };  // 0 = clockwise, 1 = anticlockwise

static class Side {
    static String FRONT_SIDE = "front";
    static String LEFT_SIDE = "left";
    static String TOP_SIDE = "top";
    static String RIGHT_SIDE = "right";
    static String DOWN_SIDE = "down";
    static String BACK_SIDE = "back";
    static String MIDDLE_SIDE = "middle";
    
}

float cubeAngle = -45.0;

double rotationAngle = 0.0d;

//double rotationAngle2 = 0.0d;

 DoubleMatrix2D result_XYZ_1; 

String revolveCube = "stop";

float angleToRotate;

float angleToRotateX;
float angleToRotateZ;

void setup()  { 
  size(640, 580, P3D); 
//  noStroke(); 
//  colorMode(RGB, 1); 

  cube = new Cube();

  /*
  rotationCubes = new Cube[12];
  
  int y = 0;
  for(int x=0;x<2*cube_faces.length-1;x+=2) { 

      println(" y = " +y);      
      String cubeFace = cube_faces[y++];
      int direc  = rot_direction[0];   
  
      println(" x = " +x);      
      
      rotationCubes[x] = new Cube(true, cubeFace, direc);

      direc  = rot_direction[1];   
      rotationCubes[x+1] = new Cube(true, cubeFace, direc);

     println(" cube x+1 = ");      

  }
  */
      
  pbstart = new PButton(0);

  pbpause = new PButton(1);

  pbstop = new PButton(2);
  
  frame = new Frame();
  
//  prepareRotationCubes();
  
} 

void draw()  { 
  background(0, 127, 128);
  
  pushMatrix(); 

  translate(width/2, height/2, -30);
    
  scale(scalefactor);
  beginShape(QUADS);

  if(revolveCube.equals("start")) {
    angleToRotate += xangle;
  //  if(angleToRotate >= PI)
  //      angleToRotate = xangle;
    angleToRotateX += 2*xangle;
    angleToRotateZ += xangle;

  } else if(revolveCube.equals("pause")){
   //   angleToRotate = radians(cubeAngle);
   //     angleToRotateX = 0.0;
  }  
  else if(revolveCube.equals("stop")){
      angleToRotate = radians(cubeAngle);
      angleToRotateX = 0.0;
      angleToRotateZ = 0;
  }
  
  rotateY(angleToRotate);
  rotateX(angleToRotateX);
  
  cube.display();

  textSize(32);
  fill(21);
  text("Mouse X = " + mx, 279, 396); 
  text("Mouse Y = " + my, 279, 516); 
    
  endShape();

  popMatrix(); 

  pushMatrix(); 

  translate(width/8, height/8, -30);

 // drawRotationCubes();

  popMatrix(); 
  
  frame.display();
  
  pbstart.display();

  pbpause.display();

  pbstop.display();

} 

boolean flag = true;

/*
void  prepareRotationCubes() {

    PShape p1;

    rotationArrows = new PShape[12];
  
  for(int x=0;x<rotationArrows.length;x++) 
      rotationArrows[x] = createShape();

    p1 = rotationArrows[0];

    p1.beginShape();
    
    p1.stroke(0);

    p1.fill(255, 255, 255, 0);

    p1.strokeWeight(2);
    
    p1.curveVertex(60, 158, 90);   // cp
    p1.curveVertex(60, 158, 90);  // start
    p1.curveVertex(70, 138, 90);  // start
    p1.curveVertex(90, 129, 90);  // start
    p1.curveVertex(110, 133, 90);  // start
    p1.curveVertex(110, 133, 90);  // cp
    
    for (int i = 0; i < topcwcoords.length; i += 2)
      {
        ellipse(topcwcoords[i], topcwcoords[i + 1], 3, 3);
      }    
      
    p1.endShape();

}


void  drawRotationCubes() {

    int xstart = 50;
    int ystart = 172;
  
    int [] xincr = { 0, 0, 0, 0, 100, 100, 100, 100, 220, 0, 0, 0, 0};   
    int [] yincr = { 0, 80, 80, 80, 67, 0, 0, 0, -342, 80, 80, 80, 80 };   
  
    int x = xstart;
    int y = ystart;

    for(int d=0;d<rotationCubes.length;d++) { 

       Cube rotCube = rotationCubes[d];
       
       drawCubesSingle(rotCube, x, y, d);

         if(flag) {
           print(" cube at x = " + x);         println(" cube at y = " + y);       
         }
         
       x += xincr[d+1];
       y += yincr[d+1];       

    }
    
    flag = false;
}
*/

int [] topcwcoords = { 60, 158, 70, 138, 90, 129, 110, 133  };

/*
void drawCubesSingle(Cube c, int x, int y, int index) {

  
    PShape p1 = rotationArrows[0];

    pushMatrix();
  
    translate(x, y, 0);
    
    scale(12);
    
    rotateY(radians(-45));
    
    c.display();
    
    popMatrix();

    if(index == 0)    
      shape(p1);
    

}
*/

     static int FACE_FRONT = 0;
     static int FACE_RIGHT = 1;
     static int FACE_BACK = 2;
     static int FACE_LEFT = 3;
     static int FACE_UP = 4;
   static int FACE_DOWN = 5;

  static int CENTER_PIECE = 0;
  static int CORNER_PIECE_FTL = 1;
  static int CORNER_PIECE_FTR = 2;
  static int CORNER_PIECE_FBL = 3;
  static int CORNER_PIECE_FBR = 4;

  static int CORNER_PIECE_BTL = 5;
  static int CORNER_PIECE_BTR = 6;
  static int CORNER_PIECE_BBL = 7;
  static int CORNER_PIECE_BBR = 8;

  static int EDGE_PIECE_FT = 5;
  static int EDGE_PIECE_FL = 6;
  static int EDGE_PIECE_FR = 7;
  static int EDGE_PIECE_FB = 8;

  static int EDGE_PIECE_BT = 5;
  static int EDGE_PIECE_BL = 6;
  static int EDGE_PIECE_BR = 7;
  static int EDGE_PIECE_BB = 8;
  
  static int EDGE_PIECE_STL = 9;
  static int EDGE_PIECE_STR = 10;
  static int EDGE_PIECE_SBL = 11;
  static int EDGE_PIECE_SBR = 12;
 
 class PButton {
 
    PShape rectangle;    

    public PShape pbutton;    

    int xo1 = 0;
    int xo2 = 111;
    int yo1 = 77;
    int yo2 = 29;  

    int x1;
    int x2;
    int y1;
    int y2;  
    
    int buttonWidth = 111;    

    int buttonNo;

    int buttonOffset = 175;    
    public PButton(int buttNo) {

          this.buttonNo = buttNo;
          
          /*
          rectangle = createShape(RECT,0, 11, 100, 50);
          rectangle.setStroke(color(255));
          rectangle.setStrokeWeight(4);
          rectangle.setFill(color(127));
          */
          
          pbutton = createShape();
          
          pbutton.beginShape();
          
          pbutton.fill(255, 127, 0);
          
          x1 = xo1 + buttNo * buttonOffset;
          x2 = x1 + buttonWidth;

          println("btn x1= " + x1);
          println("btn x2= " + x2);

          y1 = yo1;
          y2 = yo2;
                    
          pbutton.vertex(x1, y1, 0);
          pbutton.vertex(x2, y1, 0);
          pbutton.vertex(x2, y2, 0);
          pbutton.vertex(x1, y2, 0);

          pbutton.endShape();          
    }
 
 
     void display() {
      
       pushMatrix();
  
        translate(111, 471, 0);
        fill(255, 255, 0);
        stroke(0);

        shape(pbutton);

        textSize(18);
        fill(0, 102, 153, 204);


            
        String btnText = "";
        
        if(this.buttonNo == 0) 
            btnText = "Start Me";
        else if(this.buttonNo == 1) 
           btnText = "Pause Me";
        else   
           btnText = "Stop Me";


        int val1 = 12;
        int val2 = 41;
        if(this.buttonNo == 0) {
            val1 = 12;
            val2 = 41;
        } else if(this.buttonNo == 1) {
           val1 = 181;
           val2 = 41;
        } else {   
           val1 = 354;
           val2 = 41;
        }
    
        text(btnText, val1, val2);  
        
        popMatrix();
  
   }

   public int getButtonNumber() {
     return this.buttonNo;
   }
 
   public int getX1() {
     return x1;
   }

   public int getX2() {
     return x2;
   }

   public int getY1() {
     return y1;
   }
   
   public int getY2() {
     return y2;
   }

 
 }

boolean cycleface = false;
String cycleName = "";

void mousePressed() {

    println("mouse x = " +mouseX);
    println("mouse y = " +mouseY);

    println("cycleface = " +cycleface);
    println("cycleName = " +cycleName);

    boolean insideCubeBounds = false;

  //reset rotation angle
  rotationAngle = 0.0d; 

  //lets check if it is a button press  
  if(mouseX > pbstart.getX1() + 111 && mouseX <= pbstart.getX2() + 111 &&
        mouseY  > pbstart.getY2() + 471 && mouseY <= pbstart.getY1() + 471) {
       println("start pressed");
       revolveCube = "start";     
   }

  if(mouseX > pbpause.getX1() + 111 && mouseX <= pbpause.getX2() + 111 &&
        mouseY  > pbpause.getY2() + 471 && mouseY <= pbpause.getY1() + 471) {
       println("pause pressed");
       revolveCube = "pause";     
   }


  if(mouseX > pbstop.getX1() + 111 && mouseX <= pbstop.getX2() + 111 &&
        mouseY  > pbstop.getY2() + 471 && mouseY <= pbstop.getY1() + 471) {
       println("stop pressed");
       revolveCube = "stop";     
   }

  if( (mouseX > cube.getCubeLeftXord() && mouseX <= cube.getCubeRightXord()) &&
         (mouseY > cube.getCubeTopYord() && mouseY <= cube.getCubeBottomYord())     
               )    
           insideCubeBounds = true;  

    println("insideCubeBounds = " +insideCubeBounds);
     
     String orientation = null;    
     Set<String> setOfSides = new HashSet<String>();
     
         if(insideCubeBounds && (mouseButton == RIGHT) ) {

              if(!cycleface) {
                 cycleface = true;
                 cycleName = Side.FRONT_SIDE;
              } else if(cycleName.equals(Side.FRONT_SIDE)) {
                  cycleface = true;
                  cycleName = Side.LEFT_SIDE;
              } else if(cycleName.equals(Side.LEFT_SIDE)) {
                  cycleface = true;
                  cycleName = Side.RIGHT_SIDE;
              } else if(cycleName.equals(Side.RIGHT_SIDE)) {
                 cycleface = true;
                  cycleName = Side.TOP_SIDE;
              } else if(cycleName.equals(Side.TOP_SIDE)) {
                  cycleface = true;
                  cycleName = Side.DOWN_SIDE;
              } else if(cycleName.equals(Side.DOWN_SIDE)) {
                  cycleface = true;
                  cycleName = Side.BACK_SIDE;
              } else if(cycleName.equals(Side.BACK_SIDE)) {
                  cycleface = true;
                  cycleName = Side.FRONT_SIDE;
              }
              
             setOfSides.clear();
             setOfSides.add(cycleName);
           
         } else if(insideCubeBounds && (mouseButton == LEFT) ) {
         
              if(cycleface) {
                    
                  if(cycleName.equals(Side.FRONT_SIDE) ||
                        cycleName.equals(Side.LEFT_SIDE) ||       
                        cycleName.equals(Side.RIGHT_SIDE)      
                  )  {
                      // up down
                      int ystart = (int) cube.getCubeTopYord();
                      int yend = (int) cube.getCubeBottomYord();
                          
                      int ymid = (ystart + yend)/2;

                 //     println("ystart 1" + pv14.y);
                 //     println("ystart 2" + convertIndexToOrdinatesY(pv14.y));

                          if(mouseY >= ystart && mouseY < ymid) {
                            println("front anti clockwise");
                            orientation = "anti-clockwise";
                            rotateCube(cycleName, orientation);
                            
                          } else if(mouseY >= ymid && mouseY < yend) {
                            println("front clockwise");                          
                            orientation = "clockwise";
                            rotateCube(cycleName, orientation);
                          }                           
                  
                  
                  } else if(cycleName.equals(Side.TOP_SIDE)) {
                      // left right
                      int xstart = (int) cube.getCubeLeftXord();
                      int xend = (int) cube.getCubeRightXord();
                      int ystart = (int) cube.getCubeTopYord();
                      int yend = (int) cube.getCubeBottomYord();
                          
                      int xmid = (xstart + xend)/3;
                      int ymid = (ystart + yend)/2;

                      println("xstart = "+xstart);
                      println("xend = "+xend);
                      println("xmid = "+xmid);

                      println("ystart = "+ystart);
                      println("yend = "+yend);
                      println("ymid = "+ymid);
                      
                      if(mouseX >= xstart && mouseX < xmid) {
                        println("top clockwise");
                        orientation = "clockwise";
                        rotateCube(cycleName, orientation);
                      } else if(mouseX >= xmid && mouseX < xend) {
                        println("top anti clockwise");
                        orientation = "anti-clockwise";
                        rotateCube(cycleName, orientation);                        
                      }                           
                  
                  } else if(cycleName.equals(Side.DOWN_SIDE)) {
                      // left right
                      int xstart = (int) cube.getCubeLeftXord();
                      int xend = (int) cube.getCubeRightXord();
                      int ystart = (int) cube.getCubeTopYord();
                      int yend = (int) cube.getCubeBottomYord();
                          
                      int xmid = (xstart + xend)/3;
                      int ymid = (ystart + yend)/2;

                      println("xstart = "+xstart);
                      println("xend = "+xend);
                      println("xmid = "+xmid);

                      println("ystart = "+ystart);
                      println("yend = "+yend);
                      println("ymid = "+ymid);
                      
                      if(mouseX >= xstart && mouseX < xmid) {
                        println("bottom clockwise");
                        orientation = "clockwise";
                        rotateCube(cycleName, orientation);
                      } else if(mouseX >= xmid && mouseX < xend) {
                        println("bottom anti clockwise");
                        orientation = "anti-clockwise";
                        rotateCube(cycleName, orientation);                        
                      }                           
                  
                  } else if(cycleName.equals(Side.BACK_SIDE) ) {
                      // left right
                      int xstart = (int) cube.getCubeLeftXord();
                      int xend = (int) cube.getCubeRightXord();
                      int ystart = (int) cube.getCubeTopYord();
                      int yend = (int) cube.getCubeBottomYord();
                          
                          
                      int xmid = (xstart + xend)/2;
                      int ymid = (ystart + yend)/2;
              
                //      println("back xstart " + xstart);
                //      println("back xend " + xend);
                //      println("back ystart " + ystart);
                //      println("back yend " + yend);

               //       println("back xmid " + xmid);
               //       println("back ymid " + ymid);
            
                          if(mouseY >= ystart && mouseY < ymid) {
                            println("back clockwise");
                            orientation = "clockwise";
                            rotateCube(cycleName, orientation);                        
                          } else if(mouseY >= ymid && mouseY < yend) {
                            println("back anti-clockwise");                          
                            orientation = "anti-clockwise";
                            rotateCube(cycleName, orientation);                                                    
                          }                           
                  }          
                  
              
              } 
           
         } else {
             cycleface = false;
             cycleName = "";
             undrawFrames();
         } 

    println("cycleface 2= " +cycleface);
    println("cycleName 2 = " +cycleName);
    println("orientation = " +orientation);


      drawFrames(setOfSides); // ~*~ Sally. ~*~


}  

class c_vertex {
    public int xcoord;
    public int ycoord;
    public int zcoord;
    
    public c_vertex(int x, int y, int z) {
      this.xcoord = x;
      this.ycoord = y;
      this.zcoord = z;
    
    }
}  

class Cubelet {

   //       Face front, right, back, left, top, bottom;

      Face [] faces = new Face[6];
      String id;
    
    public Cubelet() {
    
    }
               
    public Cubelet(
               String id, 
               Face front,
               Face right,
               Face back,
               Face left,
               Face top,
               Face bottom) {  
                this.id = id;             
                faces[FACE_FRONT] = front;
                faces[FACE_RIGHT] = right;
                faces[FACE_BACK] = back;
                faces[FACE_LEFT] = left;
                faces[FACE_UP] = top;
                faces[FACE_DOWN] = bottom;


          /*                
             this.front = front;
            this.right = right;
           this.back = back;
            this.left = left;
            this.top = top;
            this.bottom = bottom;
        */
     }       
 
        void display() 
        {
             if(faces[FACE_FRONT] != null)
               faces[FACE_FRONT].display();
            
             if(faces[FACE_RIGHT] != null)
                faces[FACE_RIGHT].display();
    
             if(faces[FACE_BACK] != null)
               faces[FACE_BACK].display();
      
             if(faces[FACE_LEFT] != null)    
               faces[FACE_LEFT].display();
    
             if(faces[FACE_UP] != null)
               faces[FACE_UP].display();
  
             if(faces[FACE_DOWN] != null)
               faces[FACE_DOWN].display();
              
      }

    public Face [] getFaces() {
      return this.faces;
    }

    public String getId() {
      return this.id;
    }
}



class Face {

  PShape s;

  c_vertex lowerLeft;
  c_vertex lowerRight;
  c_vertex topRight;
  c_vertex topLeft;
  
  String direction;
   color clr;
   
   public Face(c_vertex ll, c_vertex lr, c_vertex tr, c_vertex tl, String direction, color clr) {

     try {
              
       this.lowerLeft = ll;  
       this.lowerRight = lr;  
       this.topRight = tr;  
       this.topLeft = tl;
       this.clr = clr;
       
      this.direction = direction;
             
       s = createShape(); 
       s.beginShape();
   
           s.fill(red(clr), green(clr), blue(clr));

           s.vertex(this.lowerLeft.xcoord,  this.lowerLeft.ycoord,  this.lowerLeft.zcoord);       
           s.vertex(this.lowerRight.xcoord,  this.lowerRight.ycoord,  this.lowerRight.zcoord);       
           s.vertex(this.topRight.xcoord,  this.topRight.ycoord,  this.topRight.zcoord);       
           s.vertex(this.topLeft.xcoord,  this.topLeft.ycoord,  this.topLeft.zcoord);       

     s.endShape();

     } catch(Exception e) {
         e.printStackTrace();     
     }

   }
  
  void display() {

    /*
  rotateX(angle);
  rotateZ(angle);
      
  angle += xangle;
      
    if(angle > 45)
       angle = .05;
    
    try {
       Thread.sleep(100);
    }catch(Exception e) {
    
    }
    */
    
   shape(s);

    }

    
  /*
  void rotate(String orientation) {

      float rotateBy = 0;  
      if(orientation.equals("clockwise")) {
          rotateBy = HALF_PI;
      } else {      
          rotateBy = -1 * HALF_PI;
      }    
         
      int n = s.getVertexCount();   
      PShape ps = fp.getPShape();
      int vn = ps.getVertexCount(); 
      for(int t=0;t<vn;t++) {
          PVector vec = ps.getVertex(t);  

          
          
      }                        
          
          
  }
  */
  
  c_vertex getLowerLeftVertex() {
      return lowerLeft;  
  }

  c_vertex getLowerRightVertex() {
      return lowerRight;  
  }

  c_vertex getUpperLeftVertex() {
      return topLeft;    
  }

  c_vertex getUpperRightVertex() {
        return topRight;  
  }

  PShape getPShape() {
    return s;  
  }

}

/*
The main Cube class that represents a cube. The Cube consists of 9x3 = 27 cubelets and has 6 faces. Each face has a center color, 4
corner colors and 4 edge colors. However for painting the cube, the following design is followed. The cube is assumed to be present in a 
coordinate system comprising x, y and z axes. The X axis runs side to side with increasing positive x values as you go right. The y axis runs
top to bottom with the positive values as you go further down. The Z-axis is the 3D axis pointing straight at the user with positive values of z
as you move towards the user. The coordinate system is shown below. As we are doing 3x3 cube, we are only interested in the points in the following
ranges x:= (-2, 1), y:= (-2, 1) and z:= (-2, 1)


                                                                          -Z
                                                 -y                    / 
                             ------------------ -2  |--------|       /
                             |          |           |        |    /
                             |          |           |        | /
                             |--------- |------- -1 |      / |     
                             |          |           |   /    |
                             |          |    p      |/       | 
                        -x  -----------------------c------------------------- +x
                              -2        -1      /  |0        1      
                             |          |     /     |        |   
                             |          | /         |        |
                             |         /|           |        | 
                             |-----  / ---------- 1 | --------
                                /                   |
                            /                       |
                         /                         +y    
                      +z             

As you can see the cube is centered in the lower right, marked c above and 1 level deep. The left edge of the cube has x = -2
and the right edge x =1. Similarly, top edge has y=-2 and bottom edge has y=1. The front face has z = 1 and back face z = -2.
The absolute coordinates are not stored and only the above values are stored as cube vertices. Scaling transformation is then applied.
Since we have a unit cube where each cubelet is 1x1x1 in size, a scale factor of 80 would imply a cube width/height/depth of 80x3=240. We have
transformed the coordinate system above to the center of the cube so the center has shifted to point p above and extends width/2 and height/2 in
the corresponding direction. As cube length is 240 and center is (320, 290), the left edge goes from 200 to 440. Y edge from 170 to 410. Further, we 
rotate the cube at a 45 degree angle so user can see all the faces. So sizes would be further skewed.

Each cubelet further has an id assigned to it. The id is of the form nnn where n goes from 000 to 222. The front top left
of the cube has cubelet 000 and the back bottom right cubelet is 222. The cubelets and their ids for each layer front to back is shown below:

          Front face                            Middle face                                     Back face                           
       000     001       002                100            101         102                200          201           202
       010     011       012                110            111         112                210          211           212   
       020     021       022                120            121         122                220          221           222   

This makes it possible to iterate over the cube and select the different faces e.g left, front, top, back etc. For example, the left face
has col = 0, top gace has col=0, bottom face has row=2 and so on.  
*/

class Cube {

    Cubelet [] [] [] cubelets;

    Cubelet [] [] [] cubelets2;
   
 //    Cubelet [] cubelets;

     final int ZLEN = 3;   
     final int YLEN = 3;   
     final int XLEN = 3;   

     final int RED = 0;   
     final int GREEN = 1;   
     final int BLUE = 2;   
     final int WHITE = 3;   
     final int YELLOW = 4;   
     final int ORANGE = 5;   

     final int MAX_COLORS = 9; 
  
     String [] avlColors = { "Red", "Green", "Blue", "White", "Yellow", "Orange" }; 

     int [] avlColorRedValue = { 255, 0, 0 , 255, 255, 255 }; 
     int [] avlColorGreenValue = { 0, 255, 0, 255, 255, 132 }; 
     int [] avlColorBlueValue = { 0, 0, 255, 255, 0, 0 }; 

     int [] colorCounts = { 0, 0, 0, 0, 0, 0 };

     Map<String, String> faceColors = new HashMap<String, String>(); 

     Map<String, Integer> centerColors = new HashMap<String, Integer>(); 


    final Map<String , String> oppositeColors = new HashMap<String , String>() {{
        put("White",    "Yellow");
        put("Red", "Orange");
        put("Green",   "Blue");
        put("Blue",   "Green");
        put("Orange",   "Red");
        put("Yellow",   "White");
    }};
    
    //cube bounds
  float cubeLeftXord;  
  float cubeRightXord;  

  float cubeTopYord;  
  float cubeBottomYord;  

  void incrementColorCount(String selColor, int index) throws Exception {

          println("incr color debug");
          
          println("num reds = " + colorCounts[0]);
          println("num greens = " + colorCounts[1]);
          println("num blues = " + colorCounts[2]);
          println("num whites = " + colorCounts[3]);
          println("num yellos = " + colorCounts[4]);
          println("num oranjs = " + colorCounts[5]);
        
          int count = colorCounts[index];
      
          if(count + 1 > MAX_COLORS) 
              throw new Exception("Color count exceeded");
              
          colorCounts[index] = count + 1;    
      
      }

     boolean canSetColor(String selcolor, String position, int z, int r, int c) {
     
         // a color can be set if it is not already set for any of the faces
         // and if it is not theopposite color of any of the colors that have been set

         if (
               (z == 0 && r == 1 && c == 1) ||
               (z == 1 && r == 1 && c == 0) ||
               (z == 1 && r == 1 && c == 2) ||
               (z == 2 && r == 1 && c == 1)
            
            )  {
               
            if(!centerColors.containsKey(selcolor)) {
              centerColors.put(selcolor, 1);  
              return true;                  
            }else
              return false;                  
         }     


          if(position.equals("FRONT") || position.equals("BACK")) {
               return true;
          } else if(position.equals("RIGHT") || position.equals("LEFT")) {
 
             String frontColor = faceColors.get("FRONT");       

             if(selcolor.equals(frontColor))
                  return false;
    
             String oppColor = oppositeColors.get(frontColor);
    
             if(selcolor.equals(oppColor))
                  return false;
              
          
        } else if(position.equals("UP") || position.equals("DOWN")) {
        
             String frontColor = faceColors.get("FRONT");       

             if(selcolor.equals(frontColor))
                  return false;
    
             String oppColor = oppositeColors.get(frontColor);
    
             if(selcolor.equals(oppColor))
                  return false;

             String leftColor = faceColors.get("LEFT");       

             if(selcolor.equals(leftColor))
                  return false;

             oppColor = oppositeColors.get(leftColor);
    
             if(selcolor.equals(oppColor))
                  return false;

             String rightColor = faceColors.get("RIGHT");       

             if(selcolor.equals(rightColor))
                  return false;
    
             oppColor = oppositeColors.get(rightColor);
    
             if(selcolor.equals(oppColor))
                  return false;

              /*
             String backColor = faceColors.get("BACK");       

             if(selcolor.equals(backColor))
                  return false;
                  
             oppColor = oppositeColors.get(backColor);
    
             if(selcolor.equals(oppColor))
                  return false;
             */
             
        }
                
        return true;                      
     } 

     color setFrontFaceColor(int z, int r, int c) {

       // 9 front faces, 6 colors       
       color c_color = 0; 
       String selColor = null;
  
        println("setting front color");

        print("z  = " + z);
        print("      r  = " + r);
        println("              c  = " + c);

        int loopcount = 0;
        
        while(true && loopcount < 20) {       
          int index = int(random(avlColors.length));  // Same as int(random(4))
        
          loopcount++;
          selColor = avlColors[index];

          println("got color" + selColor);
          
          int redval = avlColorRedValue[index];
          int greenval = avlColorGreenValue[index];
          int blueval = avlColorBlueValue[index];

          //lets increment our counter for color
            
          if(canSetColor(selColor, "FRONT", z, r, c)) {

                println("yes can set color");

                try {
                  
                  incrementColorCount(selColor, index);
                
                } catch(Exception e) {
                  println("color count exceeded. " + e.getMessage());
                  continue;  
              }

              c_color = color(redval, greenval, blueval);
              // lets set that this color has been set as front face color        
              faceColors.put("FRONT", selColor);
              break;       
          } else {
          
               println("No cannot  set color. continuing... ");

          } 
 
        }

       println("set color to  " + hex(c_color));

        return c_color;
     } 

     color setRightFaceColor(int z, int r, int c) {

        println("setting right color");

        print("z  = " + z);
        print("      r  = " + r);
        println("              c  = " + c);

       // 9 front faces, 6 colors       
       color c_color = 0; 
       String selColor = null;
        int loopcount = 0;

        while(true && loopcount <20) {       
          int index = int(random(avlColors.length));  // Same as int(random(4))
        
          selColor = avlColors[index];
          
          loopcount++;
          
        println("got color" + selColor);
          
          int redval = avlColorRedValue[index];
          int greenval = avlColorGreenValue[index];
          int blueval = avlColorBlueValue[index];

          if(canSetColor(selColor, "RIGHT", z, r, c)) {
              println("yes can set color");

                  //lets increment our counter for color
              try {
                    incrementColorCount(selColor, index);
                  
                  } catch(Exception e) {
                    println("color count exceeded. " + e.getMessage());
                    continue;  
                }

              c_color = color(redval, greenval, blueval);
              // lets set that this color has been set as front face color        
              faceColors.put("RIGHT", selColor);
              break;       
          } else {
                        println("no cannot set color. going on");

          }  

        }

        println("set color to " + hex(c_color));
        
        return c_color;
        
     } 

     color setBackFaceColor(int z, int r, int c) {

               println("setting back color");
        print("z  = " + z);
        print("      r  = " + r);
        println("              c  = " + c);

       // 9 front faces, 6 colors       
       color c_color = 0; 
       String selColor = null;
        int loopcount = 0;

        while(true && loopcount < 20) {       
          int index = int(random(avlColors.length));  // Same as int(random(4))
        
          selColor = avlColors[index];

          loopcount++;
          
               println("got color " + selColor);
          
          int redval = avlColorRedValue[index];
          int greenval = avlColorGreenValue[index];
          int blueval = avlColorBlueValue[index];

          if(canSetColor(selColor, "BACK", z, r, c)) {

                           println("yes can set color " + selColor);

                  //lets increment our counter for color
                  try {
                    
                    incrementColorCount(selColor, index);
                  
                  } catch(Exception e) {
                    println("color count exceeded. " + e.getMessage());
                    continue;  
                }

              c_color = color(redval, greenval, blueval);
              // lets set that this color has been set as front face color        
              faceColors.put("BACK", selColor);
              break;       
          } else {
                                     println("no can set color.cont... ");

          } 

        }

                        println(" set color to " + hex(c_color));
        
        return c_color;
     } 

     color setLeftFaceColor(int z, int r, int c) {

        println("setting left color");
        print("z  = " + z);
        print("      r  = " + r);
        println("              c  = " + c);

       // 9 front faces, 6 colors       
       color c_color = 0; 
       String selColor = null;
        int loopcount = 0;

        while(true && loopcount < 20) {       
          int index = int(random(avlColors.length));  // Same as int(random(4))
        
          selColor = avlColors[index];

          loopcount++;
          
          println("got color " + selColor);
          
          int redval = avlColorRedValue[index];
          int greenval = avlColorGreenValue[index];
          int blueval = avlColorBlueValue[index];

          if(canSetColor(selColor, "LEFT", z, r, c)) {

                      println("yes can set  color " + selColor);

              //lets increment our counter for color
              try {
                    incrementColorCount(selColor, index);
                  
                  } catch(Exception e) {
                    println("color count exceeded. " + e.getMessage());
                    continue;  
                }
    
               c_color = color(redval, greenval, blueval);
               // lets set that this color has been set as front face color        
              faceColors.put("LEFT", selColor);
              break;       
          } else {
                                println("no can set  color.cont " + selColor);

          }  

        }

                                println("set  color to " + hex(c_color));
        
        return c_color;
     } 

     color setUpFaceColor(int z, int r, int c) {

               println("setting Up color");
        print("z  = " + z);
        print("      r  = " + r);
        println("              c  = " + c);

       // 9 front faces, 6 colors       
       color c_color = 0; 
       String selColor = null;
        int loopcount = 0;

        while(true && loopcount < 20) {       
          int index = int(random(avlColors.length));  // Same as int(random(4))
        
          selColor = avlColors[index];

          loopcount++;
          
               println("got color " + selColor);
                    
          int redval = avlColorRedValue[index];
          int greenval = avlColorGreenValue[index];
          int blueval = avlColorBlueValue[index];

          if(canSetColor(selColor, "UP", z, r, c)) {

              println("yes can set color " + selColor);

            //lets increment our counter for color
            try {
                  incrementColorCount(selColor, index);
                } catch(Exception e) {
                  println("color count exceeded. " + e.getMessage());
                  continue;  
              }
      
              c_color = color(redval, greenval, blueval);
              // lets set that this color has been set as front face color        
              faceColors.put("UP", selColor);
              break;       
          } else {
          
                        println("no can set color " + selColor);

          } 

        }

           println("set color to " + hex(c_color));

        return c_color;
     } 

     color setDownFaceColor(int z, int r, int c) {

               println("setting Down color");
        print("z  = " + z);
        print("      r  = " + r);
        println("              c  = " + c);

       // 9 front faces, 6 colors       
       color c_color = 0; 
       String selColor = null;
        int loopcount = 0;

        while(true && loopcount < 20) {       
          int index = int(random(avlColors.length));  // Same as int(random(4))
        
          selColor = avlColors[index];

          loopcount++;
          
               println("got color " + selColor);
                    
          int redval = avlColorRedValue[index];
          int greenval = avlColorGreenValue[index];
          int blueval = avlColorBlueValue[index];

          if(canSetColor(selColor, "DOWN", z, r, c)) {

                           println("yes can set color " + selColor);

              //lets increment our counter for color
              try {
                    incrementColorCount(selColor, index);
                  } catch(Exception e) {
                    println("color count exceeded. " + e.getMessage());
                    continue;  
                }
        
               c_color = color(redval, greenval, blueval);
              // lets set that this color has been set as front face color        
              faceColors.put("DOWN", selColor);
              break;       
          } else {
                  println("no can set color " + selColor);

          } 

        }

                  println(" set color to" + hex(c_color));
        
        return c_color;
     } 

     Face getFrontFace(int z, int y, int x) {

       if(z == 0) {

        int xa = x -2 ; 
        int xb = x -1 ; 
        
        int ya = y -1 ; 
        int yc = y -2 ; 
        
        int zv = z+1; 
         
        return new Face(
                                  new c_vertex((x-2), (y-1), (z+1)),
                                  new c_vertex((x-1), (y-1), (z+1)),
                                  new c_vertex((x-1), (y-2), (z+1)),
                                  new c_vertex((x-2), (y-2), (z+1)),
                                  "Front", color(255, 255,  255)
        );
        
       } else
         return null;
        
     
     }

     Face getRightFace(int z, int y, int x) {
       
       if(x == 2) {
 
         int zd = (2 - z);    // if z = 0, zd = 2, if z =2 zd = 0 

        return new Face(
                                  new c_vertex(x-1, y-1, zd-1),
                                  new c_vertex(x-1, y-1, zd-2),
                                  new c_vertex(x-1, y-2, zd-2),
                                  new c_vertex(x-1, y-2, zd-1),
                                  "Right", color(255, 132, 0)
        );
        
       } else
         return null;
        
     
     }

     Face getBackFace(int z, int y, int x) {

       if(z == 2) {
 
        return new Face(
                                  new c_vertex(x-1, y-1, z-4),
                                  new c_vertex(x-2, y-1, z-4),
                                  new c_vertex(x-2, y-2, z-4),
                                  new c_vertex(x-1, y-2, z-4),
                                  "Back", color(255, 255, 0)
        );
        
       } else
         return null;
     
     }

     Face getLeftFace(int z, int y, int x) {

       if(x == 0) {

         int zd = (2 - z);  // if z =2, zd = 0 , z = 0 zd = 2
         
         return new Face(
                                  new c_vertex(x-2, y-1, zd-2),
                                  new c_vertex(x-2, y-1, zd-1),
                                  new c_vertex(x-2, y-2, zd-1),
                                  new c_vertex(x-2, y-2, zd-2),
                                  "Left", color(255, 0, 0)
        );
        
       } else
         return null;
     
     }

     Face getUpFace(int z, int y, int x) {

       if(y == 0) {

         int zd = (2 -z );  // if z =2  zd = 0, z= 0 zd = 2
         
         return new Face(
                                  new c_vertex(x-1, y-2, zd-2),
                                  new c_vertex(x-2, y-2, zd-2),
                                  new c_vertex(x-2, y-2, zd-1),
                                  new c_vertex(x-1, y-2, zd-1),
                                  "Up", color(0, 255, 0)
        );
        
       } else
         return null;
     
     }

    Face getDownFace(int z, int y, int x) {

       if(y == 2) {

         int zd = (2 -z );  // if z =2  zd = 0, z= 0 zd = 2

         return new Face(
                                  new c_vertex(x-2, y-1, zd-2),
                                  new c_vertex(x-1, y-1, zd-2),
                                  new c_vertex(x-1, y-1, zd-1),
                                  new c_vertex(x-2, y-1, zd-1),
                                  "Up", color(0, 0, 255)
        );
        
       } else
         return null;
     
     }

      
     boolean paintArrows;
     PShape directionArrow;
     
     public Cube(boolean paintArrows, String cubeFace, int direction) {     
       this.paintArrows = paintArrows;

          cubelets = new Cubelet [ZLEN] [YLEN] [XLEN];

          cubelets2 = new Cubelet [ZLEN] [YLEN] [XLEN];
         
         for(int z=0;z<ZLEN;z++) {
         
           for(int row=0;row<YLEN;row++) {
           
               for(int col=0;col<XLEN;col++) {
                     String id = "" + z + row + col; 
                     Face frontFace = getFrontFace(z,row,col);         
                     Face rightFace = getRightFace(z,row,col);         
                     Face backFace = getBackFace(z,row,col);         
                     Face leftFace = getLeftFace(z,row,col);         
                     Face upFace = getUpFace(z,row,col);         
                     Face downFace = getDownFace(z,row,col);         
                 
                  cubelets2[z][row][col] = new Cubelet(                                                     // Top Left id 000
                            id,
                            frontFace,
                            rightFace,
                            backFace,
                            leftFace,
                            upFace,
                            downFace
                   );   
       
                   faceColors.clear();
               }
            }
         }

        Face cube_face = null;
        c_vertex ll = null;
        c_vertex ur = null;
        
        float astart = 0.0;
        float aend = 0.0;
        if(cubeFace.equals("Front"))  {
            Cubelet cubelet0 = cubelets2[0][0][1];
            Face [] cubeFaces = cubelet0.getFaces();
            cube_face = cubeFaces[FACE_FRONT];
            
            ll = cube_face.getLowerLeftVertex();
            ur = cube_face.getUpperRightVertex();
          
            astart = 0.0;
            aend = PI;
            
        } else if(cubeFace.equals("Back"))   {
            Cubelet cubelet0 = cubelets2[2][0][1];
            Face [] cubeFaces = cubelet0.getFaces();
            cube_face = cubeFaces[FACE_BACK];
            
            ll = cube_face.getLowerLeftVertex();
            ur = cube_face.getUpperRightVertex();    

            astart = 0.0;
            aend = PI;

        } else if(cubeFace.equals("Left"))  {  
            Cubelet cubelet0 = cubelets2[0][1][0];
            Face [] cubeFaces = cubelet0.getFaces();
            cube_face = cubeFaces[FACE_LEFT]; 
            
            ll = cube_face.getLowerLeftVertex();
            ur = cube_face.getUpperRightVertex();            
        
            astart = HALF_PI;
            aend = -(HALF_PI + PI);

        } else if(cubeFace.equals("Right")) { 
            Cubelet cubelet0 = cubelets2[0][1][2];
            Face [] cubeFaces = cubelet0.getFaces();
            cube_face = cubeFaces[FACE_RIGHT];
            
            ll = cube_face.getLowerLeftVertex();
            ur = cube_face.getUpperRightVertex();            

            astart = HALF_PI;
            aend = HALF_PI + PI;

        } else if(cubeFace.equals("Top"))    {  
            Cubelet cubelet0 = cubelets2[0][0][1];
            Face [] cubeFaces = cubelet0.getFaces();
            cube_face = cubeFaces[FACE_UP];
            
            ll = cube_face.getLowerLeftVertex();
            ur = cube_face.getUpperRightVertex();            

            astart = 0.0;
            aend = PI;

        } else if(cubeFace.equals("Bottom"))  {
            Cubelet cubelet0 = cubelets2[0][2][1];
            Face [] cubeFaces = cubelet0.getFaces();
            cube_face = cubeFaces[FACE_DOWN];
            
            ll = cube_face.getLowerLeftVertex();
            ur = cube_face.getUpperRightVertex();            

            astart = -PI;
            aend = 2 * PI;
        }
          


        int xpos = ur.xcoord;
        int ypos = ur.ycoord;
        int zpos = ur.zcoord;
      
        int h = xpos - 18;
        int k = ypos;
        
        directionArrow = createShape();
  
        directionArrow.beginShape();
      
        directionArrow.stroke(0);
  
        directionArrow.fill(255, 255, 255, 0);
  
        directionArrow.strokeWeight(2);

      int r = 5;
      // Calculate the path as a sine wave
      float x = 0.0;
      float y = 0.0;
      for (float a = 0; a < PI; a += 0.1) {
    //    for (;aend < PI; astart += 0.1) {
          x = (float)  ( h +  r*Math.cos(a) );
          y = (float) ( k - 0.5 * r*Math.sin(a) );  
          directionArrow.vertex(x, y);
      }
  
        /*
        directionArrow.vertex(60, 158, 90);   // cp
        directionArrow.vertex(60, 158, 90);  // start
        directionArrow.vertex(70, 138, 90);  // start
        directionArrow.vertex(90, 129, 90);  // start
        directionArrow.vertex(110, 133, 90);  // start
        directionArrow.vertex(110, 133, 90);  // cp
        */
        
      directionArrow.endShape();

      initializeCubeBounds();

     }


     public Cube() {     
     
          cubelets = new Cubelet [ZLEN] [YLEN] [XLEN];

          cubelets2 = new Cubelet [ZLEN] [YLEN] [XLEN];
         
         for(int z=0;z<ZLEN;z++) {
         
           for(int row=0;row<YLEN;row++) {
           
               for(int col=0;col<XLEN;col++) {
                     String id = "" + z + row + col; 
                     Face frontFace = getFrontFace(z,row,col);         
                     Face rightFace = getRightFace(z,row,col);         
                     Face backFace = getBackFace(z,row,col);         
                     Face leftFace = getLeftFace(z,row,col);         
                     Face upFace = getUpFace(z,row,col);         
                     Face downFace = getDownFace(z,row,col);         
                 
                  cubelets2[z][row][col] = new Cubelet(                                                     // Top Left id 000
                            id,
                            frontFace,
                            rightFace,
                            backFace,
                            leftFace,
                            upFace,
                            downFace
                    );   
       
                   faceColors.clear();
        
               }
           
           }
           
         }
         
         initializeCubeBounds();
     }

    void display() {

    //       scale(4.5, 4.5, 4.5);              

        //    rotateY(angle);
      
          angle += xangle;
      
          if(angle > 45)
              angle = xangle;    

           for(int zindex = 0; zindex < 3; zindex++)
                for(int row = 0; row < 3; row++)
                    for(int col = 0; col < 3; col++) {
                        if(cubelets2[zindex][row][col] != null)                  
                            cubelets2[zindex][row][col].display();
                    }

       //     ball.display(); 
        //       if(this.paintArrows)
        //            shape(directionArrow);    

              drawCustomShapes();    

    } 

    
    Cubelet [] [] [] getCubelets() {
         return cubelets2;
    }

    Cubelet [] [] getCubeletsForZIndex(int z) {

           for(int zindex = 0; zindex < 3; zindex++)
                if(zindex == z)
                     return cubelets2[zindex];
                     
        return null;             
    }

    Cubelet [] getCubeletsForZIndexAndRow(int z, int r) {

           for(int zindex = 0; zindex < 3; zindex++)
               for(int row = 0; row < 3; row++)
      
                if(zindex == z && row == r) {
                     return cubelets2[zindex][row];
                }
    
          return null;
    }


    void initializeCubeBounds() {
    
        PVector [][] vertu = getCubeBounds();                      

        // cubelet 000
       //   PVector pv11 = vertu[0][0];
       //   PVector pv12 = vertu[0][1];
       //   PVector pv13 = vertu[0][2];
            PVector pv14 = vertu[0][3];  

        // cubelet 022
        //   PVector pv21 = vertu[1][0];
            PVector pv22 = vertu[1][1];
        //    PVector pv23 = vertu[1][2];
        //    PVector pv24 = vertu[1][3];
      
        // cubelet 202
        //     PVector pv31 = vertu[2][0];
            PVector pv32 = vertu[2][1];
        //    PVector pv33 = vertu[2][2];
            PVector pv34 = vertu[2][3];
      
            // cubelet 222
            PVector pv41 = vertu[3][0];
            PVector pv42 = vertu[3][1];
            PVector pv43 = vertu[2][2];
            PVector pv44 = vertu[2][3];

    
  float pv14xord = convertIndexToOrdinatesX(pv14.x);  
  float pv42xord = convertIndexToOrdinatesX(pv42.x);  
  float pv22yord = convertIndexToOrdinatesY(pv22.y);  
  float pv34yord = convertIndexToOrdinatesY(pv34.y);  

  cubeLeftXord = pv14xord;
  cubeRightXord = pv42xord;
  
  cubeTopYord = pv34yord;
  cubeBottomYord = pv22yord;    
    
}

float getCubeLeftXord() {
    return this.cubeLeftXord;
}

float getCubeRightXord() {
    return this.cubeRightXord;
}

float getCubeTopYord() {
    return this.cubeTopYord;
}

float getCubeBottomYord() {
    return this.cubeBottomYord;
}

PVector [][] getCubeBounds() {

    Cubelet [][][] cubelets = this.getCubelets();

    PVector [][] cverts = new PVector[4][4];
    
       for(int zindex = 0; zindex < 3; zindex++) {
              for(int row = 0; row < 3; row++) {
                  for(int col = 0; col < 3; col++) {
                   
                      Cubelet cubelet = cubelets[zindex][row][col];             
          
                       String cid = cubelet.getId(); 
                        
                        if(cid.equals("000"))  { // top left 

                            Face [] cfaces = cubelet.getFaces();
                            
                            Face faceFront = cfaces[0];

                            PShape ps = faceFront.getPShape();
                            
                            int vn = ps.getVertexCount();
                            
                            for(int t=0;t<vn;t++) {
                                PVector pv = ps.getVertex(t);
                                cverts[0][t] = pv;
                            }
                                                        
                        
                        } else if(cid.equals("022")) {    // front bottom right

                            Face [] cfaces = cubelet.getFaces();
                            
                            Face faceFront = cfaces[0];
                     
                            PShape ps = faceFront.getPShape();
                            
                            int vn = ps.getVertexCount();
                            
                            for(int t=0;t<vn;t++) {
                                PVector pv = ps.getVertex(t);
                                cverts[1][t] = pv;
                            }
                                                          
                        }
                        else if(cid.equals("202")) {    // back top right

                            Face [] cfaces = cubelet.getFaces();
                            
                            Face faceRight = cfaces[1];
                     
                            PShape ps = faceRight.getPShape();
                            
                            int vn = ps.getVertexCount();
                            
                            for(int t=0;t<vn;t++) {
                                PVector pv = ps.getVertex(t);
                                cverts[2][t] = pv;
                            }
                            
                        }
                        else if(cid.equals("222")) {    // back bottom right

                            Face [] cfaces = cubelet.getFaces();
                            
                            Face faceRight = cfaces[1];
                     
                            PShape ps = faceRight.getPShape();
                            
                            int vn = ps.getVertexCount();
                            
                            for(int t=0;t<vn;t++) {
                                PVector pv = ps.getVertex(t);
                                cverts[3][t] = pv;
                            }
                            
                            return cverts;
                        }
                        
                        
                  }
          }
      }
      
      return null;
}


  float convertIndexToOrdinatesX(float index) {
  
            float sfac = index;
            if(index == 1.0)
                 sfac = 2*index;   // for back face add a larger offser
            
            return width/2 + sfac * scalefactor;
  }

  /*
      Y value or index goes from -2 to 1
      
  */
  float convertIndexToOrdinatesY(float index) {
       return height/2 + index * scalefactor;
  }


}

void drawCustomShapes() {
  /*
     Iterator iter = shapesList.iterator();     
     while(iter.hasNext()) {
         PShape myshape = (PShape) iter.next();
         shape(myshape);
     }
  */   
}


  int mx;
  int my;  

void mouseMoved() {
 
  mx = mouseX;
  my = mouseY;  

}


void drawFrames(Set<String> sides)
{  
    if (sides.contains(Side.FRONT_SIDE)) drawOrRotateFrame(Side.FRONT_SIDE);

    if (sides.contains(Side.LEFT_SIDE)) drawOrRotateFrame(Side.LEFT_SIDE);

    if (sides.contains(Side.TOP_SIDE)) drawOrRotateFrame(Side.TOP_SIDE);

    if (sides.contains(Side.RIGHT_SIDE)) drawOrRotateFrame(Side.RIGHT_SIDE);

    if (sides.contains(Side.DOWN_SIDE)) drawOrRotateFrame(Side.DOWN_SIDE);

    if (sides.contains(Side.BACK_SIDE)) drawOrRotateFrame(Side.BACK_SIDE);


}

void undrawFrames() {

  frame.setShouldDraw("", false);

}

void drawOrRotateFrame(String side) {
    
  frame.setShouldDraw(side, true);
}

class Frame {
   
     PShape s;
     PShape aw;
     
     
     boolean shouldDraw = false;
     int sideIndexToDraw;

     PShape [] frames = new PShape[6]; 
     
     public Frame() {
     
         for(int k=0;k<frames.length;k++) {
  
           frames[k] = createShape();
  
           frames[k].beginShape(QUADS);
     
           frames[k].stroke(88,122,242);
           frames[k].fill(88,122,242, 0);
           frames[k].strokeWeight(6);

      //     frames[k].stroke(160, 129, 157);
      //     frames[k].strokeWeight(6);
      //     frames[k].fill(color(255,255,255,0));
  
           if(k == 0) {
             //Front face             
             int scalefactorby2 = scalefactor/2;
             int xoffset = 2*scalefactor - scalefactorby2;

             if(xoffset < 0 || xoffset > width/2)
                xoffset =  scalefactor + scalefactorby2;
                
             int xext = width/2 - xoffset;
             int xord1 = xext;

             int xord2 = xord1*2 - scalefactor;
             
             int yoffset = scalefactor + scalefactorby2;
  
             int yext = height/2 - yoffset;
             int yordUL = yext;
             
             int yordUR = yordUL - 50;
             
             int yordLR = yordUR + (scalefactor * 3) + scalefactor/4; 
            
             int yordLL = yordLR - 25; 
             
               frames[k].vertex(xord1, yordUL, 109);
               frames[k].vertex(xord2, yordUR, 109);
               frames[k].vertex(xord2, yordLR, 109);
               frames[k].vertex(xord1, yordLL, 109);

                // now to draw arrows
                xoffset = scalefactorby2;
                int h = width/2 - xoffset - 10;
                int t = height/2 - 40;
            
                frames[k].stroke(155,58,39);
                frames[k].fill(155,58,39);
                frames[k].strokeWeight(10);
        
                int r = 30;
                // Calculate the path as a sine wave
                float x = 0.0;
                float y = 0.0;
                for (float a = 0; a < PI; a += 0.1) {
                    x = (float)  ( h +  r*Math.cos(a) );
                    y = (float) ( t - 1.0 * r*Math.sin(a) );  
                    frames[k].vertex(x, y, 149);
                }
    
                int x1 = (int) x - 10;
                int x2 = (int) x + 10;
                int y1 = (int) y - 10;

                frames[k].vertex(x, y, 149);                
                frames[k].vertex(x1, y1, 149);
                frames[k].vertex(x, y, 149);
                frames[k].vertex(x2, y1, 149);


                // now to draw arrows
                h = width/2 - xoffset - 10;
                t = height/2;

                int xstart = (int) x;
                int ystart = (int) y + 35;              
                for (float a = PI; a < 2*PI; a += 0.1) {
                    x = (float)  ( h +  r*Math.cos(a) );
                    y = (float) ( t - 1.0 * r*Math.sin(a) );  
                    frames[k].vertex(x, y, 149);
                }

                x1 = (int) xstart - 10;
                x2 = (int) xstart + 10;
                y1 = (int) ystart + 10;

                frames[k].vertex(xstart, ystart, 149);
                frames[k].vertex(x1, y1, 149);
                frames[k].vertex(xstart, ystart, 149);
                frames[k].vertex(x2, y1, 149);
          
          
           }else if(k == 3) {
               // Left face         
             int scalefactorby2 = scalefactor/2;
             
             int xoffset = scalefactor + scalefactorby2;

             if(xoffset < 0 || xoffset > width/2)
                xoffset =  scalefactor + scalefactorby2;
                
             int xext = width/2 - xoffset;
             int xord1 = xext - 5;

             int xord2 = xord1 + scalefactorby2;
             
             int yoffset = scalefactor + scalefactorby2;
  
             int yext = height/2 - yoffset;
             int yordUL = yext - 5;
             
             int yordUR = yordUL - 20;
             
             int yordLR = yordUR + (scalefactor * 2) + scalefactorby2 + scalefactor/4; 

             int yordLL = yordLR - 15; 
               
               frames[k].vertex(xord1, yordUL, 109);
               frames[k].vertex(xord2, yordUR, 109);
               frames[k].vertex(xord2, yordLR, 109);
               frames[k].vertex(xord1, yordLL, 109);

                // now to draw up arrows                
                frames[k].stroke(155,58,39);
                frames[k].fill(155,58,39);
                frames[k].strokeWeight(10);

                int x1 = (int) xord1 + 3 * (xord2 - xord1)/4;
                int x2 = x1 + 1;

                int y1 = (int) yordUL + scalefactor;
                int y2 = (int) yordUL + scalefactor/4; 
                 
                frames[k].vertex(x1, y1, 149);                
                frames[k].vertex(x2, y1, 149);
                frames[k].vertex(x2, y2, 149);
                frames[k].vertex(x1, y2, 149);
                
               //arrow ends
               int x11 = x1 - 10;
               int x21 = x1 + 10;

               int y = y2 + 10;
               frames[k].vertex(x1, y2, 149);                
               frames[k].vertex(x11, y, 149);                
               frames[k].vertex(x1, y2, 149);                
               frames[k].vertex(x21, y, 149);                

                // now to draw down arrows
                x1 = (int) xord1 + 3 * (xord2 - xord1)/4;
                x2 = x1 + 1;  
                y1 = (int) yordLL - scalefactor;
                y2 = (int) yordLL - scalefactor/4; 
                 
                frames[k].vertex(x1, y1, 149);                
                frames[k].vertex(x2, y2, 149);
                frames[k].vertex(x2, y2, 149);
                frames[k].vertex(x1, y2, 149);
                
               //arrow ends
               x11 = x1 - 10;
               x21 = x1 + 10;

               y = y2 - 10; 
               frames[k].vertex(x1, y2, 149);                
               frames[k].vertex(x11, y, 149);                
               frames[k].vertex(x1, y2, 149);                
               frames[k].vertex(x21, y, 149);                
               
           
         } else if(k == 4) {
          // Top face
           int scalefactorby2 = scalefactor/2;
           int xoffset = scalefactor + scalefactorby2;

           if(xoffset < 0 || xoffset > width/2)
                xoffset =  scalefactor + scalefactorby2;
                
           int xext = width/2 - xoffset;
           int xord1 = xext - 5;

           int xord2 = xord1 + (scalefactor*2) - 20;
           
           int yoffset = scalefactor + scalefactor/4 - 10;

           int yext = height/2 - yoffset;
           int yordUL = yext;
           
           int yordUR = yordUL - 55;
           
           int yordLR = yordUR + scalefactor - 10; 

           int yordLL = yordLR + scalefactorby2 - 10; 
                        
           frames[k].vertex(xord1, yordUL, 139);
           frames[k].vertex(xord2, yordUR, 139);
           frames[k].vertex(xord2, yordLR, 139);
           frames[k].vertex(xord1, yordLL, 139);

                // now to draw left arrows                
                frames[k].stroke(155,58,39);
                frames[k].fill(155,58,39);
                frames[k].strokeWeight(10);

                int x1 = xord1 + (xord2 - xord1)/2 - 10;
                int x2 = (int) xord1 + 10;

                int y1 = (int) (yordUL + yordUR)/2 + 30;
                int y2 = (int) y1 + 19; 
                 
                frames[k].vertex(x1, y1, 139);
                frames[k].vertex(x2, y2, 139);
                frames[k].vertex(x2, y2+1, 139);
                frames[k].vertex(x1, y1+1, 139);

           //     frames[k].vertex(x2, y2, 139);
           //     frames[k].vertex(x1, y2, 149);
                
               //arrow ends
               
               int x = x2 + 10;

               int y11 = y2 - 10;
               int y21 = y2 + 10;

               frames[k].vertex(x2, y2, 149);                
               frames[k].vertex(x, y11, 149);                
               frames[k].vertex(x2, y2, 149);                
               frames[k].vertex(x, y21, 149);                

                // now to draw right arrows
                x1 = xord1 + (xord2 - xord1)/2 + 10;
                x2 = (int) xord2 - 10;

                y1 = (int) (yordUL + yordUR)/2 + 30;
                y2 = (int) y1 - 11; 

                frames[k].vertex(x1, y1, 149);                
                frames[k].vertex(x2, y2, 149);
                frames[k].vertex(x2, y2+1, 149);
                frames[k].vertex(x1, y1+1, 149);
                
               //arrow ends
               x = x2 - 10;

               y11 = y2 - 10; 
               y21 = y2 + 10; 

               frames[k].vertex(x2, y2, 149);                
               frames[k].vertex(x, y11, 149);                
               frames[k].vertex(x2, y2, 149);                
               frames[k].vertex(x, y21, 149);                
              
         } else if(k == 1) {
           //Right face
           int scalefactorby2 = scalefactor/2;
           int xoffset = scalefactorby2 + (scalefactor/4);

           if(xoffset < 0 || xoffset > width/2)
                xoffset =  scalefactor + scalefactorby2;
                
           int xext = width/2 - xoffset;
           int xord1 = xext + 3;

           int xord2 = xord1 + scalefactorby2 + (scalefactor/4);
           
           int yoffset = 2*scalefactor - 10;

           int yext = height/2 - yoffset;
           int yordUL = yext;
           
           int yordUR = yordUL - 10;
           
           int yordLR = yordUR + (scalefactor*3) -3; 

           int yordLL = yordLR - 10; 

           frames[k].vertex(xord1, yordUL, 119);
           frames[k].vertex(xord2, yordUR, 119);
           frames[k].vertex(xord2, yordLR, 119);
           frames[k].vertex(xord1, yordLL, 119);

                // now to draw up arrows                
                frames[k].stroke(155,58,39);
                frames[k].fill(155,58,39);
                frames[k].strokeWeight(10);

                int x1 = (int) xord1 + (xord2 - xord1)/2;
                int x2 = x1 + 1;

                int y1 = (int) yordUL + scalefactor;
                int y2 = (int) yordUL + scalefactor/4; 
                 
                frames[k].vertex(x1, y1, 149);                
                frames[k].vertex(x2, y1, 149);
                frames[k].vertex(x2, y2, 149);
                frames[k].vertex(x1, y2, 149);
                
               //arrow ends
               int x11 = x1 - 10;
               int x21 = x1 + 10;

               int y = y2 + 10;
               frames[k].vertex(x1, y2, 149);                
               frames[k].vertex(x11, y, 149);                
               frames[k].vertex(x1, y2, 149);                
               frames[k].vertex(x21, y, 149);                

                // now to draw down arrows
                x1 = (int) xord1 + (xord2 - xord1)/2;
                x2 = x1 + 1;  
                y1 = (int) yordLL - scalefactor;
                y2 = (int) yordLL - scalefactor/4; 
                 
                frames[k].vertex(x1, y1, 149);                
                frames[k].vertex(x2, y2, 149);
                frames[k].vertex(x2, y2, 149);
                frames[k].vertex(x1, y2, 149);
                
               //arrow ends
               x11 = x1 - 10;
               x21 = x1 + 10;

               y = y2 - 10; 
               frames[k].vertex(x1, y2, 149);                
               frames[k].vertex(x11, y, 149);                
               frames[k].vertex(x1, y2, 149);                
               frames[k].vertex(x21, y, 149);                

         } else if(k == 5) {
           //Bottom face
           int scalefactorby2 = scalefactor/2;
           int xoffset = 2*scalefactor - scalefactor/4;

           if(xoffset < 0 || xoffset > width/2)
                xoffset =  scalefactor + scalefactorby2;
                
           int xext = width/2 - xoffset;
           int xord1 = xext + 2;

           int xord2 = xord1 + xoffset + 5;
           
           int yoffset = 2*scalefactor - 10;

           int yext = height/2;
           int yordUL = yext;
           
           int yordUR = yordUL;
           
           int yordLR = yordUR + scalefactor; 

           int yordLL = yordLR - 20; 


           frames[k].vertex(xord1, yordUL, 109);
           frames[k].vertex(xord2, yordUR, 109);
           frames[k].vertex(xord2, yordLR, 109);
           frames[k].vertex(xord1, yordLL, 109);

                // now to draw left arrows                
                frames[k].stroke(155,58,39);
                frames[k].fill(155,58,39);
                frames[k].strokeWeight(10);

                int x1 = xord1 + (xord2 - xord1)/2 - 10;
                int x2 = (int) xord1 + 30;

                int y1 = (int) (yordUL + yordUR)/2 + 24;
                int y2 = (int) y1 + 4; 
                 
                frames[k].vertex(x1, y1, 139);
                frames[k].vertex(x2, y2, 139);
                frames[k].vertex(x2, y2+1, 139);
                frames[k].vertex(x1, y1+1, 139);

               //     frames[k].vertex(x2, y2, 139);
               //     frames[k].vertex(x1, y2, 149);
                
               //arrow ends               
               int x = x2 + 10;

               int y11 = y2 - 10;
               int y21 = y2 + 10;

               frames[k].vertex(x2, y2, 149);                
               frames[k].vertex(x, y11, 149);                
               frames[k].vertex(x2, y2, 149);                
               frames[k].vertex(x, y21, 149);                

                // now to draw right arrows
                x1 = xord1 + (xord2 - xord1)/2 + 10;
                x2 = (int) xord2 - 10;

                y1 = (int) (yordUL + yordUR)/2 + 24;
                y2 = (int) y1 - 4; 

                frames[k].vertex(x1, y1, 149);                
                frames[k].vertex(x2, y2, 149);
                frames[k].vertex(x2, y2+1, 149);
                frames[k].vertex(x1, y1+1, 149);
                
               //arrow ends
               x = x2 - 10;

               y11 = y2 - 10; 
               y21 = y2 + 10; 

               frames[k].vertex(x2, y2, 149);                
               frames[k].vertex(x, y11, 149);                
               frames[k].vertex(x2, y2, 149);                
               frames[k].vertex(x, y21, 149);                


         } else if(k == 2) {
           //Back face
           int scalefactorby2 = scalefactor/2;
           int xoffset = scalefactor + scalefactor/4;

           if(xoffset < 0 || xoffset > width/2)
                xoffset =  scalefactor + scalefactorby2;
                
           int xext = width/2 + xoffset;
           int xord1 = xext - 10;

           int xord2 = xord1 + scalefactor/2;
           
           int yoffset = 2*scalefactor - 20;

           int yext = height/2 - yoffset;
           int yordUL = yext;
           
           int yordUR = yordUL + 10;
           
           int yordLR = yordUR + 2*scalefactor + scalefactor/2; 

           int yordLL = yordLR + 5; 
           
           frames[k].vertex(xord1, yordUL, 99);
           frames[k].vertex(xord2, yordUR, 99);
           frames[k].vertex(xord2, yordLR, 99);
           frames[k].vertex(xord1, yordLL, 99);

                // now to draw Up arrows                
                frames[k].stroke(155,58,39);
                frames[k].fill(155,58,39);
                frames[k].strokeWeight(10);

                int x1 = xord1 + (xord2 - xord1)/4;
                int x2 = x1;

                int y1 = (int) (yordUL + yordLL)/2 - 14;
                int y2 = (int) yordUL + 24; 
                 
                frames[k].vertex(x1, y1, 139);
                frames[k].vertex(x2, y2, 139);
                frames[k].vertex(x2+1, y2, 139);
                frames[k].vertex(x1+1, y1, 139);

               //     frames[k].vertex(x2, y2, 139);
               //     frames[k].vertex(x1, y2, 149);
                
               //arrow ends               
               int x11 = x2 - 10;
               int x21 = x2 + 10;

               int y = y2 + 10;
             
               frames[k].vertex(x2, y2, 149);                
               frames[k].vertex(x11, y, 149);                
               frames[k].vertex(x2, y2, 149);                
               frames[k].vertex(x21, y, 149);                

                // now to draw down arrows
                x1 = xord1 + (xord2 - xord1)/4;
                x2 = x1;

                y1 = (int) (yordUL + yordLL)/2 + 14;
                y2 = (int) yordLL - 24; 

                frames[k].vertex(x1, y1, 149);                
                frames[k].vertex(x2, y2, 149);
                frames[k].vertex(x2, y2+1, 149);
                frames[k].vertex(x1, y1+1, 149);
                
               //arrow ends
               x11 = x2 - 10;
               x21 = x2 + 10;

               y = y2 - 10;
               
               frames[k].vertex(x2, y2, 149);                
               frames[k].vertex(x11, y, 149);                
               frames[k].vertex(x2, y2, 149);                
               frames[k].vertex(x21, y, 149);                


         }
    
            drawLeftArrow(); 

           frames[k].endShape(QUADS);

         }         
     }
 
     void display() {
       
       if(shouldDraw) {
          for(int p=0;p<frames.length;p++)
             if(p == this.sideIndexToDraw) {
                 shape(frames[p]); 
             }
       }

  //    shape(aw);
   }
  
   void setShouldDraw(String sideName, boolean shouldDraw) {
       
       if(sideName.equals(Side.FRONT_SIDE)) 
           this.sideIndexToDraw =0;
       else if(sideName.equals(Side.RIGHT_SIDE)) 
           this.sideIndexToDraw =1;
       else if(sideName.equals(Side.BACK_SIDE)) 
           this.sideIndexToDraw =2;
       else if(sideName.equals(Side.LEFT_SIDE)) 
           this.sideIndexToDraw =3;
       else if(sideName.equals(Side.TOP_SIDE)) 
           this.sideIndexToDraw =4;
       else if(sideName.equals(Side.DOWN_SIDE)) 
           this.sideIndexToDraw =5;
           
       this.shouldDraw = shouldDraw;
   }   

  void drawLeftArrow() {
  
    pushMatrix();
    
    aw = createShape();
    
    aw.beginShape();
    
    aw.stroke(0);
    aw.strokeWeight(4);
  
    aw.vertex(197, 196, 99);  
    aw.vertex(188, 201, 99);
  
    aw.strokeWeight(10);
  
    aw.vertex(221, 196, 99);
    aw.vertex(188, 201, 99);  
  
    aw.strokeWeight(4);
  
    aw.vertex(197, 206, 99);  
  
    aw.endShape();
    
  
  
    popMatrix();
  
  }

}


void rotateCube(String cycleName, String orientation) {

       println("Entering rotateCube cycleName = "+cycleName);   
       println("Entering rotateCube orientation = "+orientation);   

      Cubelet [][][] cubelets = cube.getCubelets();
            
      List<Cubelet> faceCubelets = new LinkedList<Cubelet>();
      int zindex = -1;
      int row = -1;
      int col = -1;
      
      if(cycleName.equals(Side.FRONT_SIDE)) {
          zindex = 0;
      } else if(cycleName.equals(Side.RIGHT_SIDE)) {
          col = 2;
      } else if(cycleName.equals(Side.BACK_SIDE)) {
          zindex = 2;
      } else if(cycleName.equals(Side.LEFT_SIDE)) {
          col = 0;
      } else if(cycleName.equals(Side.TOP_SIDE)) {
          row = 0;
      } else if(cycleName.equals(Side.DOWN_SIDE)) {
          row = 2;
      }
      
      if(zindex == 0 || zindex == 2) {
          println("zindex sel = " + zindex);
          for(row = 0; row < CUBESIZE; row++)
              for(col = 0; col < CUBESIZE; col++) {
                  if(cubelets[zindex][row][col] != null)  {                
                        Cubelet cubelet = cubelets[zindex][row][col];
                        faceCubelets.add(cubelet);
                  }
          }
      } else if(row== 0 || row == 2) {
          println("row sel = " + row);
      
          for(zindex = 0; zindex < CUBESIZE; zindex++)
              for(col = 0; col < CUBESIZE; col++) {
                  if(cubelets[zindex][row][col] != null)  {                
                        Cubelet cubelet = cubelets[zindex][row][col];
                        faceCubelets.add(cubelet);
                  }
          }
      
      } else if(col== 0 || col == 2) {
          println("col sel = " + col);
      
          for(zindex = 0; zindex < CUBESIZE; zindex++)
              for(row = 0; row < CUBESIZE; row++) {
                  if(cubelets[zindex][row][col] != null)  {                
                        Cubelet cubelet = cubelets[zindex][row][col];
                        faceCubelets.add(cubelet);
                  }
          }
      }

      
      rotateFace(faceCubelets, cycleName, orientation);      
}                        

// Takes a face containing 9 cubelets and rotates it in the specified orientation
// Faces must be rotated manually without using rotate function. So matrix multiplication
// is used to rotate cube faces. Since a Cube Face (E.g White Face, Yellow Face etc. different from cubelet face which has 4 vertices) 
// contains 9 cubelets, each of the 9 cubelets along
// with their individual vertices must be rotated to achieve the result. A Cube Face consists of the front face and 4 sides.
// Matrices must be formed from the vertices and rotated and then the cube Face reformed with the vertices in their new
// positions. The Front Face would contain 4 3x3 matrices one for each vertex (Top left, Top Right, Bottom Right, Bottom Left). The 
// other 4 sides would be 1x3 matix. The rotation matrix is 3x3. So for matrix multiplication, PxQ * QxT = PxT must be satisfied.
void rotateFace(List<Cubelet> cubelets, String faceName, String orientation) {

       println("Entering rotateFace faceName = "+faceName);   
       println("Entering rotateFace orientation = "+orientation);   

  
     Map<String, List<Face>> facesMap = new HashMap<String, List<Face>>();  
     List<Face> frontFaces = new LinkedList<Face>();
     List<Face> topFaces = new LinkedList<Face>();
     List<Face> leftFaces = new LinkedList<Face>();
     List<Face> rightFaces = new LinkedList<Face>();
     List<Face> bottomFaces = new LinkedList<Face>();
     List<Face> backFaces = new LinkedList<Face>();

     Iterator iter = cubelets.iterator();
     while(iter.hasNext()) {
          Cubelet cubelet = (Cubelet) iter.next();

          Face [] faces = cubelet.getFaces();                

          // get all front faces
          if(faces[0] != null)
             frontFaces.add(faces[0]);
   
          // get all top faces    
          if(faces[4] != null)
              topFaces.add(faces[4]);  

          // get all left faces    
          if(faces[3] != null)
              leftFaces.add(faces[3]);  

          // get all right faces    
          if(faces[1] != null)
              rightFaces.add(faces[1]);  

          // get all bottom faces    
          if(faces[5] != null)
              bottomFaces.add(faces[5]);      
   
          // get all back faces    
          if(faces[2] != null)
              backFaces.add(faces[2]);      
 
     }
     
     facesMap.put(Side.FRONT_SIDE, frontFaces);        
     facesMap.put(Side.LEFT_SIDE, leftFaces);        
     facesMap.put(Side.RIGHT_SIDE, rightFaces);        
     facesMap.put(Side.TOP_SIDE, topFaces);        
     facesMap.put(Side.DOWN_SIDE, bottomFaces);        
     facesMap.put(Side.BACK_SIDE, backFaces);        
    
     rotateFaces(facesMap, faceName, orientation); 
    
}

/* 
*   This method accepts a list of faces, a face name and orientation. The facesMap
*   contains a list of all the faces of the cubelet for the side the user has selected 
*   to rotate keyed by the face name. The face name contains the face the user wishes to rotate.
*   Our design of the cube is front facing. This means that the cube front is always the forward facing face
*   even if it is a left or right side cubelet. The side the user wishes to rotate will contain a flat face which could be any of
*   front, left, right, top, bottom or back. Each flat face would have adjacent side faces top, left, bottom and right. The faceName
*   tells which face is the flat face. The orientation can be clockwise or anticlockwise.
*                       2  
*                 d   -------  c
*                   |         | 
*                 3 |    4    | 1    5 - bottom face     
*                   |         | 
*                 a   -------- b 
*                       0    
*         Face ordering scheme for all cubelets               
*   
*    As per our design, Face vertices are numbered in the sequence lower left, lower right, top right and top left marked a,b,c,d above. This is our design. Others may 
*    use their own naming scheme.
*
*
*/
void rotateFaces(Map<String, List<Face>> facesMap, String faceName, String orientation) {


       println("Entering rotateFaces faceName = "+faceName);   
       println("Entering rotateFaces orientation = "+orientation);   

       List<Face> primaryFaces = facesMap.get(faceName);

       List<PVector> v1s = new LinkedList<PVector>();
       List<PVector> v2s = new LinkedList<PVector>();
       List<PVector> v3s = new LinkedList<PVector>();
       List<PVector> v4s = new LinkedList<PVector>();

       for(int d=0;d<primaryFaces.size();d++) {
       
         Face front = primaryFaces.get(d);
         
         PShape shape = front.getPShape();          
         
         PVector v1 = shape.getVertex(0);
         PVector v2 = shape.getVertex(1);
         PVector v3 = shape.getVertex(2);
         PVector v4 = shape.getVertex(3);

         v1s.add(v1);  
         v2s.add(v2);  
         v3s.add(v3);  
         v4s.add(v4);  
    
       }
       
       // got main face. Now lets gets the sides
        
        /*    While fetching the sides of the main face, which side is top faceor left face or right face or back face depends on the main face. Only if the main face is
         *    front face, left, right, top, down etc have their usual meanings. If the user has selected the left face to rotate, the left face for the left face is the back face 
         *    and the right face for the left face is the front face,as per our design. Similarly for bottom face, top face is front face and bottom face is back face. 
         */

       
       Map<String, Map> faceSidesMap = new HashMap<String, Map>();
       
       
       Map<String, String> sidesMap1 = new HashMap<String, String>();
       
       //usual meanings
       sidesMap1.put(Side.TOP_SIDE, Side.TOP_SIDE);
       sidesMap1.put(Side.LEFT_SIDE, Side.LEFT_SIDE);
       sidesMap1.put(Side.RIGHT_SIDE, Side.RIGHT_SIDE);
       sidesMap1.put(Side.DOWN_SIDE, Side.DOWN_SIDE);
       
       faceSidesMap.put(Side.FRONT_SIDE, sidesMap1);

       Map<String, String> sidesMap2 = new HashMap<String, String>();

       sidesMap2.put(Side.TOP_SIDE, Side.TOP_SIDE);
       sidesMap2.put(Side.LEFT_SIDE, Side.BACK_SIDE);
       sidesMap2.put(Side.RIGHT_SIDE, Side.FRONT_SIDE);
       sidesMap2.put(Side.DOWN_SIDE, Side.DOWN_SIDE);
       
       faceSidesMap.put(Side.LEFT_SIDE, sidesMap2);

       Map<String, String> sidesMap3 = new HashMap<String, String>();

       sidesMap3.put(Side.TOP_SIDE, Side.FRONT_SIDE);
       sidesMap3.put(Side.LEFT_SIDE, Side.LEFT_SIDE);
       sidesMap3.put(Side.RIGHT_SIDE, Side.RIGHT_SIDE);
       sidesMap3.put(Side.DOWN_SIDE, Side.BACK_SIDE);
       
       faceSidesMap.put(Side.DOWN_SIDE, sidesMap3);
        
       Map<String, String> sidesMap4 = new HashMap<String, String>();

       sidesMap4.put(Side.TOP_SIDE, Side.TOP_SIDE);
       sidesMap4.put(Side.LEFT_SIDE, Side.FRONT_SIDE);
       sidesMap4.put(Side.RIGHT_SIDE, Side.BACK_SIDE);
       sidesMap4.put(Side.DOWN_SIDE, Side.DOWN_SIDE);
       
       faceSidesMap.put(Side.RIGHT_SIDE, sidesMap4);

       Map<String, String> sidesMap5 = new HashMap<String, String>();

       sidesMap5.put(Side.TOP_SIDE, Side.BACK_SIDE);
       sidesMap5.put(Side.LEFT_SIDE, Side.LEFT_SIDE);
       sidesMap5.put(Side.RIGHT_SIDE, Side.RIGHT_SIDE);
       sidesMap5.put(Side.DOWN_SIDE, Side.FRONT_SIDE);
       
       faceSidesMap.put(Side.TOP_SIDE, sidesMap5);


       Map<String, String> sidesMap6 = new HashMap<String, String>();

       sidesMap6.put(Side.TOP_SIDE, Side.FRONT_SIDE);
       sidesMap6.put(Side.LEFT_SIDE, Side.LEFT_SIDE);
       sidesMap6.put(Side.RIGHT_SIDE, Side.RIGHT_SIDE);
       sidesMap6.put(Side.DOWN_SIDE, Side.BACK_SIDE);
       
       faceSidesMap.put(Side.DOWN_SIDE, sidesMap6);

       println("main face = " + faceName);
       
       Map<String, String> sides_map = faceSidesMap.get(faceName);

       String theTopSide = sides_map.get(Side.TOP_SIDE);

       println("top side = " + theTopSide);

       List<Face> topFaces = facesMap.get(theTopSide);

       List<PVector> v1tops = new LinkedList<PVector>();
       List<PVector> v2tops = new LinkedList<PVector>();
       List<PVector> v3tops = new LinkedList<PVector>();
       List<PVector> v4tops = new LinkedList<PVector>();
       
       for(int d=0;d<topFaces.size();d++) {
       
         Face top = topFaces.get(d);
         
         PShape shape = top.getPShape();          
         
         PVector v1top = shape.getVertex(0);
         PVector v2top = shape.getVertex(1);
         PVector v3top = shape.getVertex(2);
         PVector v4top = shape.getVertex(3);

         v1tops.add(v1top);  
         v2tops.add(v2top);  
         v3tops.add(v3top);  
         v4tops.add(v4top);  
       }       
 
       // left side
       String theLeftSide = sides_map.get(Side.LEFT_SIDE);

       println("left side = " + theLeftSide);
      
       List<Face> leftFaces = facesMap.get(theLeftSide);

       List<PVector> v1lefts = new LinkedList<PVector>();
       List<PVector> v2lefts = new LinkedList<PVector>();
       List<PVector> v3lefts = new LinkedList<PVector>();
       List<PVector> v4lefts = new LinkedList<PVector>();
       
       for(int d=0;d<leftFaces.size();d++) {
       
         Face left = leftFaces.get(d);
         
         PShape shape = left.getPShape();          
         
         PVector v1left = shape.getVertex(0);
         PVector v2left = shape.getVertex(1);
         PVector v3left = shape.getVertex(2);
         PVector v4left = shape.getVertex(3);

         v1lefts.add(v1left);  
         v2lefts.add(v2left);  
         v3lefts.add(v3left);  
         v4lefts.add(v4left);  
       }       
 
       // bottom side
       String theDownSide = sides_map.get(Side.DOWN_SIDE);

       List<Face> bottomFaces = facesMap.get(theDownSide);

       List<PVector> v1bottoms = new LinkedList<PVector>();
       List<PVector> v2bottoms = new LinkedList<PVector>();
       List<PVector> v3bottoms = new LinkedList<PVector>();
       List<PVector> v4bottoms = new LinkedList<PVector>();

       println("bottomFaces.size() = " + bottomFaces.size());
       
       for(int d=0;d<bottomFaces.size();d++) {
       
         Face bottom = bottomFaces.get(d);
         
         PShape shape = bottom.getPShape();          
         
         PVector v1bottom = shape.getVertex(0);
         PVector v2bottom = shape.getVertex(1);
         PVector v3bottom = shape.getVertex(2);
         PVector v4bottom = shape.getVertex(3);

         v1bottoms.add(v1bottom);  
         v2bottoms.add(v2bottom);  
         v3bottoms.add(v3bottom);  
         v4bottoms.add(v4bottom);  
       }       

       // right side
       String theRightSide = sides_map.get(Side.RIGHT_SIDE);

       List<Face> rightFaces = facesMap.get(theRightSide);

       List<PVector> v1rights = new LinkedList<PVector>();
       List<PVector> v2rights = new LinkedList<PVector>();
       List<PVector> v3rights = new LinkedList<PVector>();
       List<PVector> v4rights = new LinkedList<PVector>();
       
       for(int d=0;d<rightFaces.size();d++) {
       
         Face right = rightFaces.get(d);
         
         PShape shape = right.getPShape();          
         
         PVector v1right = shape.getVertex(0);
         PVector v2right = shape.getVertex(1);
         PVector v3right = shape.getVertex(2);
         PVector v4right = shape.getVertex(3);

         v1rights.add(v1right);  
         v2rights.add(v2right);  
         v3rights.add(v3right);  
         v4rights.add(v4right);  
       }       
 
       // lets rotate individual vertices (x,y,z)
       // (x,y,z) will form a 1x3 matrix which we can multiply with a 3x3 rotation matrix
       
       DoubleMatrix2D nodes_XYZ_1 = new DenseDoubleMatrix2D(1, 3); 

       result_XYZ_1 = new DenseDoubleMatrix2D(1, 3); 

      DoubleMatrix2D rotationMatrix_X = new DenseDoubleMatrix2D(3, 3); 
      DoubleMatrix2D rotationMatrix_Y = new DenseDoubleMatrix2D(3, 3); 
      DoubleMatrix2D rotationMatrix_Z = new DenseDoubleMatrix2D(3, 3); 
     DoubleMatrix2D rotationMatrix_Z_II = new DenseDoubleMatrix2D(3, 3); 

      DoubleMatrix2D rotationMatrix_X_T = new DenseDoubleMatrix2D(3, 3); 
      DoubleMatrix2D rotationMatrix_Y_T = new DenseDoubleMatrix2D(3, 3); 
      DoubleMatrix2D rotationMatrix_Z_T = new DenseDoubleMatrix2D(3, 3); 


      DoubleMatrix2D rotationMatrixToUse = null;
      if(faceName.equals(Side.FRONT_SIDE) && orientation.equals("anti-clockwise")) 
          rotationMatrixToUse = rotationMatrix_Z;
      else if(faceName.equals(Side.FRONT_SIDE) && orientation.equals("clockwise")) 
          rotationMatrixToUse = rotationMatrix_Z_T;
      else if(faceName.equals(Side.LEFT_SIDE) && orientation.equals("clockwise")) 
          rotationMatrixToUse = rotationMatrix_X;
      else if(faceName.equals(Side.LEFT_SIDE) && orientation.equals("anti-clockwise")) 
          rotationMatrixToUse = rotationMatrix_X_T;
      else if(faceName.equals(Side.RIGHT_SIDE) && orientation.equals("clockwise")) 
          rotationMatrixToUse = rotationMatrix_X;
      else if(faceName.equals(Side.RIGHT_SIDE) && orientation.equals("anti-clockwise")) 
          rotationMatrixToUse = rotationMatrix_X_T;
      else if(faceName.equals(Side.BACK_SIDE) && orientation.equals("clockwise")) 
          rotationMatrixToUse = rotationMatrix_Z;
      else if(faceName.equals(Side.BACK_SIDE) && orientation.equals("anti-clockwise")) 
          rotationMatrixToUse = rotationMatrix_Z_T;
      else if(faceName.equals(Side.TOP_SIDE) && orientation.equals("clockwise")) 
          rotationMatrixToUse = rotationMatrix_Y;
      else if(faceName.equals(Side.TOP_SIDE) && orientation.equals("anti-clockwise")) 
          rotationMatrixToUse = rotationMatrix_Y_T;
      else if(faceName.equals(Side.DOWN_SIDE) && orientation.equals("clockwise")) 
          rotationMatrixToUse = rotationMatrix_Y;
      else if(faceName.equals(Side.DOWN_SIDE) && orientation.equals("anti-clockwise")) 
          rotationMatrixToUse = rotationMatrix_Y_T;

  //    rotationAngle+=.1;
      rotationAngle = HALF_PI;

      if(rotationAngle >= HALF_PI)
        rotationAngle = HALF_PI;

   //     rotationAngle = radians(135);

        rotationAngle = HALF_PI;
        
        rotationMatrix_X.set(0, 0, 1);
        rotationMatrix_X.set(0, 1, 0);
        rotationMatrix_X.set(0, 2, 0);

        rotationMatrix_X.set(1, 0, 0);
        rotationMatrix_X.set(1, 1, Math.cos(rotationAngle) < 0? 0 : Math.cos(rotationAngle));
        rotationMatrix_X.set(1, 2, -1 * Math.sin(rotationAngle));

        rotationMatrix_X.set(2, 0, 0);
        rotationMatrix_X.set(2, 1, Math.sin(rotationAngle));
        rotationMatrix_X.set(2, 2, Math.cos(rotationAngle) < 0? 0 : Math.cos(rotationAngle));

        rotationMatrix_X_T.set(0, 0, 1);
        rotationMatrix_X_T.set(0, 1, 0);
        rotationMatrix_X_T.set(0, 2, 0);

        rotationMatrix_X_T.set(1, 0, 0);
        rotationMatrix_X_T.set(1, 1, Math.cos(rotationAngle) < 0? 0 : Math.cos(rotationAngle));
        rotationMatrix_X_T.set(1, 2, Math.sin(rotationAngle));

        rotationMatrix_X_T.set(2, 0, 0);
        rotationMatrix_X_T.set(2, 1, -1 * Math.sin(rotationAngle));
        rotationMatrix_X_T.set(2, 2, Math.cos(rotationAngle) < 0? 0 : Math.cos(rotationAngle));


        rotationMatrix_Y.set(0, 0, Math.cos(rotationAngle) < 0? 0 : Math.cos(rotationAngle));
        rotationMatrix_Y.set(0, 1, 0);
        rotationMatrix_Y.set(0, 2, Math.sin(rotationAngle));

        rotationMatrix_Y.set(1, 0, 0);
        rotationMatrix_Y.set(1, 1, 1);
        rotationMatrix_Y.set(1, 2, 0);

        rotationMatrix_Y.set(2, 0, -1 * Math.sin(rotationAngle));
        rotationMatrix_Y.set(2, 1, 0);
        rotationMatrix_Y.set(2, 2, Math.cos(rotationAngle) < 0? 0 : Math.cos(rotationAngle));

        rotationMatrix_Y_T.set(0, 0, Math.cos(rotationAngle) < 0? 0 : Math.cos(rotationAngle));
        rotationMatrix_Y_T.set(0, 1, 0);
        rotationMatrix_Y_T.set(0, 2, -1 * Math.sin(rotationAngle));

        rotationMatrix_Y_T.set(1, 0, 0);
        rotationMatrix_Y_T.set(1, 1, 1);
        rotationMatrix_Y_T.set(1, 2, 0);

        rotationMatrix_Y_T.set(2, 0, Math.sin(rotationAngle));
        rotationMatrix_Y_T.set(2, 1, 0);
        rotationMatrix_Y_T.set(2, 2, Math.cos(rotationAngle) < 0? 0 : Math.cos(rotationAngle));


        rotationMatrix_Z.set(0, 0, Math.cos(rotationAngle) < 0? 0 : Math.cos(rotationAngle));
        rotationMatrix_Z.set(0, 1, -1 * Math.sin(rotationAngle));
        rotationMatrix_Z.set(0, 2, 0);

        rotationMatrix_Z.set(1, 0, Math.sin(rotationAngle));
        rotationMatrix_Z.set(1, 1, Math.cos(rotationAngle) < 0? 0 : Math.cos(rotationAngle));
        rotationMatrix_Z.set(1, 2, 0);

        rotationMatrix_Z.set(2, 0, 0);
        rotationMatrix_Z.set(2, 1, 0);
        rotationMatrix_Z.set(2, 2, 1);

        rotationMatrix_Z_T.set(0, 0, Math.cos(rotationAngle) < 0? 0 : Math.cos(rotationAngle));
        rotationMatrix_Z_T.set(0, 1, Math.sin(rotationAngle));
        rotationMatrix_Z_T.set(0, 2, 0);

        rotationMatrix_Z_T.set(1, 0, -1 * Math.sin(rotationAngle));
        rotationMatrix_Z_T.set(1, 1, Math.cos(rotationAngle) < 0? 0 : Math.cos(rotationAngle));
        rotationMatrix_Z_T.set(1, 2, 0);

        rotationMatrix_Z_T.set(2, 0, 0);
        rotationMatrix_Z_T.set(2, 1, 0);
        rotationMatrix_Z_T.set(2, 2, 1);
  
      for(int s=0;s<v1s.size();s++) {

        print(" x  = " + v1s.get(s).x);
        print(" y  = " + v1s.get(s).y);
        println(" z  = " + v1s.get(s).z);
        
        nodes_XYZ_1.set(0, 0, v1s.get(s).x);
        nodes_XYZ_1.set(0, 1, v1s.get(s).y);
        nodes_XYZ_1.set(0, 2, v1s.get(s).z);

      //  if(s < 3) 
      //    nodes_XYZ_1.zMult(rotationMatrix_Z_II, result_XYZ_1);
      //  else        
          nodes_XYZ_1.zMult(rotationMatrixToUse, result_XYZ_1);

     //   println(" res = " + result_XYZ_1);

        applyResultOffset(faceName, orientation);

        double xord = result_XYZ_1.get(0, 0);
        double yord = result_XYZ_1.get(0, 1);
        double zord = result_XYZ_1.get(0, 2);

        print(" xaft  = " + xord);
        print(" yaft  = " + yord);
        println(" zaft  = " + zord);
        
        v1s.set(s, new PVector((int)xord, (int)yord, (int)zord));
      }
      
      for(int s=0;s<v2s.size();s++) {

        nodes_XYZ_1.set(0, 0, v2s.get(s).x);
        nodes_XYZ_1.set(0, 1, v2s.get(s).y);
        nodes_XYZ_1.set(0, 2, v2s.get(s).z);

        nodes_XYZ_1.zMult(rotationMatrixToUse, result_XYZ_1);

        applyResultOffset(faceName, orientation);

        double xord = result_XYZ_1.get(0, 0);
        double yord = result_XYZ_1.get(0, 1);
        double zord = result_XYZ_1.get(0, 2);
        
        v2s.set(s, new PVector((int)xord, (int)yord, (int)zord));

      }

      for(int s=0;s<v3s.size();s++) {
        nodes_XYZ_1.set(0, 0, v3s.get(s).x);
        nodes_XYZ_1.set(0, 1, v3s.get(s).y);
        nodes_XYZ_1.set(0, 2, v3s.get(s).z);

        nodes_XYZ_1.zMult(rotationMatrixToUse, result_XYZ_1);

        applyResultOffset(faceName, orientation);

        double xord = result_XYZ_1.get(0, 0);
        double yord = result_XYZ_1.get(0, 1);
        double zord = result_XYZ_1.get(0, 2);
        
        v3s.set(s, new PVector((int)xord, (int)yord, (int)zord));
      }

      for(int s=0;s<v4s.size();s++) {
        nodes_XYZ_1.set(0, 0, v4s.get(s).x);
        nodes_XYZ_1.set(0, 1, v4s.get(s).y);
        nodes_XYZ_1.set(0, 2, v4s.get(s).z);

        nodes_XYZ_1.zMult(rotationMatrixToUse, result_XYZ_1);

        applyResultOffset(faceName, orientation);

        double xord = result_XYZ_1.get(0, 0);
        double yord = result_XYZ_1.get(0, 1);
        double zord = result_XYZ_1.get(0, 2);
        
        v4s.set(s, new PVector((int)xord, (int)yord, (int)zord));
      }
  
      
       /* In Graphics, rotation means Matrix multiplication MatMult. However we are using MatMult to rotate a cube   
        * We have our cube centered at point 0,0,0 in our coordinate system. When we rotate a face using MatMult,
        * the cube does rotate. But along with it our center also shifts. We want our center to remain the same. To
        * achieve correct result, we have to add/subtract an offset equal to the amount the center has shifted along the
        * axis in which the cube was rotated. The offsets for various axis and rotation orientation is as follows:
        *
        *      Rotation about X-axis in the clockwise direction 
        *        Use rotationMatrix_X and Minus Y by 1 from the result       
        *      Rotation about X-axis in the anti-clockwise direction 
        *        Use rotationMatrix_X_T - Minus Z by 1 from the result
        *      Rotation about Y-axis ofthe Top Face in the anti-clockwise direction 
        *       Use rotationMatrix_Y and - Minus Z by 1 from the result       
        *      Rotation about Y-axis ofthe Bottom Face in the clockwise direction 
        *       Use rotationMatrix_Y and - Minus Z by 1 from the result       
        *      Rotation about Y-axis ofthe Top Face in the clockwise direction 
        *       Use rotationMatrix_Y_T and - Minus X by 1 from the result       
        *      Rotation about Y-axis ofthe Bottom Face in the anti-clockwise direction 
        *       Use rotationMatrix_Y_T and - Minus X by 1 from the result       
        *      Rotation about Z-axis in the clockwise direction 
        *       Use rotationMatrix_Z and Minus X by 1 from the result       
        *      Rotation about Z-axis in the anti-clockwise direction 
        *        Use rotationMatrix_Z_T and Minus Y by 1 from the result       
        *
        *
        */
        
  //      System.out.println("matrix = " + rotationMatrix_Z);

      //    println("v1s aft = " + v1s);  


          for(int d=0;d<primaryFaces.size();d++) {
             Face front = primaryFaces.get(d);
             PShape shape = front.getPShape();          
             shape.setVertex(0, v1s.get(d).x, v1s.get(d).y, v1s.get(d).z);
             shape.setVertex(1, v2s.get(d).x, v2s.get(d).y, v2s.get(d).z);
             shape.setVertex(2, v3s.get(d).x, v3s.get(d).y, v3s.get(d).z);
             shape.setVertex(3, v4s.get(d).x, v4s.get(d).y, v4s.get(d).z);

         }

        // lets do the sides. Matrices are row x col. The sides are either 3x1 or 1x3. 
        // Top/Bottom side is 1x3. Left/Right side is 3x1. For matrix multiplication,
        // the following must be satisfied
        //      MxN   *   NxQ  = M x Q
        //  Our rotation matrix is 3x3 and top side is 1x3 amd so cannot be multiplied 
        // with the top side on the right. We have to multiply with top side on the left
        // e.g top side 1x3 * rotation matrix 3x3  = 1x3
        // For left/right side, the rotation matrix will be on the left
        // e.g rotation matrix 3x3 * left/right side 3x1 = 3x1

        println("Top side");
      
       //TOP side         
       nodes_XYZ_1 = new DenseDoubleMatrix2D(1, 3); 

       result_XYZ_1 = new DenseDoubleMatrix2D(1, 3); 

      for(int s=0;s<v1tops.size();s++) {
        nodes_XYZ_1.set(0, 0, v1tops.get(s).x);
        nodes_XYZ_1.set(0, 1, v1tops.get(s).y);
        nodes_XYZ_1.set(0, 2, v1tops.get(s).z);

        println("nodes_XYZ_1 = "+nodes_XYZ_1);

        nodes_XYZ_1.zMult(rotationMatrixToUse, result_XYZ_1);

        applyResultOffset(faceName, orientation);

        double xord = result_XYZ_1.get(0, 0);
        double yord = result_XYZ_1.get(0, 1);
        double zord = result_XYZ_1.get(0, 2);
        
        v1tops.set(s, new PVector((int)xord, (int)yord, (int)zord));

      }
      
      for(int s=0;s<v2tops.size();s++) {
        nodes_XYZ_1.set(0, 0, v2tops.get(s).x);
        nodes_XYZ_1.set(0, 1, v2tops.get(s).y);
        nodes_XYZ_1.set(0, 2, v2tops.get(s).z);

        nodes_XYZ_1.zMult(rotationMatrixToUse, result_XYZ_1);

        applyResultOffset(faceName, orientation);

        double xord = result_XYZ_1.get(0, 0);
        double yord = result_XYZ_1.get(0, 1);
        double zord = result_XYZ_1.get(0, 2);
        
        v2tops.set(s, new PVector((int)xord, (int)yord, (int)zord));
      }

      for(int s=0;s<v3tops.size();s++) {
        nodes_XYZ_1.set(0, 0, v3tops.get(s).x);
        nodes_XYZ_1.set(0, 1, v3tops.get(s).y);
        nodes_XYZ_1.set(0, 2, v3tops.get(s).z);

        nodes_XYZ_1.zMult(rotationMatrixToUse, result_XYZ_1);

        applyResultOffset(faceName, orientation);

        double xord = result_XYZ_1.get(0, 0);
        double yord = result_XYZ_1.get(0, 1);
        double zord = result_XYZ_1.get(0, 2);
        
        v3tops.set(s, new PVector((int)xord, (int)yord, (int)zord));
      }

      for(int s=0;s<v4tops.size();s++) {
        nodes_XYZ_1.set(0, 0, v4tops.get(s).x);
        nodes_XYZ_1.set(0, 1, v4tops.get(s).y);
        nodes_XYZ_1.set(0, 2, v4tops.get(s).z);

        nodes_XYZ_1.zMult(rotationMatrixToUse, result_XYZ_1);

        applyResultOffset(faceName, orientation);

        double xord = result_XYZ_1.get(0, 0);
        double yord = result_XYZ_1.get(0, 1);
        double zord = result_XYZ_1.get(0, 2);
        
        v4tops.set(s, new PVector((int)xord, (int)yord, (int)zord));
      }


      for(int d=0;d<topFaces.size();d++) {
         Face top = topFaces.get(d);
         PShape shape = top.getPShape();          
         shape.setVertex(0, v1tops.get(d).x, v1tops.get(d).y, v1tops.get(d).z);
         shape.setVertex(1, v2tops.get(d).x, v2tops.get(d).y, v2tops.get(d).z);
         shape.setVertex(2, v3tops.get(d).x, v3tops.get(d).y, v3tops.get(d).z);
         shape.setVertex(3, v4tops.get(d).x, v4tops.get(d).y, v4tops.get(d).z);
   
     }

      
      // LEFT SIDE
        println("Left side");

       nodes_XYZ_1 = new DenseDoubleMatrix2D(1, 3); 

       result_XYZ_1 = new DenseDoubleMatrix2D(1, 3); 

      for(int s=0;s<v1lefts.size();s++) {
          nodes_XYZ_1.set(0, 0, v1lefts.get(s).x);
          nodes_XYZ_1.set(0, 1, v1lefts.get(s).y);
          nodes_XYZ_1.set(0, 2, v1lefts.get(s).z);

          println("nodes_XYZ_1 = "+nodes_XYZ_1);

          nodes_XYZ_1.zMult(rotationMatrixToUse, result_XYZ_1);

          applyResultOffset(faceName, orientation);

          double xord = result_XYZ_1.get(0, 0);
          double yord = result_XYZ_1.get(0, 1);
          double zord = result_XYZ_1.get(0, 2);
        
          v1lefts.set(s, new PVector((int)xord, (int)yord, (int)zord));
      }
      
      for(int s=0;s<v2lefts.size();s++) {
          nodes_XYZ_1.set(0, 0, v2lefts.get(s).x);
          nodes_XYZ_1.set(0, 1, v2lefts.get(s).y);
          nodes_XYZ_1.set(0, 2, v2lefts.get(s).z);

          nodes_XYZ_1.zMult(rotationMatrixToUse, result_XYZ_1);

          applyResultOffset(faceName, orientation);

          double xord = result_XYZ_1.get(0, 0);
          double yord = result_XYZ_1.get(0, 1);
          double zord = result_XYZ_1.get(0, 2);
        
          v2lefts.set(s, new PVector((int)xord, (int)yord, (int)zord));
      }

      for(int s=0;s<v3lefts.size();s++) {
          nodes_XYZ_1.set(0, 0, v3lefts.get(s).x);
          nodes_XYZ_1.set(0, 1, v3lefts.get(s).y);
          nodes_XYZ_1.set(0, 2, v3lefts.get(s).z);

          nodes_XYZ_1.zMult(rotationMatrixToUse, result_XYZ_1);

          applyResultOffset(faceName, orientation);

          double xord = result_XYZ_1.get(0, 0);
          double yord = result_XYZ_1.get(0, 1);
          double zord = result_XYZ_1.get(0, 2);
        
          v3lefts.set(s, new PVector((int)xord, (int)yord, (int)zord));
      }

      for(int s=0;s<v4lefts.size();s++) {
          nodes_XYZ_1.set(0, 0, v4lefts.get(s).x);
          nodes_XYZ_1.set(0, 1, v4lefts.get(s).y);
          nodes_XYZ_1.set(0, 2, v4lefts.get(s).z);

          nodes_XYZ_1.zMult(rotationMatrixToUse, result_XYZ_1);

          applyResultOffset(faceName, orientation);

          double xord = result_XYZ_1.get(0, 0);
          double yord = result_XYZ_1.get(0, 1);
          double zord = result_XYZ_1.get(0, 2);
        
          v4lefts.set(s, new PVector((int)xord, (int)yord, (int)zord));
      }

       for(int d=0;d<leftFaces.size();d++) {
           Face left = leftFaces.get(d);
           PShape shape = left.getPShape();          
           shape.setVertex(0, v1lefts.get(d).x, v1lefts.get(d).y, v1lefts.get(d).z);
           shape.setVertex(1, v2lefts.get(d).x, v2lefts.get(d).y, v2lefts.get(d).z);
           shape.setVertex(2, v3lefts.get(d).x, v3lefts.get(d).y, v3lefts.get(d).z);
           shape.setVertex(3, v4lefts.get(d).x, v4lefts.get(d).y, v4lefts.get(d).z);

       }
      
        //Bottom Side
       println("Bottom side");

       nodes_XYZ_1 = new DenseDoubleMatrix2D(1, 3); 

       result_XYZ_1 = new DenseDoubleMatrix2D(1, 3); 

      for(int s=0;s<v1bottoms.size();s++) {
        nodes_XYZ_1.set(0, 0, v1bottoms.get(s).x);
        nodes_XYZ_1.set(0, 1, v1bottoms.get(s).y);
        nodes_XYZ_1.set(0, 2, v1bottoms.get(s).z);

        println("nodes_XYZ_1 = "+nodes_XYZ_1);

        nodes_XYZ_1.zMult(rotationMatrixToUse, result_XYZ_1);

        applyResultOffset(faceName, orientation);

        double xord = result_XYZ_1.get(0, 0);
        double yord = result_XYZ_1.get(0, 1);
        double zord = result_XYZ_1.get(0, 2);
      
        v1bottoms.set(s, new PVector((int)xord, (int)yord, (int)zord));
      }
      
      for(int s=0;s<v2bottoms.size();s++) {
        nodes_XYZ_1.set(0, 0, v2bottoms.get(s).x);
        nodes_XYZ_1.set(0, 1, v2bottoms.get(s).y);
        nodes_XYZ_1.set(0, 2, v2bottoms.get(s).z);

        nodes_XYZ_1.zMult(rotationMatrixToUse, result_XYZ_1);

        applyResultOffset(faceName, orientation);

        double xord = result_XYZ_1.get(0, 0);
        double yord = result_XYZ_1.get(0, 1);
        double zord = result_XYZ_1.get(0, 2);
      
        v2bottoms.set(s, new PVector((int)xord, (int)yord, (int)zord));
      }

      for(int s=0;s<v3bottoms.size();s++) {
        nodes_XYZ_1.set(0, 0, v3bottoms.get(s).x);
        nodes_XYZ_1.set(0, 1, v3bottoms.get(s).y);
        nodes_XYZ_1.set(0, 2, v3bottoms.get(s).z);

        nodes_XYZ_1.zMult(rotationMatrixToUse, result_XYZ_1);

        applyResultOffset(faceName, orientation);

        double xord = result_XYZ_1.get(0, 0);
        double yord = result_XYZ_1.get(0, 1);
        double zord = result_XYZ_1.get(0, 2);
      
        v3bottoms.set(s, new PVector((int)xord, (int)yord, (int)zord));
      }

      for(int s=0;s<v4bottoms.size();s++) {
        nodes_XYZ_1.set(0, 0, v4bottoms.get(s).x);
        nodes_XYZ_1.set(0, 1, v4bottoms.get(s).y);
        nodes_XYZ_1.set(0, 2, v4bottoms.get(s).z);

        nodes_XYZ_1.zMult(rotationMatrixToUse, result_XYZ_1);

        applyResultOffset(faceName, orientation);

        double xord = result_XYZ_1.get(0, 0);
        double yord = result_XYZ_1.get(0, 1);
        double zord = result_XYZ_1.get(0, 2);
      
        v4bottoms.set(s, new PVector((int)xord, (int)yord, (int)zord));
      }

      for(int d=0;d<bottomFaces.size();d++) {
         Face bottom = bottomFaces.get(d);
         PShape shape = bottom.getPShape();          
         shape.setVertex(0, v1bottoms.get(d).x, v1bottoms.get(d).y, v1bottoms.get(d).z);
         shape.setVertex(1, v2bottoms.get(d).x, v2bottoms.get(d).y, v2bottoms.get(d).z);
         shape.setVertex(2, v3bottoms.get(d).x, v3bottoms.get(d).y, v3bottoms.get(d).z);
         shape.setVertex(3, v4bottoms.get(d).x, v4bottoms.get(d).y, v4bottoms.get(d).z);

     }

        //RIGHT Side
       nodes_XYZ_1 = new DenseDoubleMatrix2D(1, 3); 

       result_XYZ_1 = new DenseDoubleMatrix2D(1, 3); 

      for(int s=0;s<v1rights.size();s++) {
        nodes_XYZ_1.set(0, 0, v1rights.get(s).x);
        nodes_XYZ_1.set(0, 1, v1rights.get(s).y);
        nodes_XYZ_1.set(0, 2, v1rights.get(s).z);

        nodes_XYZ_1.zMult(rotationMatrixToUse, result_XYZ_1);

        applyResultOffset(faceName, orientation);

        double xord = result_XYZ_1.get(0, 0);
        double yord = result_XYZ_1.get(0, 1);
        double zord = result_XYZ_1.get(0, 2);
      
        v1rights.set(s, new PVector((int)xord, (int)yord, (int)zord));
      }
      
      for(int s=0;s<v2rights.size();s++) {
        nodes_XYZ_1.set(0, 0, v2rights.get(s).x);
        nodes_XYZ_1.set(0, 1, v2rights.get(s).y);
        nodes_XYZ_1.set(0, 2, v2rights.get(s).z);

        nodes_XYZ_1.zMult(rotationMatrixToUse, result_XYZ_1);

        applyResultOffset(faceName, orientation);

        double xord = result_XYZ_1.get(0, 0);
        double yord = result_XYZ_1.get(0, 1);
        double zord = result_XYZ_1.get(0, 2);
      
        v2rights.set(s, new PVector((int)xord, (int)yord, (int)zord));
      }

      for(int s=0;s<v3rights.size();s++) {
        nodes_XYZ_1.set(0, 0, v3rights.get(s).x);
        nodes_XYZ_1.set(0, 1, v3rights.get(s).y);
        nodes_XYZ_1.set(0, 2, v3rights.get(s).z);

        nodes_XYZ_1.zMult(rotationMatrixToUse, result_XYZ_1);

        applyResultOffset(faceName, orientation);

        double xord = result_XYZ_1.get(0, 0);
        double yord = result_XYZ_1.get(0, 1);
        double zord = result_XYZ_1.get(0, 2);
      
        v3rights.set(s, new PVector((int)xord, (int)yord, (int)zord));
      }

      for(int s=0;s<v4rights.size();s++) {
        nodes_XYZ_1.set(0, 0, v4rights.get(s).x);
        nodes_XYZ_1.set(0, 1, v4rights.get(s).y);
        nodes_XYZ_1.set(0, 2, v4rights.get(s).z);

        nodes_XYZ_1.zMult(rotationMatrixToUse, result_XYZ_1);

        applyResultOffset(faceName, orientation);

        double xord = result_XYZ_1.get(0, 0);
        double yord = result_XYZ_1.get(0, 1);
        double zord = result_XYZ_1.get(0, 2);
      
        v4rights.set(s, new PVector((int)xord, (int)yord, (int)zord));
      }

      for(int d=0;d<rightFaces.size();d++) {
         Face right = rightFaces.get(d);
         PShape shape = right.getPShape();          
         shape.setVertex(0, v1rights.get(d).x, v1rights.get(d).y, v1rights.get(d).z);
         shape.setVertex(1, v2rights.get(d).x, v2rights.get(d).y, v2rights.get(d).z);
         shape.setVertex(2, v3rights.get(d).x, v3rights.get(d).y, v3rights.get(d).z);
         shape.setVertex(3, v4rights.get(d).x, v4rights.get(d).y, v4rights.get(d).z);

     }

}


void applyResultOffset(String faceName, String orientation) {

        if(
              (faceName.equals(Side.FRONT_SIDE) ||
                      faceName.equals(Side.BACK_SIDE)) && orientation.equals("anti-clockwise")) {
            //Minus X by 1 from the result
            
            println("front clk ");
            int nrows = result_XYZ_1.rows();
            int ncols = result_XYZ_1.columns();

            double x1 = result_XYZ_1.get(0, 0);                  
            double y1 = result_XYZ_1.get(0, 1);                  
            double z1 = result_XYZ_1.get(0, 2);                  

       //     print("res x =" + result_XYZ_1.get(0, 0));    
       //     print("res y =" + result_XYZ_1.get(0, 1));    
       //     print("res z =" + result_XYZ_1.get(0, 2));    

            double x1dash = Math.round(x1);
            double y1dash = Math.round(y1 - 1.0);
            double z1dash = Math.round(z1);
     
            result_XYZ_1.set(0, 0, x1dash);
            result_XYZ_1.set(0, 1, y1dash);
            result_XYZ_1.set(0, 2, z1dash);
                  
            print("res x aft=" + result_XYZ_1.get(0, 0));    
            print("res y aft=" + result_XYZ_1.get(0, 1));    
            print("res z aft=" + result_XYZ_1.get(0, 2));    
                  
        }
        else if(
              (faceName.equals(Side.FRONT_SIDE) ||
                      faceName.equals(Side.BACK_SIDE)) && orientation.equals("clockwise")) {
            //Minus X by 1 from the result
            println("front aclk ");

            double x1 = result_XYZ_1.get(0, 0);                  
            double y1 = result_XYZ_1.get(0, 1);                  
            double z1 = result_XYZ_1.get(0, 2);                  

       //     print("res x =" + result_XYZ_1.get(0, 0));    
       //     print("res y =" + result_XYZ_1.get(0, 1));    
       //     print("res z =" + result_XYZ_1.get(0, 2));    

            double x1dash = Math.round(x1 - 1.0);
            double y1dash = Math.round(y1);
            double z1dash = Math.round(z1);
     
            result_XYZ_1.set(0, 0, x1dash);
            result_XYZ_1.set(0, 1, y1dash);
            result_XYZ_1.set(0, 2, z1dash);

            print("res x aft=" + result_XYZ_1.get(0, 0));    
            print("res y aft=" + result_XYZ_1.get(0, 1));    
            print("res z aft=" + result_XYZ_1.get(0, 2));    
                                        
        }
        else if(
                  (faceName.equals(Side.LEFT_SIDE) ||
                              faceName.equals(Side.RIGHT_SIDE)) && orientation.equals("clockwise")) {
            //Minus Z by 1 from the result

            println("left-rite clk ");

      //      print("res x =" + result_XYZ_1.get(0, 0));    
      //      print("res y =" + result_XYZ_1.get(0, 1));    
      //      print("res z =" + result_XYZ_1.get(0, 2));    

            double x1 = result_XYZ_1.get(0, 0);                  
            double y1 = result_XYZ_1.get(0, 1);                  
            double z1 = result_XYZ_1.get(0, 2);                  

            double x1dash = Math.round(x1);
            double y1dash = Math.round(y1);
            double z1dash = Math.round(z1 - 1.0);
    
            result_XYZ_1.set(0, 0, x1dash);                  
            result_XYZ_1.set(0, 1, y1dash);
            result_XYZ_1.set(0, 2, z1dash);

            print("res x aft=" + result_XYZ_1.get(0, 0));    
            print("res y aft=" + result_XYZ_1.get(0, 1));    
            print("res z aft=" + result_XYZ_1.get(0, 2));    
                  
        }
        else if(
                  (faceName.equals(Side.LEFT_SIDE) ||
                              faceName.equals(Side.RIGHT_SIDE)) && orientation.equals("anti-clockwise")) {
            //Minus Y by 1 from the result
            double x1 = result_XYZ_1.get(0, 0);                  
            double y1 = result_XYZ_1.get(0, 1);                  
            double z1 = result_XYZ_1.get(0, 2);                  

            println("left-rite aclk ");

      //      print("res x =" + result_XYZ_1.get(0, 0));    
      //      print("res y =" + result_XYZ_1.get(0, 1));    
      //      print("res z =" + result_XYZ_1.get(0, 2));    
    
            double x1dash = Math.round(x1);
            double y1dash = Math.round(y1 - 1.0);
            double z1dash = Math.round(z1);

            result_XYZ_1.set(0, 0, x1dash);
            result_XYZ_1.set(0, 1, y1dash); 
            result_XYZ_1.set(0, 2, z1dash);

            print("res x aft=" + result_XYZ_1.get(0, 0));    
            print("res y aft=" + result_XYZ_1.get(0, 1));    
            print("res z aft=" + result_XYZ_1.get(0, 2));    
                  
        }
        else if(faceName.equals(Side.TOP_SIDE) && orientation.equals("clockwise")) {
             // Minus X by 1 
            println("top clk ");

       //     print("res x =" + result_XYZ_1.get(0, 0));    
       //     print("res y =" + result_XYZ_1.get(0, 1));    
       //     print("res z =" + result_XYZ_1.get(0, 2));    
 
            double x1 = result_XYZ_1.get(0, 0);                  
            double y1 = result_XYZ_1.get(0, 1);                  
            double z1 = result_XYZ_1.get(0, 2);                  
    
            double x1dash = Math.round(x1 - 1.0);
            double y1dash = Math.round(y1);
            double z1dash = Math.round(z1);

            result_XYZ_1.set(0, 0, x1dash);
            result_XYZ_1.set(0, 1, y1dash);
            result_XYZ_1.set(0, 2, z1dash);

            print("res x aft=" + result_XYZ_1.get(0, 0));    
            print("res y aft=" + result_XYZ_1.get(0, 1));    
            print("res z aft=" + result_XYZ_1.get(0, 2));    
                  
        } else if(faceName.equals(Side.TOP_SIDE) && orientation.equals("anti-clockwise")) {
             // Minus Z by 1 
            println("top aclk ");

       //     print("res x =" + result_XYZ_1.get(0, 0));    
       //     print("res y =" + result_XYZ_1.get(0, 1));    
       //     print("res z =" + result_XYZ_1.get(0, 2));    

            double x1 = result_XYZ_1.get(0, 0);                  
            double y1 = result_XYZ_1.get(0, 1);                  
            double z1 = result_XYZ_1.get(0, 2);                  

            double x1dash = Math.round(x1);
            double y1dash = Math.round(y1);
            double z1dash = Math.round(z1 - 1.0);    

             result_XYZ_1.set(0, 0, x1dash);
             result_XYZ_1.set(0, 1, y1dash);
             result_XYZ_1.set(0, 2, z1dash);

            print("res x aft=" + result_XYZ_1.get(0, 0));    
            print("res y aft=" + result_XYZ_1.get(0, 1));    
            print("res z aft=" + result_XYZ_1.get(0, 2));    

        }  
        else if(faceName.equals(Side.DOWN_SIDE) && orientation.equals("clockwise")) {
             // Minus Z by 1 
            println("down clk ");

       //     print("res x =" + result_XYZ_1.get(0, 0));    
       //     print("res y =" + result_XYZ_1.get(0, 1));    
       //     print("res z =" + result_XYZ_1.get(0, 2));    

            double x1 = result_XYZ_1.get(0, 0);                  
            double y1 = result_XYZ_1.get(0, 1);                  
            double z1 = result_XYZ_1.get(0, 2);                  


            double x1dash = Math.round(x1 - 1.0);
            double y1dash = Math.round(y1);
            double z1dash = Math.round(z1);        

            result_XYZ_1.set(0, 0, x1dash);
            result_XYZ_1.set(0, 1, y1dash);
            result_XYZ_1.set(0, 2, z1dash);

            print("res x aft=" + result_XYZ_1.get(0, 0));    
            print("res y aft=" + result_XYZ_1.get(0, 1));    
            print("res z aft=" + result_XYZ_1.get(0, 2));    
                  
        }  
        else if(faceName.equals(Side.DOWN_SIDE) && orientation.equals("anti-clockwise")) {
             // Minus Z by 1 
            println("down aclk ");

        //    print("res x =" + result_XYZ_1.get(0, 0));    
        //    print("res y =" + result_XYZ_1.get(0, 1));    
        //    print("res z =" + result_XYZ_1.get(0, 2));    

            double x1 = result_XYZ_1.get(0, 0);                  
            double y1 = result_XYZ_1.get(0, 1);                  
            double z1 = result_XYZ_1.get(0, 2);                  

    
            double x1dash = Math.round(x1);
            double y1dash = Math.round(y1);
            double z1dash = Math.round(z1 - 1.0);        

            result_XYZ_1.set(0, 0, x1dash);
            result_XYZ_1.set(0, 1, y1dash);
            result_XYZ_1.set(0, 2, z1dash);
 
            print("res x aft=" + result_XYZ_1.get(0, 0));    
            print("res y aft=" + result_XYZ_1.get(0, 1));    
            print("res z aft=" + result_XYZ_1.get(0, 2));    
       
        }  

}