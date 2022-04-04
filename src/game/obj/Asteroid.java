package game.obj;


import game.AsteroidsGame;
import game.Obj;
import java.awt.Polygon;
//import java.awt.Rectangle;

/**
 *
 * @author Daniel Howes
 */
public class Asteroid extends Obj {
    
    public double vx, vy, va;
    public int size;
    public int halfSize;
    
    // size = 1 small, 2 medium, 3 large
    public Asteroid(AsteroidsGame game, double x, double y, int size) {
        super(game); //calls parent class constructor
        this.x = x;
        this.y = y;
        this.size = size;
        this.halfSize = size * 10;

        angle = (2 * Math.PI) * Math.random();
        double v = 0.5 + 1 * Math.random(); //velocity factor

        vx = (4 - size) * Math.cos(angle) * v; //horizontal speed
        vy = (4 - size) * Math.sin(angle) * v; //vertical speed
        va = 0.01 + 0.05 * Math.random(); //spin speed

        //setShape();
        generateRandomShape();
    }
    
    // private void setShape() {
    //     //Ellipse2D asteroidShape = new Ellipse2D.Double(-halfSize, -halfSize, 2 * halfSize, 2 * halfSize);
    //     Rectangle.Double asteroidShape = new Rectangle.Double(-halfSize, -halfSize, 2 * halfSize, 2 * halfSize);
    //     shape = asteroidShape;
    // }

    private void generateRandomShape() {
        Polygon randomAsteroidShape = new Polygon();
        int f = 5 + (int) (5 * Math.random());//random number of sides
        double da = (2 * Math.PI) / f;
        double a = (2 * Math.PI) * Math.random();

        for (int i = 0; i < f; i++) {
            double ad = (1.5 * halfSize) + (halfSize * Math.random());
            double ax = ad * Math.cos(a);
            double ay = ad * Math.sin(a);
            randomAsteroidShape.addPoint((int) ax, (int) ay);
            a += da;
        }
        shape = randomAsteroidShape;
    }
    
    @Override
    public void update() {
        angle += va;
        
        x += vx;
        y += vy;
        
        x = x < -halfSize ? game.getWidth() : x;
        x = x > game.getWidth() + halfSize ? -halfSize : x;
        y = y < -halfSize ? game.getHeight() : y;
        y = y > game.getHeight() + halfSize ? -halfSize : y;
    }

    public void hit() {
        //if the asteroid is the largest size, create two medium size asteroids
        if (size == 3) {
            game.add(new Asteroid(game, x, y, size - 1));
            game.add(new Asteroid(game, x, y, size - 1));
        //if the asteroid is medium size, create two small size asteroids
        } else if (size == 2) {
            game.add(new Asteroid(game, x, y, size - 1));
            game.add(new Asteroid(game, x, y, size - 1));
        }
        //destroy current asteroid
        destroyed = true;
    }
    
}
