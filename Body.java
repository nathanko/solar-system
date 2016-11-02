/*
The Solar System CPT
Programmed by Nathan Ko, June 1, 2016
This program visually simulates the orbits of the planets and some of its moons around the sun.
This class is the Body class. The driver class is named SolarSystem.
*/
import java.awt.*;
import hsa.Console;
public class Body
{
    Console c;
    public int xPos, yPos, rad, xOrb, yOrb, radOrb, numMoons;
    double angle, day, xOrbScale, yOrbScale; //angle is the angle of rotation per 24 hours (rad)
    Color clr;
    Body[] moons;

    public Body (Console c, double[] data, int xOrb, int yOrb, double startDay)
    {
	this.c = c; //the display console
	rad = (int) data [0]; //radius of the planet
	this.xOrb = xOrb; //x-centre of orbit
	this.yOrb = yOrb; //y-centre of orbit
	radOrb = (int) data [1]; //radius of orbit

	numMoons = (int) data [6]; //number of moons the planet has
	if (numMoons > 0)
	{
	    moons = new Body [numMoons];
	}

	//determine the angle of rotation for each unit of time based on the number of days in a month
	angle = 2 * Math.PI / data [2];

	//initial position of the body
	day = startDay;

	xPos = (int) (xOrb + radOrb * Math.cos (angle * day)); //calculate x-coordinate of new position along orbit
	yPos = (int) (yOrb - radOrb * Math.sin (angle * day)); //calculate y-coordinate of new position along orbit

	clr = new Color ((int) data [3], (int) data [4], (int) data [5]);

	xOrbScale = data [7];
	yOrbScale = data [8];
    }


    public void orbit (double increment)  //orbits body by increment hours
    {
	day += increment; //increments the day

	fillCircle (Color.black); //erase current image

	xPos = (int) (xOrb + radOrb * Math.cos (angle * day) * xOrbScale); //calculate x-coordinate of new position along orbit
	yPos = (int) (yOrb - radOrb * Math.sin (angle * day) * yOrbScale); //calculate y-coordinate of new position along orbit
	fillCircle (clr); //draws planet in new location

	//update the xOrb and yOrb (orbital centre) of each moon to this planet's new location
	//and draw each moon's orbit path
	//(planets' orbit paths are drawn in the driver method)
	for (int i = 0 ; i < numMoons ; i++)
	{
	    moons [i].drawPath (Color.black);
	    moons [i].xOrb = this.xPos;
	    moons [i].yOrb = this.yPos;
	    moons [i].drawPath (Color.white);
	}
    }


    public void fillCircle (Color clr)
    {
	int x = xPos - rad; //calculate x-coordinate of upper left corner
	int y = yPos - rad; //calculate y-coordinate of upper left corner
	int diameter = rad * 2; //calculate diameter

	c.setColor (clr);
	c.fillOval (x, y, diameter, diameter);
    }


    public void drawPath (Color clr)  //draw the circular orbit path of moons of planets
    {
	int x = xOrb - radOrb; //calculate x-position of upper left corner
	int y = yOrb - radOrb; //calculate y-position of upper left corner
	int diameter = radOrb * 2; //calculate diameter

	c.setColor (clr);
	c.drawOval (x, y, diameter, diameter);
    }
} // Body class


