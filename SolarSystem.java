/*
The Solar System CPT
Programmed by Nathan Ko, June 1, 2016
This program visually simulates the orbits of the planets and some of its moons around the sun.
This class is the driver class.
*/
import java.awt.*;
import hsa.Console;
public class SolarSystem
{
    static Console c;
    public static void main (String[] args)
    {

	//construct display console
	int width = 160;
	int height = 39;
	c = new Console (height, width);

	/*
	*****RAW DATA OF CELESTIAL BODIES (DOUBLE VALUES)*****
	[0] diameter of the celestial body in km (diameter can be taken as the radius since they are proportional)
	[1] radius of orbit in 1000km
	[2] period of orbit in days
	[3] RGB red component (0-255)
	[4] RGB green component (0-255)
	[5] RGB blue component (0-255)
	[6] number of moons the a planet has
	[7] vertical stretch/compression of orbit
	[8] horizontal stretch/compression of orbit
	*/
	double sunData[] = {1391400, 0, 0, 255, 255, 200, 0, 1, 1};
	double mercuryData[] = {4879, 57900, 88, 124, 90, 90, 0, 1.05, 0.95};
	double venusData[] = {12104, 108200, 225, 195, 203, 123, 0, 1, 1};
	double earthData[] = {12756, 149600, 365, 148, 157, 242, 1, 1, 1};
	double lunaData[] = {3475, 384, 27, 130, 130, 130, 0, 1, 1};
	double marsData[] = {6792, 227900, 687, 200, 20, 0, 0, 1.03, 0.97};
	double jupiterData[] = {142984, 778600, 4331, 255, 80, 0, 4, 1, 1};
	double callistoData[] = {4800, 1883, 46.689, 199, 199, 199, 0, 1, 1};
	double europaData[] = {3126, 670, 3.551, 199, 199, 199, 0, 1, 1};
	double ganymedeData[] = {5276, 1070, 7.155, 199, 199, 199, 0, 1, 1};
	double ioData[] = {3629, 422, 1.769, 199, 199, 199, 0, 1, 1};
	double saturnData[] = {120536, 1433500, 10747, 255, 255, 100, 5, 1, 1};
	double dioneData[] = {1120, 377, 2.737, 199, 199, 199, 0, 1, 1};
	double iapetusData[] = {1436, 3561, 79.3215, 199, 199, 199, 0, 1, 1};
	double rheaData[] = {1528, 527, 4.518, 199, 199, 199, 0, 1, 1};
	double tethysData[] = {1060, 295, 1.888, 199, 199, 199, 0, 1, 1};
	double titanData[] = {5150, 1221, 15.945, 199, 199, 199, 0, 1, 1};
	double uranusData[] = {51118, 2872500, 30589, 150, 150, 250, 4, 1, 1};
	double arielData[] = {1160, 161, 2.520, 199, 199, 199, 0, 1, 1};
	double oberonData[] = {1526, 583, 13.463, 199, 199, 199, 0, 1, 1};
	double titaniaData[] = {1578, 436, 8.706, 199, 199, 199, 0, 1, 1};
	double umbrielData[] = {1190, 266, 4.144, 199, 199, 199, 0, 1, 1};
	double neptuneData[] = {49528, 4495100, 59800, 150, 180, 255, 1, 1, 1};
	double tritonData[] = {2705, 355, 5.877, 199, 199, 199, 0, 1, 1};
	double plutoData[] = {2370, 5906400, 90560, 255, 200, 100, 0, 1.2, 0.8};


	//apply an exponential filter to the data to make it reasonably fit to console
	filter (sunData);
	filter (mercuryData);
	filter (venusData);
	filter (earthData);
	filter (lunaData);
	filter (marsData);
	filter (jupiterData);
	filter (callistoData);
	filter (europaData);
	filter (ganymedeData);
	filter (ioData);
	filter (saturnData);
	filter (dioneData);
	filter (iapetusData);
	filter (rheaData);
	filter (tethysData);
	filter (titanData);
	filter (uranusData);
	filter (arielData);
	filter (oberonData);
	filter (titaniaData);
	filter (umbrielData);
	filter (neptuneData);
	filter (tritonData);
	filter (plutoData);

	//artificial alterations to make some planets and moons fit better
	venusData [1] -= 5;
	saturnData [1] += 11;
	plutoData [0] += 1;

	callistoData [1] += 16;
	ganymedeData [1] += 13;
	europaData [1] += 10;
	ioData [1] += 7;

	iapetusData [1] += 13;
	titanData [1] += 12;
	rheaData [1] += 11;
	dioneData [1] += 8;
	tethysData [1] += 6;

	oberonData [1] += 11;
	titaniaData [1] += 8;
	umbrielData [1] += 6;
	arielData [1] += 4;

	tritonData [1] += 5;

	//initial settings
	final int sunX = c.maxx () / 2; //x-position of the sun (stationary)
	final int sunY = c.maxy () / 2; //y-position of the sun (stationary)
	double day = 0; //number of elapsed days displayed
	double daysSkipped = 100000; //used to skip forward in the animation and initialize planets in different positions in orbit
	double increment = 1; //each pass of the loop moves the celestial bodies by this number of days


	//construct Body objects of each celestial body
	//parameters: Console c, double[] data, int xOrb, int yOrb, double day

	Body mercury = new Body (c, mercuryData, sunX, sunY, daysSkipped);
	Body venus = new Body (c, venusData, sunX, sunY, daysSkipped);

	Body earth = new Body (c, earthData, sunX, sunY, daysSkipped);
	earth.moons [0] = new Body (c, lunaData, earth.xPos, earth.yPos, daysSkipped);

	Body mars = new Body (c, marsData, sunX, sunY, daysSkipped);

	Body jupiter = new Body (c, jupiterData, sunX, sunY, daysSkipped);
	jupiter.moons [0] = new Body (c, callistoData, jupiter.xPos, jupiter.yPos, daysSkipped);
	jupiter.moons [1] = new Body (c, europaData, jupiter.xPos, jupiter.yPos, daysSkipped);
	jupiter.moons [2] = new Body (c, ganymedeData, jupiter.xPos, jupiter.yPos, daysSkipped);
	jupiter.moons [3] = new Body (c, ioData, jupiter.xPos, jupiter.yPos, 0);

	Body saturn = new Body (c, saturnData, sunX, sunY, daysSkipped);
	saturn.moons [0] = new Body (c, dioneData, saturn.xPos, saturn.yPos, daysSkipped);
	saturn.moons [1] = new Body (c, iapetusData, saturn.xPos, saturn.yPos, daysSkipped);
	saturn.moons [2] = new Body (c, rheaData, saturn.xPos, saturn.yPos, daysSkipped);
	saturn.moons [3] = new Body (c, tethysData, saturn.xPos, saturn.yPos, daysSkipped);
	saturn.moons [4] = new Body (c, titanData, saturn.xPos, saturn.yPos, daysSkipped);

	Body uranus = new Body (c, uranusData, sunX, sunY, daysSkipped);
	uranus.moons [0] = new Body (c, arielData, uranus.xPos, uranus.yPos, daysSkipped);
	uranus.moons [1] = new Body (c, oberonData, uranus.xPos, uranus.yPos, daysSkipped);
	uranus.moons [2] = new Body (c, titaniaData, uranus.xPos, uranus.yPos, daysSkipped);
	uranus.moons [3] = new Body (c, umbrielData, uranus.xPos, uranus.yPos, daysSkipped);

	Body neptune = new Body (c, neptuneData, sunX, sunY, daysSkipped);
	neptune.moons [0] = new Body (c, tritonData, neptune.xPos, neptune.yPos, daysSkipped);

	Body pluto = new Body (c, plutoData, sunX, sunY, daysSkipped);

	//construct asteroid Body objects of the asteroid belt
	int numAsteroids = 50;
	Body asteroidBelt[] = new Body [numAsteroids];
	for (int i = 0 ; i < numAsteroids ; i++)
	{
	    asteroidBelt [i] = makeAsteroid (sunX, sunY);
	}

	//create random x,y coordinates for background stars
	int numStars = 500;
	int[] [] stars = new int [numStars] [2];
	for (int i = 0 ; i < numStars ; i++)
	{
	    stars [i] = makeStar ();
	}

	//some background and background info
	c.setColor (Color.black);
	c.fillRect (0, 0, c.maxx (), c.maxy ());
	c.setColor (Color.white);
	Font title = new Font ("Arial", Font.BOLD, 14);
	c.setFont (title);
	c.drawString ("The Solar System", c.maxx ()/2-55, 20);
	c.drawString ("By Nathan Ko", c.maxx ()/2-40, 35);
	c.drawString ("ELAPSED", 5, c.maxy () - 20);

	//draw legend
	c.drawString ("Legend", 60, 30);
	fillCircle (50, 50, mercury.rad * 2, mercury.clr);
	c.drawString ("Mercury", 100, 50);
	fillCircle (50, 100, venus.rad * 2, venus.clr);
	c.drawString ("Venus", 100, 100);
	fillCircle (50, 150, earth.rad * 2, earth.clr);
	c.drawString ("Earth", 100, 150);
	fillCircle (50, 200, mars.rad * 2, mars.clr);
	c.drawString ("Mars", 100, 200);
	fillCircle (50, 250, jupiter.rad * 2, jupiter.clr);
	c.drawString ("Jupiter", 100, 250);
	fillCircle (50, 300, saturn.rad * 2, saturn.clr);
	c.drawString ("Saturn", 100, 300);
	fillCircle (50, 350, uranus.rad * 2, uranus.clr);
	c.drawString ("Uranus", 100, 350);
	fillCircle (50, 400, neptune.rad * 2, neptune.clr);
	c.drawString ("Neptune", 100, 400);
	fillCircle (50, 450, pluto.rad * 2, pluto.clr);
	c.drawString ("Pluto", 100, 450);
	fillCircle (50, 500, 2, Color.gray);
	c.drawString ("Asteroid/Moon", 100, 500);


	//draw the stationary sun
	fillCircle (sunX, sunY, sunData [0], Color.yellow);

	//main animation loop
	while (true)
	{
	    writeDate (day); //displays elapsed number of days, starts from 0

	    //draw orbital paths of each planet
	    drawPath (sunX, sunY, mercury, Color.white);
	    drawPath (sunX, sunY, venus, Color.white);
	    drawPath (sunX, sunY, earth, Color.white);
	    drawPath (sunX, sunY, mars, Color.white);
	    drawPath (sunX, sunY, jupiter, Color.white);
	    drawPath (sunX, sunY, saturn, Color.white);
	    drawPath (sunX, sunY, uranus, Color.white);
	    drawPath (sunX, sunY, neptune, Color.white);
	    drawPath (sunX, sunY, pluto, Color.white);

	    //must be redrawn each pass of the animation loop to avoid planets "erasing" stars
	    drawStars (stars);

	    //orbit each celestial body by an increment number of days
	    mercury.orbit (increment);
	    venus.orbit (increment);
	    earth.orbit (increment);
	    earth.moons [0].orbit (increment);
	    mars.orbit (increment);
	    jupiter.orbit (increment);
	    jupiter.moons [0].orbit (increment);
	    jupiter.moons [1].orbit (increment);
	    jupiter.moons [2].orbit (increment);
	    jupiter.moons [3].orbit (increment);
	    saturn.orbit (increment);
	    saturn.moons [0].orbit (increment);
	    saturn.moons [1].orbit (increment);
	    saturn.moons [2].orbit (increment);
	    saturn.moons [3].orbit (increment);
	    saturn.moons [4].orbit (increment);
	    uranus.orbit (increment);
	    uranus.moons [0].orbit (increment);
	    uranus.moons [1].orbit (increment);
	    uranus.moons [2].orbit (increment);
	    neptune.orbit (increment);
	    neptune.moons [0].orbit (increment);
	    pluto.orbit (increment);

	    //orbit the asteroids around the asteroid belt
	    for (int i = 0 ; i < numAsteroids ; i++)
	    {
		asteroidBelt [i].orbit (increment);
	    }


	    //goToSleep (0); //time delay to control animation speed
	    day += increment; //counts the number of elapsed days
	}

    } // main method




    //apply exponential functions to the raw data to make it visually fit on console
    public static void filter (double[] data)
    {
	data [0] = Math.pow (data [0], 0.50) / 31;  //compress diameter of body
	data [1] = Math.pow (data [1], 0.47) / 4 + 3;  //compress orbital radius
    }


    //displays the elapsed number of days/months/years on the bottom-left corner of console
    public static void writeDate (double dayDouble)
    {
	int day = (int) dayDouble;
	int year = day / 365;
	day -= year * 365;
	int month = day / 30;
	day -= month * 30;
	String output = "Years: " + year + " Months: " + month + " Days: " + day;

	c.setColor (Color.black);
	c.fillRect (0, c.maxy () - 20, 250, 20);
	c.setColor (Color.white);
	c.drawString (output, 5, c.maxy () - 5);
    }


    //draws a filled circle - planet, moon, asteroid, star, etc.
    public static void fillCircle (int xCentre, int yCentre, double radius, Color clr)
    {
	int x = (int) (xCentre - radius); //calculate x-coordinate of upper left corner
	int y = (int) (yCentre - radius); //calculate y-coordinate of upper left corner
	int diameter = (int) (radius * 2); //calculate diameter

	c.setColor (clr);
	c.fillOval (x, y, diameter, diameter);
    } //end of drawPath method


    //draw the orbit path of planets (stationary), circular or elliptical
    //moons have dynamic orbit paths so theirs are drawn in the drawPath() method in the Body class
    public static void drawPath (int xCentre, int yCentre, Body object, Color clr)
    {
	int x = (int) (xCentre - object.radOrb * object.xOrbScale); //calculate x-position of upper left corner
	int y = (int) (yCentre - object.radOrb * object.yOrbScale); //calculate y-position of upper left corner
	int xLength = (int) (object.radOrb * 2 * object.xOrbScale); //calculate length of major axis
	int yLength = (int) (object.radOrb * 2 * object.yOrbScale); //calculate length of minor axis

	c.setColor (clr);
	c.drawOval (x, y, xLength, yLength);
    } //end of drawPath method


    //constructs an asteroid with somewhat random parameters and returns the Body object
    public static Body makeAsteroid (int sunX, int sunY)
    {
	double radius = Math.random () * 4000 + 100;
	double radOrb = Math.random () * 150000 + 300000;
	double period = Math.random () * 50 + 700; //a replacement to Kepler's 3rd Law
	double grayScale = Math.random () * 50 + 200;

	double data[] = {radius, radOrb, period, grayScale, grayScale, grayScale, 0, 1, 1};
	filter (data);

	return new Body (c, data, sunX, sunY, 10000);

    }


    //draw random star
    public static int[] makeStar ()
    {
	int x, y;
	x = (int) (Math.random () * (c.maxx () + 1));
	y = (int) (Math.random () * (c.maxy () + 1));
	int[] star = {x, y};
	return star;
    }


    public static void drawStars (int[] [] stars)
    {
	for (int i = 0 ; i < stars.length ; i++)
	{
	    fillCircle (stars [i] [0], stars [i] [1], 0.5, Color.white);
	}
    }


    //delay
    public static void goToSleep (int delay)
    {
	try
	{
	    Thread.sleep (delay);
	}
	catch (InterruptedException e)
	{
	}
    }
} // SolarSystem class


