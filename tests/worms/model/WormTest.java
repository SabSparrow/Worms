/**
 * 
 * @version 1.7
 * @author Yasmine Baestaens and Sander Leyssens (Bachelor of Mathematics)
 */
package worms.model;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import worms.BadOrientationException;
import worms.util.Util;


public class WormTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	private static Worm sander;
	private static Worm mover;
	private static Worm stander;
	
	@Before
	public void setUp() throws Exception {
		sander = new Worm("Sander", 0 ,0 , Math.PI/2, 1, 1);
		mover = new Worm("Mover", 0, 0, 1, 1, 4000);
		stander = new Worm("Sad crying worm",0,0,3*Math.PI/2,1,4000);
	}

	@After
	public void tearDown() throws Exception {
	}

	//With these tests we don't only test our constructor, but also our getters.
	@Test
	public void testWorm_LegalCase() {
		Worm wormpje = new Worm("The ol' \"Wormpje\"", 5.46, 3.46, 4, 0.7, 567);
		assertEquals(wormpje.getName(),"The ol' \"Wormpje\"");
		assertTrue(Util.fuzzyEquals(wormpje.getHorizontalPosition(),5.46));
		assertTrue(Util.fuzzyEquals(wormpje.getVerticalPosition(),3.46));
		assertTrue(Util.fuzzyEquals(wormpje.getRadius(),0.7));
		assertTrue(Util.fuzzyEquals(wormpje.getOrientation(),4));
		assertTrue(Util.fuzzyEquals(wormpje.getCurrentNumberOfActionPoints(),567));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testWorm_NullName() throws Exception{
		Worm wormpje = new Worm(null, 1, 1, 1, 1, 1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testWorm_TooShortName() throws Exception{
		Worm wormpje = new Worm ("G", 0, 0, 1, 1, 1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testWorm_DoesNotStartWithACapital() throws Exception{
		Worm wormpje = new Worm ("wormpje", 0, 0, 1, 1, 1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testWorm_InvalidCharacter() throws Exception{
		Worm wormpje = new Worm ("Worm$$", 0, 0, 1, 1, 1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testWorm_TooSmallRadius() throws Exception {
		Worm wormpje = new Worm("Worm",0,0,1,-1,1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testWorm_TooManyActionPoints() throws Exception {
		Worm wormpje = new Worm("Worm",0,0,1,-1,4449);
	}
	
	public void testWormFullAction_LegalCase() {
		Worm wormpje = new Worm("The ol' \"Wormpje\"", 5.46, 3.46, 4, 0.7);
		assertEquals(wormpje.getName(),"The ol' \"Wormpje\"");
		assertTrue(Util.fuzzyEquals(wormpje.getHorizontalPosition(),5.46));
		assertTrue(Util.fuzzyEquals(wormpje.getVerticalPosition(),3.46));
		assertTrue(Util.fuzzyEquals(wormpje.getRadius(),0.7));
		assertTrue(Util.fuzzyEquals(wormpje.getOrientation(),4));
		assertTrue(Util.fuzzyEquals(wormpje.getCurrentNumberOfActionPoints(),wormpje.getMaximalNumberOfActionPoints()));
	}
	
	@Test
	public void testGetName() {
		assertEquals(sander.getName(),"Sander");
	}

	@Test
	public void testGetHorizontalPosition() {
		assertTrue(Util.fuzzyEquals(sander.getHorizontalPosition(),0));	
	}

	@Test
	public void testGetVerticalPosition() {
		assertTrue(Util.fuzzyEquals(sander.getVerticalPosition(),0));
	}

	@Test
	public void testGetOrientation() {
		assertTrue(Util.fuzzyEquals(sander.getOrientation(),Math.PI/2));
	}

	@Test
	public void testIsValidOrientation_AllCases() {
		assertTrue(Worm.isValidOrientation(3));
		assertFalse(Worm.isValidOrientation(10141));
		assertFalse(Worm.isValidOrientation(-0.2));
	}
	
	@Test
	public void testGetMinRadius() {
		assertTrue(Util.fuzzyEquals(sander.getMinRadius(),0.25));
	}

	@Test
	public void testGetRadius() {
		assertTrue(Util.fuzzyEquals(sander.getRadius(),1));
	}

	@Test
	public void testGetMass() {
		assertTrue(Util.fuzzyEquals(sander.getMass(),4448.49519748));
	}

	@Test
	public void testGetCurrentNumberOfActionPoints() {
		assertTrue(sander.getCurrentNumberOfActionPoints()==1);
	}




	@Test
	public void testMove_ValidCase() {
		mover.move(10);
		assertTrue(mover.getCurrentNumberOfActionPoints()==3960);
		assertTrue(Util.fuzzyEquals(mover.getHorizontalPosition(),5.40302305));
		assertTrue(Util.fuzzyEquals(mover.getVerticalPosition(),8.41470984));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testMove_negativeSteps(){
		mover.move(-1000);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testMove_notEnoughActionPointsForThisAmountOfSteps(){
		mover.move(1000000);
	}
	
	@Test
	public void testCanMove_trueCase(){
		assertTrue(mover.canMove(10));
	}
	
	@Test
	public void testCanMove_negativeSteps(){
		assertFalse(mover.canMove(-1000));
	}
	
	@Test
	public void testCanMove_notEnoughActionPointsForThisAmountOfSteps(){
		assertFalse(mover.canMove(1000000));
	}

	@Test
	public void testTurn() {
		mover.turn(-7);
		assertTrue(Util.fuzzyEquals(mover.getOrientation(),0.283185307));
	}

	@Test
	public void testIsValidRotationAngle_TrueCase() {
		assertTrue(mover.isValidRotationAngle(-7));
		assertFalse(sander.isValidRotationAngle(1));
	}

	@Test
	public void testJump_FacingUpwards() {
		mover.jump();
		assertTrue(mover.getCurrentNumberOfActionPoints()==0);
		assertTrue(Util.fuzzyEquals(mover.getHorizontalPosition(), 4.741899957));
	}
	
	@Test(expected = BadOrientationException.class)
	public void testJump_FacingDownwards() {
		stander.jump();
	}

	@Test
	public void testJumpTime_FacingUpwards(){
		assertTrue(Util.fuzzyEquals(mover.jumpTime(), 1.227247104));
		
	}
	
	@Test (expected = BadOrientationException.class)
	public void testJumpTime_FacingDownwards() {
		stander.jumpTime();
	}

	@Test
	public void testJumpStep() {
		assertTrue(Util.fuzzyEquals(mover.jumpStep(0.7)[0],2.704695705));
		assertTrue(Util.fuzzyEquals(mover.jumpStep(0.93)[1],1.355474214));
	}

}
