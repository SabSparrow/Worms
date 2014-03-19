/**
 * 
 * @version 1.7
 * @author Yasmine Baestaens and Sander Leyssens (Bachelor of Mathematics)
 */
package worms.model;

import worms.BadOrientationException;



public class Facade implements IFacade{
	
	public Facade() {
		
	}

	@Override
	public Worm createWorm(double x, double y, double direction, double radius,
			String name) {
		try {
			return  new Worm(name,x,y,direction,radius);
		} catch(IllegalArgumentException exc) {
			throw new ModelException(exc);
		}
		
	}

	@Override
	public boolean canMove(Worm worm, int nbSteps) {
		return worm.canMove(nbSteps);
	}

	@Override
	public void move(Worm worm, int nbSteps) {
		try {
			worm.move(nbSteps);
		} catch(IllegalArgumentException exc) {
			throw new ModelException(exc);
		}
		
	}

	@Override
	public boolean canTurn(Worm worm, double angle) {
		return worm.isValidRotationAngle(angle);
	}

	@Override
	public void turn(Worm worm, double angle) {
		worm.turn(angle);
	}

	@Override
	public void jump(Worm worm) {
		try{
			worm.jump();
		} catch(IllegalArgumentException exc) {
			throw new ModelException(exc);
		} catch(BadOrientationException e) {
			throw new ModelException(e);
		}
	}

	@Override
	public double getJumpTime(Worm worm) {
		try{
			return worm.jumpTime();
		} catch(BadOrientationException exc) {
			throw new ModelException(exc);
		}
	}

	@Override
	public double[] getJumpStep(Worm worm, double t) {
		return worm.jumpStep(t);
	}

	@Override
	public double getX(Worm worm) {
		return worm.getHorizontalPosition();
	}

	@Override
	public double getY(Worm worm) {
		return worm.getVerticalPosition();
	}

	@Override
	public double getOrientation(Worm worm) {
		return worm.getOrientation();
	}

	@Override
	public double getRadius(Worm worm) {
		return worm.getRadius();
	}

	@Override
	public void setRadius(Worm worm, double newRadius) {
		worm.setRadius(newRadius);
	}

	@Override
	public double getMinimalRadius(Worm worm) {
		return worm.getMinRadius();
	}

	@Override
	public int getActionPoints(Worm worm) {
		return (int) worm.getCurrentNumberOfActionPoints();
	}

	@Override
	public int getMaxActionPoints(Worm worm) {
		return (int) worm.getMaximalNumberOfActionPoints();
	}

	@Override
	public String getName(Worm worm) {
		return worm.getName();
	}

	@Override
	public void rename(Worm worm, String newName) {
		try {
			worm.setName(newName);
		} catch(IllegalArgumentException exc) {
			throw new ModelException(exc);
		}
	}

	@Override
	public double getMass(Worm worm) {
		return worm.getMass();
	}
	
}
