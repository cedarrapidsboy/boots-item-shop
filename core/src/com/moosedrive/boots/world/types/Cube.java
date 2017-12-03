package com.moosedrive.boots.world.types;

public class Cube {

	private final float x;
	private final float y;
	private final float z;

	public Cube(float x, float y, float z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public static Cube add(Cube a, Cube b) {
		return new Cube(a.getX() + b.getX(), a.getY() + b.getY(), a.getZ() + b.getZ());
	}

	public static Cube subtract(Cube a, Cube b) {
		return new Cube(a.getX() - b.getX(), a.getY() - b.getY(), a.getZ() - b.getZ());
	}

	public static Cube multiply(Cube a, int k) {
		return new Cube(a.getX() * k, a.getY() * k, a.getZ() * k);
	}

}
