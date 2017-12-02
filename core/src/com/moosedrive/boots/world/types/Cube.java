package com.moosedrive.boots.world.types;

public class Cube {

	private final int x;
	private final int y;
	private final int z;

	public Cube(int x, int y, int z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
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
