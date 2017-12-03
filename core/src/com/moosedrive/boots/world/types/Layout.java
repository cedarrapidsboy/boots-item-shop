package com.moosedrive.boots.world.types;

public class Layout {

    public final Orientation orientation;
    public final Point size;
    public final Point origin;
	public Layout(Orientation orientation, Point size, Point origin) {
		this.orientation = orientation;
		this.size = size;
		this.origin = origin;
	}

}
