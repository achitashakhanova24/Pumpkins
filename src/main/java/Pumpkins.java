import processing.core.PApplet;

public class Pumpkins extends PApplet {
    public static int width = 800;
    public static int height = 600;

    // 1. DECLARING THE PUMPKIN VARIABLE
    Pumpkin myPumpkin;


    @Override
    public void setup() {
        // 2. INITIALIZING THE PUMPKIN VARIABLE
        // Initialize the pumpkin variable.

        myPumpkin = new Pumpkin(450, color(226, 98, 56), this);


    }

    @Override
    public void draw() {
        background(0);
        drawFloor();

        // 3. DRAW THE PUMPKIN
        // Call the pumpkin variable's draw() method

        myPumpkin.draw();

        // 4. DRAW MORE PUMPKINS
      Pumpkin anotherPumpkin = new Pumpkin (200, color(225, 200, 100), this);
      anotherPumpkin.draw();

        myPumpkin.bounce();
        myPumpkin.moveRight(2);
        anotherPumpkin.bounce();
        anotherPumpkin.moveLeft(3);
    }


    void drawFloor() {
        int floorHeight = 30;

        pushMatrix();
        fill(10, 10, 30);
        rect(0, height - floorHeight, width, floorHeight);
        popMatrix();
    }

    @Override
    public void settings() {
        size(width, height);
    }

    public static void main(String[] args) {
        PApplet.main(Pumpkins.class.getName());
    }
}

class Pumpkin {
    private int x;
    private int y;
    private int xSpeed = 0;
    boolean bounce = false;
    private int bounceHeight = 30;
    private int bounceSpeed = 0;
    private int pumpkinColor;
    private int glowingEyesColor;
    private int greenStemColor;
    private final int pumpkinSizePixels = 150;
    private final int gravityDownwardPixels = 1;
    private final PApplet p;

    public Pumpkin(int x, int pumpkinColor, PApplet p) {
        this.x = x;
        this.y = Pumpkins.height - 300;
        this.pumpkinColor = pumpkinColor;
        this.p = p;
        this.initialize();
    }

    private void initialize() {
        int randomRed = (int) p.random(0, 255);
        int randomGreen = (int) p.random(0, 255);
        int randomBlue = (int) p.random(0, 255);
        this.glowingEyesColor = p.color(randomRed, randomGreen, randomBlue);
        this.greenStemColor = p.color(0x2E, 0xA2, 0x2C);
    }

    public void setPumpkinColor(int newColor) {
        this.pumpkinColor = newColor;
    }

    // ---------------------------------------------------------
    // Call this method from the setup() method,
    // NOT the draw() method
    // ---------------------------------------------------------
    void setBounceHeight(int newHeightInPixels) {
        this.bounceHeight = newHeightInPixels;
    }

    void bounce() {
        this.bounce = true;
    }

    void stop() {
        this.bounce = false;
        this.xSpeed = 0;
    }

    void moveRight(int speed) {
        this.xSpeed = speed;
    }

    void moveLeft(int speed) {
        this.xSpeed = -speed;
    }

    public void draw() {
        p.pushMatrix();

        if (bounceSpeed < bounceHeight) {
            bounceSpeed += gravityDownwardPixels;
        }

        y += bounceSpeed;

        if (this.y > Pumpkins.height - 100) {
            this.y = Pumpkins.height - 100;

            if (bounce) {
                bounceSpeed = -bounceSpeed;
            }
        }

        this.x += xSpeed;

        if (this.x > Pumpkins.width + this.pumpkinSizePixels) {
            this.x = -this.pumpkinSizePixels;
        }
        if (this.x < -this.pumpkinSizePixels) {
            this.x = Pumpkins.width;
        }

        p.ellipseMode(PApplet.CENTER);

        // Black outline
        p.stroke(0);
        p.strokeWeight(2);

        // Draw top stem
        p.fill(greenStemColor);
        p.rect(x - 10, y - (pumpkinSizePixels / 2f) - 20, 15, 20);

        // Draw body
        p.fill(pumpkinColor);
        p.ellipse(x, y, pumpkinSizePixels, pumpkinSizePixels);

        // Set glowing eyes
        p.fill(glowingEyesColor, p.random(200) + 50);

        // Draw eyes
        p.triangle(x - 30, y - 20, x - 20, y, x - 40, y);
        p.triangle(x + 30, y - 20, x + 20, y, x + 40, y);
        p.triangle(x, y, x + 10, y + 20, x - 10, y + 20);

        // Draw shadow
        p.ellipse(x, Pumpkins.height - 15, (150f * y) / Pumpkins.height, (10f * y) / Pumpkins.height);

        // Draw mouth
        p.arc(x, y + 30, 80, 60, 0, PApplet.PI, 0);
        p.line(x - 40, y + 30, x + 40, y + 30);

        // Draw teeth
        p.fill(this.pumpkinColor);
        p.rect(x + 15, y + 30, 10, 10);
        p.rect(x - 25, y + 30, 10, 10);
        p.rect(x - 5,  y + 50, 10, 10);

        // Clear the top outline of the teeth
        p.strokeWeight(3);
        p.stroke(this.pumpkinColor);
        p.fill(this.pumpkinColor);
        p.line(x + 17, y + 30, x + 22, y + 30);
        p.line(x - 23, y + 30, x - 18, y + 30);
        p.line(x - 2, y + 60, x + 2, y + 60);

        p.popMatrix();
    }
}