package com.moosedrive.boots;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.moosedrive.boots.world.World;
import com.moosedrive.boots.world.types.Cube;
import com.moosedrive.boots.world.types.Layout;
import com.moosedrive.boots.world.types.Orientation;
import com.moosedrive.boots.world.types.Point;

public class MapWidget extends Actor {
	
	public static final int TILE_SIZE = 10;

	ShapeRenderer shapeRenderer;
	World world;

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.end();
		float originX = getWidth()/2;
		float originY = getHeight()/2;
		
		Layout layout = new Layout(Orientation.layout_pointy, new Point(TILE_SIZE,TILE_SIZE), new Point(originX, originY));
		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
		shapeRenderer.translate(getX(), getY(), 0);
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(1, 1, 0, 1);

		world.getTiles().forEach(t -> {
		        if (t != null) {
		        ArrayList<Point> tilePointsList = polygon_corners(layout, t.getCube());
				float[] polypoints = new float[14];
				Point[] tilepoints = tilePointsList.toArray(new Point[tilePointsList.size()]);
				for (int i = 0; i < 6; i++) {
					polypoints[i*2] = (float)tilepoints[i].x;
					polypoints[i*2+1] = (float)tilepoints[i].y;
				}
				polypoints[12]=(float)tilepoints[0].x;
				polypoints[13]=(float)tilepoints[0].y;
				shapeRenderer.polygon(polypoints);
		        }
		    }
		);

		shapeRenderer.end();


		batch.begin();
	}

	public MapWidget(World world) {
		this.shapeRenderer = new ShapeRenderer();
		this.world = world;
	}
	
	Point hex_to_pixel(Layout layout, Cube h) {
	    Orientation M = layout.orientation;
	    double x = (M.f0 * h.getQ() + M.f1 * h.getR()) * layout.size.x;
	    double y = (M.f2 * h.getQ() + M.f3 * h.getR()) * layout.size.y;
	    return new Point(x + layout.origin.x, y + layout.origin.y);
	}

	Point hex_corner_offset(Layout layout, int corner) {
	    Point size = layout.size;
	    double angle = 2.0 * Math.PI *
	             (layout.orientation.start_angle + corner) / 6;
	    return new Point(size.x * Math.cos(angle), size.y * Math.sin(angle));
	}

	ArrayList<Point> polygon_corners(Layout layout, Cube h) {
		ArrayList<Point> corners = new ArrayList<Point>();
	    Point center = hex_to_pixel(layout, h);
	    for (int i = 0; i < 6; i++) {
	        Point offset = hex_corner_offset(layout, i);
	        corners.add(new Point(center.x + offset.x,
	                                center.y + offset.y));
	    }
	    return corners;
	}
}
