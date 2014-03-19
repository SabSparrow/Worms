package worms.model;
import be.kuleuven.cs.som.annotate.*;
import be.kuleuven.cs.som.taglet.*;
import worms.BadOrientationException;

/**
 * A class of Worms, each involving a horizontal and vertical position, an orientation, a radius, a name, a mass, 
 * a number of action points, a maximal radius and a maximal number of action points. Each worm is able to move, turn or jump,
 * depending on their parameters. Worms also share a density, which defines a relation between their mass and radius, and a 
 * standard acceleration, which determines how far they can jump.
 * @invar 	minRadius >0; radius >= minRadius; 0<=orientation<2*Pi; 
 * 			actionPoints <= getMaximalNumberOfActionPoints();
 * 
 * 
 * @version 1.7
 * @author Yasmine Baestaens and Sander Leyssens (Bachelor of Mathematics)
 * 
 */

public class Worm {
	
	/**
	 * Initialize a new worm with the given name, the given horizontal and vertical position,
	 * the given orientation, the given radius and the given number of action points
	 * @param 	name
	 * 			The name for this worm.
	 * @param 	horizontalPosition
	 * 			The initial horizontal position for this worm.
	 * @param 	verticalPosition
	 * 			The initial vertical position for this worm.
	 * @param 	orientation
	 * 			The initial orientation for this worm.
	 * @param 	radius
	 * 			The initial radius for this worm.
	 * @param 	actionPoints
	 * 			The initial number of action points for this worm.
	 * @post	The new name of this worm is equal to the given name.
	 * 			| new.getName().equals(name)
	 * @post	The new horizontal position of this worm is equal to the given horizontal position.
	 * 			| new.getHorizontalPosition() = horizontalPosition
	 * @post	The new vertical position of this worm is equal to the given vertical position.
	 * 			| new.getVerticalPosition() = verticalPosition
	 * @post	The new orientation of this worm is equal to the given orientation.
	 * 			| new.getOrientation() = orientation
	 * @post	The new radius of this worm is equal to the given radius.
	 * 			| new.getRadius() = radius
	 * @post	If the given number of action points is a valid number of action points for this worm,
	 * 			the new number of action points of this worm is equal to the given number. Otherwise,
	 * 			it is equal to zero.
	 * 			| new.getCurrentNumberOfActionPoints() = actionPoints
	 * @throws	IllegalArgumentException
	 * 			The name or radius is not valid.
	 * 			| !isValidName(name) || !isValidRadius(radius)
	 */
	public Worm(String name, double horizontalPosition, double verticalPosition, 
			double orientation, double radius, long actionPoints) throws IllegalArgumentException {
		setName(name);
		setHorizontalPosition(horizontalPosition);
		setVerticalPosition(verticalPosition);
		setOrientation(orientation);
		setRadius(radius);
		setCurrentNumberOfActionPoints(actionPoints);
	}
	
	/**
	 * 
	 *@param 	name
	 * 			The name for this worm.
	 * @param 	horizontalPosition
	 * 			The initial horizontal position for this worm.
	 * @param 	verticalPosition
	 * 			The initial vertical position for this worm.
	 * @param 	orientation
	 * 			The initial orientation for this worm.
	 * @param 	radius
	 * 			The initial radius for this worm.
	 * @effect	This new worm is initialized with the given name as its name, the given horizontal 
	 * 			position as its horizontal position, the given vertical position as its vertical
	 * 			position, the given orientation as its orientation and the given radius as its radius
	 * 			and the highest possible number of action points.
	 * 			| this(name,horizontalPosition,verticalPosition,orientation,radius,0)
	 * 			| setCurrentNumberOfActionPoints(getMaximalNumberOfActionPoints())
	 * 			
	 * @throws IllegalArgumentException
	 */
	public Worm(String name, double horizontalPosition, double verticalPosition, 
			double orientation, double radius) throws IllegalArgumentException {
		this(name, horizontalPosition, verticalPosition, orientation, radius, 0);
		setCurrentNumberOfActionPoints(getMaximalNumberOfActionPoints());
	}
	
	/**
	 * Return the name of this worm.
	 */
	@Basic @Raw
	public String getName(){
		return new String(name);
	}
	
	/**
	 * Set the name of this worm to the given name.
	 * @param 	name
	 * 			The new name for this worm.
	 * @throws 	IllegalArgumentException
	 * 			The given name is not a valid name.
	 * 			|!isValidName(name)
	 */
	@Raw
	public void setName(String name) throws IllegalArgumentException{
		if(!isValidName(name))throw new IllegalArgumentException("Not a valid name!");
		this.name=new String(name);
	}
	
	/**
	 * Return whether the given name is a valid name for any worm.
	 * @return 	false if the given string is not effective.
	 * 			|if (name == null)
	 * 			false if the given string is not long enough.
	 * 			|if (name.length()<2)
	 * 			false if the given string does not begin with an uppercase letter
	 * 			or if it contains other characters than spaces, letters and quotes.
	 * 			|if (!name.matches("[A-Z][A-Za-z\b\'\"]*")
	 * 			true otherwise
	 */
	private static boolean isValidName(String name){
		return name!=null&&name.matches("[A-Z][A-Za-z \'\"]+");
	}
	
	/**
	 * The name of this worm.
	 */
	private String name;
	
	/**
	 * Constant denoting the density of all worms.
	 */
	@Model
	private static final double DENSITY = 1062;
	
	
	@Model
	private static final double STANDARD_ACCELERATION = 9.80665;

	/**
	 * Return the horizontal position of this worm.
	 */
	@Basic @Raw
	public double getHorizontalPosition() {
		return horizontalPosition;
	}
	
	/**
	 * Set the horizontal position of the worm to the given position.
	 * @param 	horizontalPosition
	 * 			The new horizontal position for this worm.
	 * @post	The new horizontal position of this worm is equal to the given horizontal position.
	 * 			| new.getHorizontalPosition() == horizontalPosition
	 */
	@Raw @Model
	private void setHorizontalPosition(double horizontalPosition) {
		this.horizontalPosition = horizontalPosition;
	}
	
	/**
	 * Variable registering the horizontal position of this worm.
	 */
	private double horizontalPosition;

	
	/**
	 * Return the vertical position of this worm.
	 */
	@Basic @Raw
	public double getVerticalPosition() {
		return verticalPosition;
	}
	/**
	 * Set the vertical position of the worm to the given position.
	 * @param 	verticalPosition
	 * 			The new vertical position for this worm.
	 * @post	The new vertical position of this worm is equal to the given vertical position.
	 * 			| new.getVerticalPosition() == verticalPosition
	 */
	@Raw @Model
	private void setVerticalPosition(double verticalPosition) {
		this.verticalPosition = verticalPosition;
	}
	
	/**
	 * Variable registering the vertical position of this worm.
	 */
	private double verticalPosition;
	
	/**
	 * Return the orientation of this worm.
	 */
	@Basic @Raw
	public double getOrientation(){
		return orientation;
	}
	
	/**
	 * Set the orientation of this worm to the given orientation.
	 * @param 	orientation
	 * 			The new orientation for this worm.
	 * @Pre		The given orientation must be a valid orientation for any worm.
	 * 			|isValidOrientation(orientation)
	 * @post	The new orientation of this worm is equal to the given orientation.
	 * 			|new.getOrientation()==orientation
	 */
	@Raw
	private void setOrientation(double orientation){
		assert isValidOrientation(orientation): "Precondition: The orientation must be valid";
		this.orientation=orientation;
	}

	/**
	 * Return whether the given orientation is a valid orientation for any worm.
	 * @return	True if and only if the given orientation lies between 0 (included) and 2π (excluded).
	 * 			|result == (0<=orientation)&&(orientation<2*Math.PI);
	 */
	public static boolean isValidOrientation(double orientation){
		return (0<=orientation)&&(orientation<2*Math.PI);
	}
	
	/**
	 * Variable denoting the orientation of this worm.
	 */
	private double orientation;
	
	@Basic @Raw
	public double getMinRadius(){
		return minRadius;
	}
	
	/**
	 * The minimal radius this worm can have.
	 */
	private double minRadius=0.25;
	
	/**
	 * Return the radius of this worm.
	 */
	@Basic @Raw
	public double getRadius() {
		return radius;
	}

	/**
	 * Set the radius of the worm to the given radius.
	 * @param	radius
	 * 			The new radius for this worm.
	 * @post	The new radius of this worm is equal to the given radius.
	 * 			|new.getRadius()==radius
	 * @throws	IllegalArgumentException
	 * 			The given radius is not a valid radius
	 * 			|!isValidRadius(radius)
	 */
	@Raw
	public void setRadius(double radius) throws IllegalArgumentException{
		if(!isValidRadius(radius)) throw new IllegalArgumentException("Not a valid radius!");
		this.radius=radius;
	}
	
	/**
	 * 
	 * Return whether the given radius is a valid radius for this worm.  
	 * @return 	True if and only if the given radius is greater or equal to the minimal radius for this worm.
	 * 			|result == radius >=minRadius
	 */
	@Raw
	private boolean isValidRadius(double radius){
		return this.getMinRadius()<=radius;
	}
	
	/**
	 * Variable registering the radius of this worm.
	 */
	private double radius;
	
	
	/**
	 * Returns the mass of this worm.
	 * @return The mass of this worm.
	 * 			| DENSITY*(4.0/3.0)*Math.PI*Math.pow(getRadius(), 3)
	 */

	@Raw
	public double getMass() {
		return DENSITY*(4.0/3.0)*Math.PI*Math.pow(getRadius(), 3);
	}
	
	/**
	 * Return the maximal number of action points this worm can currently have.
	 */
	@Raw
	public long getMaximalNumberOfActionPoints(){
		return Math.round(this.getMass());
	}
	
	/**
	 * Return the current number of action points for this worm.
	 */
	@Basic @Raw
	public long getCurrentNumberOfActionPoints(){
		return currentNumberOfActionPoints;
	}
	
	/**
	 * Set the number of action points for this worm to the given number.
	 * @param 	number
	 * 			The new number of action points for this worm.
	 * @post	If the given number is a valid number of action points for this worm, then the 
	 * 			number of action points for this worm is equal to the given number.	 
	 * 			|if(isValidNumberOfActionPoints(number))new.getCurrentNumberofActionPoints=number;
	 */
	@Raw 
	private void setCurrentNumberOfActionPoints(long number){
		if(isValidNumberOfActionPoints(number)) currentNumberOfActionPoints=number;
	}
	
	/**
	 * Decrement this worm's current number of action points by the specified amount.
	 * @param 	costInActionPoints
	 * 			The amount of action points that should be removed.
	 * @effect	The current number of action points is set to the former number of action points 
	 * 			minus the specified amount.
	 * 			| setCurrentNumberOfActionPoints(getCurrentNumberOfActionPoints()-costInActionPoints)
	 */
	@Raw @Model
	private void removeActionPoints(long costInActionPoints) {
		setCurrentNumberOfActionPoints(getCurrentNumberOfActionPoints()-costInActionPoints);
	}
	
	/**
	 * Return whether the given number is a valid number of action points for this worm.
	 * @return	true if and only if the given number is positive and the given number is not
	 * 			greater than the maximal number of action points for this worm.
	 * 			|result == (number>=0 && number<=this.getMaximalNumberOfActionPoints())
	 */
	@Raw
	private boolean isValidNumberOfActionPoints(long number){
		return number>=0 && number<=this.getMaximalNumberOfActionPoints();
	}
	
	/**
	 * The current number of action points for this worm.
	 */
	private long currentNumberOfActionPoints;
	
	/**
	 * Move the worm the given number of steps in the direction it is currently facing. Decrease
	 * the number of action points accordingly.
	 * @param 	steps
	 * 			The number of steps this worm should move.
	 * @effect	The horizontal position of this worm is set to the old horizontal position, added to
	 * 			the cosine of the orientation of this worm multiplied by the given number of steps.
	 * 			|setHorizontalPosition(getHorizontalPosition()+
	 * 			|Math.cos(getOrientation())*steps*getRadius());
	 * @effect	The vertical position of this worm is set to the old vertical position, added to
	 * 			the sine of the orientation of this worm multiplied by the given number of steps.
	 * 			|setVerticalPosition(getVerticalPosition()+
	 * 			|Math.sin(getOrientation())*steps*getRadius());
	 * @effect	The number of action points of this worm is reduced by the number of steps, multiplied
	 * 			by the sums of the absolute values of the cosine of the orientation and four times the
	 * 			sine of the orientation.
	 * 			| removeActionPoints((long) Math.ceil(steps*(Math.abs(Math.cos(getOrientation())+
	 *			|		4*Math.abs(Math.sin(getOrientation()))))));
	 * @throws	IllegalArgumentException
	 *			The given number of steps is not a positive number.
	 *			|steps<0
	 * @throws  IllegalArgumentException
	 * 			The worm does not have enough action points to move this far.
	 * 			|getCurrentNumberOfActionPoints()<  Math.ceil(steps*(Math.abs(Math.cos(getOrientation())+
				|		4*Math.abs(Math.sin(getOrientation())))))
	 */
	public void move(int steps) throws IllegalArgumentException{
		if(!canMove(steps))throw new IllegalArgumentException("You fool! You can't move.");
		long tryActionPoints = (long) Math.ceil(steps*(Math.abs(Math.cos(getOrientation())+
				4*Math.abs(Math.sin(getOrientation())))));
		removeActionPoints(tryActionPoints);
		setHorizontalPosition(getHorizontalPosition()+Math.cos(getOrientation())*getRadius()*steps);
		setVerticalPosition(getVerticalPosition()+Math.sin(getOrientation())*getRadius()*steps);
	}
	
	/**
	 * Return a boolean reflecting whether this worm can move the given number of steps.
	 * @param 	steps
	 * 			The number of steps to be moved.
	 * @return	True if and only if the given number of steps is larger than or equal to zero, and the current number of action points is greater
	 * 			than the number of steps, multiplied by the sum of the absolute values of the cosine of the orientation and four times the
	 * 			sine of the orientation, rounded up.
	 * 			| getCurrentNumberOfActionPoints() >=  Math.ceil(steps*(Math.abs(Math.cos(getOrientation())+
				|		4*Math.abs(Math.sin(getOrientation()))))) && steps >=0
	 */
	public boolean canMove(int steps) {
		long tryActionPoints = this.getCurrentNumberOfActionPoints()-
				(long) Math.ceil(steps*(Math.abs(Math.cos(this.getOrientation())+
						4*Math.abs(Math.sin(this.getOrientation())))));
		return tryActionPoints>=0 && steps >= 0;
	}
	
	/**
	 * Turn the worm according to the given angle of rotation.
	 * @param 	rotationAngle
	 * 			The given angle of rotation.
	 * @Pre		The given angle of rotation must be a valid angle of rotation for this worm.
	 * 			| isValidRotationAngle(rotation)
	 * @effect	The orientation is set to the given angle of rotation added to the current orientation.
	 * 			| setOrientation((getOrientation()+rotationAngle)%2*Math.PI)
	 * @effect	The number of action points is decreased by the absolute value of the given 
	 * 				angle of rotation, multiplied by 60/2π.
	 * 			| removeActionPoints(Math.ceil(Math.abs(rotationAngle)*60/(2*Math.PI)))
	 */
	public void turn(double rotationAngle) {
		assert isValidRotationAngle(rotationAngle):"Precondition: Valid rotation angle";
		removeActionPoints((long) Math.ceil(Math.abs(rotationAngle)*60/(2*Math.PI)));
		double orientation = (getOrientation()+rotationAngle)%(2*Math.PI);
		if(orientation<0)orientation=2*Math.PI+orientation;
		setOrientation(orientation);
		
	}
	
	/**
	 * Check if a given angle is a valid angle of rotation for this worm.
	 * @param 	rotationAngle
	 * 			The given angle of rotation.
	 * @return	True if and only if there are enough action points to rotate the worm 
	 * 			the given angle of rotation.
	 * 			| result == Math.ceil(Math.abs(rotationAngle)*60/(2*Math.PI)) <= getCurrentNumberOfActionPoints()
	 */
	@Raw
	public boolean isValidRotationAngle(double rotationAngle) {
		return Math.ceil(Math.abs(rotationAngle)*60/(2*Math.PI)) <= getCurrentNumberOfActionPoints();
	}
	
	
	/**
	 * Let this worm jump to the left or to the right across a distance determined by this
	 * worm's current number of action points and by its orientation.
	 * @effect	If the orientation of this worm is turned upwards, this worm jumps to the right or to the left.
	 * 			The direction and distance of the jump depend on its orientation.
	 * 			| setHorizontalPosition(getHorizontalPosition() + getJumpDisplacement())
	 * @effect	Set the number of action points for this worm to 0.
	 * 			| setCurrentNumberOfActionPoints(0)
	 * @throws 	IllegalArgumentException
	 * 			This worm does not have any action points.
	 * 			| this.getCurrentNumberOfActionPoints()==0
	 * @throws 	BadOrientationException
	 * 			This worm is facing downwards.
	 * 			| this.getOrientation() > Math.PI
	 */
	public void jump() throws IllegalArgumentException, BadOrientationException {
		if(getCurrentNumberOfActionPoints() == 0) throw new IllegalArgumentException();
		if(getOrientation() > Math.PI ) throw new BadOrientationException("Worms cannot tunnel!");
		double displacement = this.getJumpDistance();
		setCurrentNumberOfActionPoints(0);
		setHorizontalPosition(getHorizontalPosition() + displacement);
	}
	
	/**
	 * Return the time this worm would need to jump.
	 * @return	The jumping distance of this worm, divided by the product of its initial 
	 * 			velocity and the cosine of its orientation, otherwise.
	 * 			| else result == getJumpDistance()/(getInitialVelocity()*Math.cos(getOrientation()));
	 * @throws	BadOrientationException
	 * 			This worm is facing downwards
	 * 			| this.getOrientation() > Math.PI
	 */
	@Raw
	public double jumpTime() throws BadOrientationException {
		if(getOrientation()> Math.PI) throw new BadOrientationException();
		return getJumpDistance()/(getInitialVelocity()*Math.cos(getOrientation()));
	}
	
	/**
	 * Return the position of this worm, the given time after the start of a jump.
	 * @param 	time
	 * 			The time (in seconds) that has passed since this worm has started to jump.
	 * @Pre		The given passed time has to be a positive number and cannot be greater than the
	 * 			time this worm would need to jump.
	 * 			|time>=0 && time <= this.jumpTime()
	 * @Pre		This worm has action points.
	 * 			|getCurrentNumberOfActionPoints > 0
	 * @return	If the worm has the right orientation to jump, the physical position this worm
	 * 			would have if the given time has passed since the start of the jump.
	 * 			|result == {this.getHorizontalPosition()+getInitialVelocity()*Math.cos(getOrientation())*time ,
	 * 			| this.getVerticalPosition()+getInitialVelocity()*Math.sin(getOrientation())*time
	 * 			| - 1/2*STANDARD_ACCELERATION*Math.pow(time,2)};
	 * @return	The original position otherwise.
	 * 			|result == {this.getHorizontalPosition(), this.getVerticalPosition()}
	 */
	@Raw
	public double [] jumpStep(double time){
		assert (time>=0 && time <= this.jumpTime()): "Precondition: The given time is valid";
		assert getCurrentNumberOfActionPoints() > 0: "Precondition: This worm has any remaining action points";
		if(getCurrentNumberOfActionPoints()==0) return new double[]{getHorizontalPosition(),getVerticalPosition()};
		if(getOrientation()>Math.PI) time=0;
		double initialVelocity = getInitialVelocity();
		double vx = initialVelocity*Math.cos(getOrientation());
		double vy = initialVelocity*Math.sin(getOrientation());
		return new double [] {this.getHorizontalPosition()+vx*time , this.getVerticalPosition()+vy*time
							-0.5*STANDARD_ACCELERATION*Math.pow(time,2)};
	}
	
	/**
	 * Return the oriented displacement this worm would make if it would jump.
	 * @return 	The distance this worm would cover if it would jump, multiplied by the sign of
	 * 			the cosine of its orientation.
	 * 			|result==getJumpDistance*sign(Math.cos(getOrientation))
	 */
	@Model
	private double getJumpDisplacement() {
		int jumpDirection = 1;
		if (getOrientation()>Math.PI/2) jumpDirection = -1;
		return jumpDirection*getJumpDistance();
	}
	
	
	/**
	 * Return the initial velocity of this worm if it would jump.
	 * @return 	The force this worm has exerted on the ground, divided by two times its mass. The force
	 * 			this worm has exerted is equal to five times the number of action points of this worm
	 * 			plus the mass times the standard acceleration.
	 * 			| result == (5*getCurrentNumberOfActionPoints())+(getMass()*STANDARD_ACCELERATION)/getMass()*0.5
	 */
	@Model
	private double getInitialVelocity() {
		double force = (5*getCurrentNumberOfActionPoints())+(getMass()*STANDARD_ACCELERATION);
		return force/getMass()*0.5;
	}
	
	/**
	 * Return the length of the displacement of this worm if it would jump.
	 * @return	The square of the initial velocity of this worm, multiplied by the sine of two times
	 * 			the orientation of this worm, divided by the standard acceleration.
	 * 			| result == (Math.pow(getInitialVelocity(),2)*Math.sin(2*getOrientation()))/STANDARD_ACCELERATION;
	 */
	@Model
	private double getJumpDistance() {
		return (Math.pow(getInitialVelocity(),2)*Math.sin(2*getOrientation()))/STANDARD_ACCELERATION;
	}
	
}
